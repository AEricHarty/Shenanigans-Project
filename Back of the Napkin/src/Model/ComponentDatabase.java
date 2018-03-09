package Model;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
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
 * @author Keegan Wantz - wantzkt@uw.edu (The whole thing for now!)
 */
public class ComponentDatabase {

	private Map<Integer, ComponentHolder> myCachedComponents;
	
	// Load sqlite JDBC driver
	private Connection conn;
	//Statement stmt;
	
	public ComponentDatabase() {
		conn = null;
		
		myCachedComponents = new HashMap<>();
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
	
	public Component getComponent(int theID) {
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
