package Coupon.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class CreatingTables {

	public static void main(String[] args) {
		
	String url = "jdbc:derby://localhost:1527/CouponDB";	
		
	try {
		Class.forName("org.apache.derby.jdbc.ClientDriver");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}	
	try (Connection con = DriverManager.getConnection(url);){
		
	String sql	= "create table Company ( ID bigint not null primary key, Comp_Name varchar(20) not null, Password varchar(10) not null, Email varchar(20) not null)";
//    String sql  = "create table Customer( ID bigint not null primary key, Cust_Name varchar(20) not null, Password varchar(10) not null)";
//    String sql  = "create table Coupon( ID bigint not null primary key, Title varchar(30) not null, Start_Date date not null, End_Date date not null, Amount integer not null, Type varchar(20) not null, Message varchar(100) not null, Price double not null, Image varchar(50) not null)";
//    String sql  = "create table Company_Coupon (Comp_ID bigint not null, Coupon_ID bigint not null)";
//    String sql  = "create table Customer_Coupon (Cust_ID bigint not null, Coupon_ID bigint not null)";

	
	Statement stmt = con.createStatement();
	stmt.executeUpdate(sql);
	
	System.out.println("Success: " + sql);	
		
	
	} catch (SQLException e) {
		e.printStackTrace();
	}	
	
	
	
	
	
	
	}
}