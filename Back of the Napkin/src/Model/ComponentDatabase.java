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
	/**  */
	final static int MINIMUM_ID = 0;
	/** */
	private Map<Integer, ComponentHolder> myCachedComponents;
	
	/** JDBC Connection */
	private Connection conn;
	
	/** */
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
	
	public int connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:Components.db");
						
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
	 * 
	 * @param theID
	 * @return
	 */
	public Component getComponent(final int theID) {
		if (theID < MINIMUM_ID)
			return null;
		
		ComponentHolder cached = myCachedComponents.get(theID);
		if (cached != null) {
			if (!cached.getDirty()) {
				return cached.getComponent();
			}
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
				
				double manHours = res.getDouble("Width");		
				BigDecimal costPerManHour = new BigDecimal(res.getString("CostPerManHour"));
				
				
				Component toCache = new Component(ID, name, cost, monthlyCost, length, width, height,
													radius, weight, material, manHours, costPerManHour, null);
				

				ComponentHolder cacheHolder = new ComponentHolder(toCache);
				
				myCachedComponents.put(ID, cacheHolder);
				
				return toCache;				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return null;
	}
	
	/**
	 * 
	 * @return
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
					if (!cached.getDirty()) {
						output.add(cached.getComponent());
					}
				} else {		
				
					Component component = new Component(ID, name, cost, monthlyCost, length, width, height,
														radius, weight, material, manHours, costPerManHour, null);
					
	
					ComponentHolder cacheHolder = new ComponentHolder(component);
					
					myCachedComponents.put(ID, cacheHolder);
					
					output.add(component);
				}		
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;
	}
	
	/**
	 * 
	 * @param theComponent
	 * @return
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
			return true;
		} catch (SQLException e) {
			System.out.println("SQL error.");
		}
		
		return false;
	}
	
	public void deleteComponent(int theComponentID) {
		
	}
	
	/**
	 * Contains components for the cached components list.
	 * 
	 * @author Keegan Wantz - wantzkt@uw.edu
	 *
	 */
	private class ComponentHolder {
		private Component myComponent;
		private boolean myIsDirty;
		
		private ComponentHolder(Component theComponent) {
			myComponent = theComponent;
			myIsDirty = false;
		}
		
		private Component getComponent() {
			return myComponent;
		}
		
		private void setDirty() {
			myIsDirty = true;
		}
		
		private boolean getDirty() {
			return myIsDirty;
		}
	}
}
