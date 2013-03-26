package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	Connection connection;

	public Database() {

	}
	
	//Connect to Database
//	public void connectToDb(String username, String password){
//		
//		String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug";
//		
//		try{
//			//Registering a driver
//			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
//
//		}catch(SQLException ex){
//
//			System.out.println("Message: " + ex.getMessage());
//
//		}
//		
//		try {
//			connection = DriverManager.getConnection(connectURL, username, password);
//
//			System.out.println("\nConnected to Oracle!");
//		} catch (SQLException ex) {
//			System.out.println("Message: " + ex.getMessage());
//		}
//	}
	
	//Setting Up the tables
	public void createBorrowerTable(){
		
		
		
	} 

}
