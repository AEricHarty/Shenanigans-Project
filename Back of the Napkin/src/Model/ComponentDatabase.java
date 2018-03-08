package Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.ArrayList;

public class ComponentDatabase {

	// Load sqlite JDBC driver
	Connection conn;
	Statement stmt;
	
	public ComponentDatabase() {
		conn = null;
	}
	
	public int connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:Components.db");
			
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(30);
			
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
		String query = "select " + 
				" Name, Cost, MonthlyCost, Length, Width, Height, Radius, Weight, Material, EstimatedManHours, CostPerManHour \n" + 
				"from Components\n" + 
				"where ID = " + theID; 
		
		
		return null;
	}
	
	public List<Component> getAllComponents() {
		String query = "select " + 
				" Name, Cost, MonthlyCost, Length, Width, Height, Radius, Weight, Material, EstimatedManHours, CostPerManHour \n" + 
				"from Components;";
		
		
		
		
		return null;
	}
}
