package Model;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A database of components.
 * 
 * @author Keegan Wantz - wantzkt@uw.edu (The whole thing)
 */
public class ComponentDatabase {
	/** The location of the database, and what driver to use. */
	final String DATABASE_CONNECTION_STRING = "jdbc:sqlite:Components.db";
	/** The minimum 'valid' part, anything below this is for testing. */
	final static int MINIMUM_ID = 5;
	/** A cache of all components that have been accessed. */
	private Map<Integer, ComponentHolder> myCachedComponents;
	
	/** JDBC Connection */
	private Connection conn;
	
	/** Constructs a new ComponentDatabase. */
	public ComponentDatabase() {
		conn = null;
		
		myCachedComponents = new HashMap<>();
		
		// TEMP FOR TESTING
		/*connect();
		Component test = new Component(0, "ADD_TEST", BigDecimal.ONE, BigDecimal.TEN, 10, 11, 12, 13, 14, "Ice Cream", 99, BigDecimal.ZERO, null);
		if (addComponent(test))
			System.out.println("Added the test.");
		else
			System.out.println("Failed to add the test.");*/
	}
	
	/**
	 * Connects to the database. Returns an integer for future expansion.
	 * 
	 * @return 1 if success, 0 otherwise.
	 */
	public int connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(DATABASE_CONNECTION_STRING);
						
			return 1;
		} catch (SQLException e) {
			System.out.println("SQL EXCEPTION");
			return 0;
		} catch (ClassNotFoundException e) {
			System.out.println("CLASS NOT FOUND EXCEPTION");
			return 0;
		}
	}
	
	/**
	 * Closes the connection to the database.
	 */
	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() {
		close();
	}

	/**
	 * Gets a component from the database, given its ID.
	 * 
	 * @param theID The ID of the component to retrieve.
	 * @return The Component, or null if it does not exist.
	 */
	public Component getComponent(final int theID) {
		return getComponent(theID, false);
	}
	
	/**
	 * Gets a component from the database, given its ID, includes a check to bypass the ID verification for testing.
	 * 
	 * @param theID The ID of the component to retrieve.
	 * @return The Component, or null if it does not exist.
	 */
	public Component getComponent(final int theID, boolean bypassIDCheck) {
		if (theID < MINIMUM_ID && !bypassIDCheck)
			return null;
		
		ComponentHolder cached = myCachedComponents.get(theID);
		if (cached != null) {
			return cached.getComponent();
		}		
		
		String query = "select " + 
				"ID, Name, Cost, MonthlyCost, Length, Width, Height, Radius, Weight, Material, EstimatedManHours, CostPerManHour \n" + 
				"from Components\n" + 
				"where ID = " + theID;

		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.setQueryTimeout(30);
			
			ResultSet res = stmt.executeQuery(query);
			

			while(res.next()) {
				int ID = res.getInt("ID");
				String name = res.getString("Name");
				BigDecimal cost = new BigDecimal(res.getString("Cost"));
				BigDecimal monthlyCost = new BigDecimal(res.getString("MonthlyCost"));
				double length = res.getDouble("Length");
				double width = res.getDouble("Width");
				double height = res.getDouble("Height");
				double radius = res.getDouble("Radius");
				double weight = res.getDouble("Weight");
				
				String material = res.getString("Material");
				
				double manHours = res.getDouble("EstimatedManHours");		
				BigDecimal costPerManHour = new BigDecimal(res.getString("CostPerManHour"));
				
				
				Component toCache = new Component(ID, name, cost, monthlyCost, length, width, height,
													radius, weight, material, manHours, costPerManHour, null);
				

				ComponentHolder cacheHolder = new ComponentHolder(toCache);
				
				myCachedComponents.put(ID, cacheHolder);

				stmt.close();
				
				return toCache;				
			}
			stmt.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return null;
	}
	
	/**
	 * Gets a list of all components within the database.
	 * 
	 * @return a List<Component> of all components in the database.
	 */
	public List<Component> getAllComponents() {
		String query = "select " + 
				"ID, Name, Cost, MonthlyCost, Length, Width, Height, Radius, Weight, Material, EstimatedManHours, CostPerManHour \n" + 
				"from Components;";
		
		List<Component> output = new ArrayList<>();
		
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.setQueryTimeout(30);
			
			ResultSet res = stmt.executeQuery(query);
			

			while(res.next()) {
				int ID = res.getInt("ID");
				if (ID < MINIMUM_ID)
					continue;
				
				String name = res.getString("Name");

				BigDecimal cost = new BigDecimal(res.getString("Cost"));
				BigDecimal monthlyCost = new BigDecimal(res.getString("MonthlyCost"));
				double length = res.getDouble("Length");
				double width = res.getDouble("Width");
				double height = res.getDouble("Height");
				double radius = res.getDouble("Radius");
				double weight = res.getDouble("Weight");
				
				String material = res.getString("Material");
				
				double manHours = res.getDouble("Width");		
				BigDecimal costPerManHour = new BigDecimal(res.getString("CostPerManHour"));
				
				ComponentHolder cached = myCachedComponents.get(ID);
				if (cached != null) {
					output.add(cached.getComponent());
				} else {		
				
					Component component = new Component(ID, name, cost, monthlyCost, length, width, height,
														radius, weight, material, manHours, costPerManHour, null);
					
	
					ComponentHolder cacheHolder = new ComponentHolder(component);
					
					myCachedComponents.put(ID, cacheHolder);
					
					output.add(component);
				}		
			}

			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;
	}
	
	/**
	 * Adds a component to the database.
	 * 
	 * @param theComponent The component to add to the database.
	 * @return True if success, false if failure.
	 */
	public boolean addComponent(final Component theComponent) {
		String insert = "INSERT INTO Components(Name, Cost, MonthlyCost, Length, Width, Height, Radius, Weight, Material, EstimatedManHours, CostPerManHour) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(insert);
			stmt.setString(1, theComponent.getName());
			stmt.setString(2, theComponent.getCost().toString());
			stmt.setString(3, theComponent.getCostPerMonth().toString());
			stmt.setDouble(4, theComponent.getLength());
			stmt.setDouble(5, theComponent.getWidth());
			stmt.setDouble(6, theComponent.getHeight());
			stmt.setDouble(7, theComponent.getMyRadius());
			stmt.setDouble(8, theComponent.getWeight());
			stmt.setString(9, theComponent.getMaterial());
			stmt.setDouble(10, theComponent.getManHrs());
			stmt.setString(11, theComponent.getCostPerManHr().toString());
			
			
			stmt.executeUpdate();
			stmt.close();
			return true;
		} catch (SQLException e) {
			System.out.println("SQL error.");
		}
		
		return false;
	}
	
	/**
	 * Deletes a component from the database.
	 * 
	 * @param theComponentID The ID to delete.
	 * @return True if success, false if failure.
	 */
	public boolean deleteComponent(int theComponentID) {
		String delete = "DELETE FROM Components WHERE (ID == ?)";
		if (myCachedComponents.get(theComponentID) != null)
			myCachedComponents.put(theComponentID, null);
		
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(delete);
			stmt.setInt(1, theComponentID);			
			
			stmt.executeUpdate();
			stmt.close();
			return true;
		} catch (SQLException e) {
			System.out.println("SQL error.");
		}
		
		return false;
	}
	
	/**
	 * Contains components for the cached components list.
	 * 
	 * @author Keegan Wantz - wantzkt@uw.edu
	 *
	 */
	private class ComponentHolder {
		private Component myComponent;
		
		private ComponentHolder(Component theComponent) {
			myComponent = theComponent;
		}
		
		private Component getComponent() {
			return myComponent;
		}
	}
}
