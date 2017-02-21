package Coupon.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import Coupon.Beans.*;
import Coupon.DAO.*;
import Coupon.DB.*;
import Coupon.Exception.CouponSystemException;


public class CustomerDBDAO implements CustomerDAO{

	private ConnectionPool pool;

	
	public CustomerDBDAO() throws CouponSystemException{
		pool = ConnectionPool.getInstance();
	}
	
	
	
	/**
	 * DBDAO Method to create a new Customer.
	 * Cannot create Customer with the same ID that already exists
	 * because it's a primary key. 
	 */
	@Override
	public void createCustomer(Customer customer) throws CouponSystemException{
		Connection conn = pool.getConnection();
		
		PreparedStatement pstmt;
		
		  try{
			  pstmt = conn.prepareStatement("INSERT INTO Customer (ID,Cust_Name,Password) VALUES (?,?,?)");
			
			  pstmt.setLong(1, customer.getId());
			  pstmt.setString(2, customer.getCustName());
			  pstmt.setString(3, customer.getPassword());
			  
			  pstmt.executeUpdate();
			  
		  }catch(SQLException e){
			  throw new CouponSystemException("Failed creating a new Customer try different Name or ID.");
		  }
	      finally{
	    	  pool.returnConnection(conn);    	  
	      }	
	}

	
	
	/**
	 * DBDAO Method to remove a Customer.
	 * Removes Customer's coupons history.
	 * Deleting from 2 tables Customer, Customer_Coupon.
	 */
	@Override
	public void removeCustomer(Customer customer) throws CouponSystemException{
		Connection conn = pool.getConnection();
		  
	      PreparedStatement pstmt;
	        
	      try{
	    	  //Remove Customer from Customer_Coupon table.
		     pstmt = conn.prepareStatement("DELETE FROM Customer_Coupon WHERE Cust_ID = ?");
		     pstmt.setLong(1, customer.getId());
		     
		     pstmt.executeUpdate();
		     
		     
		      //Remove Customer from Customer table.
		     pstmt = conn.prepareStatement("DELETE FROM Customer WHERE ID = ?");
		     pstmt.setLong(1, customer.getId());
		     
		     pstmt.executeUpdate();
			
		  }catch(SQLException e){
			 throw new CouponSystemException("Failed Removing the Customer.");
		  }
	      finally{	
		     pool.returnConnection(conn);
	      }
	}

	
	
	/**
	 * DBDAO Method to update Customer's Password by ID.
	 * Cannot update Customer's Name or ID.
	 */
	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException{
        Connection conn = pool.getConnection();
        
        PreparedStatement pstmt;
		
		try{
			pstmt = conn.prepareStatement("UPDATE Customer SET Password = ?  WHERE (ID = ?) ");
		
			pstmt.setString(1, customer.getPassword());
	        pstmt.setLong(2, customer.getId());
	        
	        pstmt.executeUpdate();
	        		
		}catch(SQLException e){
			throw new CouponSystemException("Failed Updating the Customer.");
		}
		finally{
			pool.returnConnection(conn);
		}	
	}

	
	
	/**
	 * DBDAO Method to get Customer's information by given ID number.
	 * */
	@Override
	public Customer getCustomer(long id) throws CouponSystemException{
        Connection conn = pool.getConnection();
        
        Statement stmt;
		ResultSet rs;
		
		Customer c = new Customer();
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Customer WHERE (id= "+id+")");
			
			while(rs.next()){
				c.setId(rs.getLong(1));
				c.setCustName(rs.getString(2));
				c.setPassword(rs.getString(3));
			}
		} catch (SQLException e) {
		    throw new CouponSystemException("Customer was not found try different ID.");	
		}
		finally{
			pool.returnConnection(conn);
		}
		return c;
	}

	
	
	/**
	 * DBDAO Method to get all of the Customers.
	 **/
	@Override
	public Collection<Customer> getAllCustomers() throws CouponSystemException{
        Connection conn = pool.getConnection();
        
		Statement stmt;
		ResultSet rs;
		Collection<Customer> custList = new ArrayList<Customer>();
	
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Customer");
		
			while(rs.next()){
				Customer c = new Customer();
				
				c.setId(rs.getLong(1));
				c.setCustName(rs.getString(2));
				c.setPassword(rs.getString(3));
				
				custList.add(c);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Failed getting all Customers.");
		}
		finally{
			pool.returnConnection(conn);			
		}
		return custList;
	}

	
	
	/**
	 * DBDAO Method to get a list of all the Coupons that belongs
	 * to a specific Customer.
	 * */
	@Override
	public Collection<Coupon> getCoupons(Customer customer) throws CouponSystemException{
        Connection conn = pool.getConnection();
		
		Statement stmt;
		ResultSet rs;
		CouponDBDAO coupDAO = new CouponDBDAO();
		
		List<Long> coupsIDs = new ArrayList<Long>(); //ID's List from the join table.
		Collection<Coupon> coupSet = new HashSet<Coupon>(); //Coupons list.
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT Coupon_ID FROM Customer_Coupon WHERE (Cust_ID =" + customer.getId() +  ")");
		    
			 //Retrieve the id's of customers coupon
		    while(rs.next()){
		    	coupsIDs.add(rs.getLong(1));
		    }
		     //Retrieve the coupon details for each id in the list.
		    for (Long cpnid : coupsIDs) {
				Coupon cpn = coupDAO.getCoupon(cpnid);
				coupSet.add(cpn);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Failed getting the Customer's Coupons.");
		}
		finally{
			pool.returnConnection(conn);
		}
		return coupSet;
	}

	
	
	/**
	 * DBDAO Method for login returns true or false.
	 * Checks if name and password are on the DB.
	 * */
	@Override
	public boolean login(String custName, String password) throws CouponSystemException{
		Connection conn = pool.getConnection();
		
		Statement stmt;
		ResultSet rs;
		
		try {
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("SELECT Cust_Name, Password FROM Customer WHERE Cust_Name='" + custName + "'");
			while (rs.next()){
				String dbName = rs.getString(1); //Putting the Company's name into dbName
				String dbPass = rs.getString(2); //Putting the Company's password into dbPass
			 //checks if custName and password that entered equals name and password that on DB.	
				if (dbName.equals(custName) && dbPass.equals(password)){
					return true;
			    }
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Failed to Login, Customer's name not found.");
		} 
		finally{
			pool.returnConnection(conn);
		}
        return false;
	}
	
	
	
	/**
	 * DBDAO Method to purchase Coupon.
	 * Adding Coupon to Customer_Coupon table. 
	 * */
	@Override
	public void purchaseCoupon(Customer customer , Coupon coupon) throws CouponSystemException{
		Connection conn = pool.getConnection();
		
		PreparedStatement pstmt;
		
		try {
			pstmt = conn.prepareStatement("INSERT INTO Customer_Coupon (Cust_ID, Coupon_ID) VALUES (?,?)");
			
			pstmt.setLong(1, customer.getId());
	        pstmt.setLong(2, coupon.getId());
	        
	        pstmt.executeUpdate();
	        		
		} catch (SQLException e) {
			throw new CouponSystemException("Failed purchasing coupon.");
		}
		finally{
			pool.returnConnection(conn);
		}
	}
	
	
	
	/**
	 * DBDAO Method to get Customer by name.
	 * To use in Coupon system only!! 
	 * */
	public Customer getCustomerByName(String name) throws CouponSystemException{
        Connection conn = pool.getConnection();
        
        Statement stmt;
		ResultSet rs;
		
		Customer c = new Customer();
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Customer WHERE (Cust_Name= '"+name+"')");
			
			while(rs.next()){
				c.setId(rs.getLong(1));
				c.setCustName(rs.getString(2));
				c.setPassword(rs.getString(3));
			}
		} catch (SQLException e) {
		    throw new CouponSystemException("Customer was not found try different name.");	
		}
		finally{
			pool.returnConnection(conn);
		}
		return c;
	} 
	
	
}