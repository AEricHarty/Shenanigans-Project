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
	
	ComponentDatabase() {
		conn = null;
		stmt = null;
	}
	
	int connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			
			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(30);
			
			return 1;
		} catch (SQLException e) {
			return 0;
		} catch (ClassNotFoundException e) {
			return 0;
		}
	}
	
	Component getComponent(int theID) {
		String query = "select " + 
				" Name, Cost, MonthlyCost, Length, Width, Height, Radius, Weight, Material, EstimatedManHours, CostPerManHour \n" + 
				"from Components\n" + 
				"where ID = " + theID; 
		
		
		return null;
	}
	
	List<Component> getAllComponents() {
		String query = "select " + 
				" Name, Cost, MonthlyCost, Length, Width, Height, Radius, Weight, Material, EstimatedManHours, CostPerManHour \n" + 
				"from Components;";
		
		
		
		
		return null;
	}
}
