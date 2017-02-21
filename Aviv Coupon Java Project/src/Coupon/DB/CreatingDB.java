package Coupon.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class CreatingDB {

	public static void main(String[] args) {

//url to the database I want to create:		
	String url = "jdbc:derby://localhost:1527/CouponDB;create=true" ;
	try {
		Class.forName("org.apache.derby.jdbc.ClientDriver");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	try (Connection con = DriverManager.getConnection(url);){
		
		System.out.println("Created: " + con);
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	
	
	
	
		
	}
}