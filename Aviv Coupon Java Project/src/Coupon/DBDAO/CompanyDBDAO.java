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


public class CompanyDBDAO implements CompanyDAO{

	private ConnectionPool pool;

	
	public CompanyDBDAO() throws CouponSystemException{
	  pool = ConnectionPool.getInstance();
	}
	
	
	
	/**
	 * DBDAO Method to create a new Company.
	 * Cannot create Company with the same ID that already exists
	 * because it's a primary key. 
	 */
	@Override
	public void createCompany(Company company) throws CouponSystemException{
	  Connection conn = pool.getConnection();
	  
	  PreparedStatement pstmt;
		
	  try{
		  pstmt = conn.prepareStatement("INSERT INTO Company (ID,Comp_Name,Password,Email) VALUES (?,?,?,?)");
		
		  pstmt.setLong(1, company.getId());
		  pstmt.setString(2, company.getCompName());
		  pstmt.setString(3, company.getPassword());
		  pstmt.setString(4, company.getEmail());
		  
		  pstmt.executeUpdate();
		  
	  }catch(SQLException e){
		  throw new CouponSystemException("Failed creating a new Company try different Name or ID.");
	  }
      finally{
    	  pool.returnConnection(conn);    	  
      }
	}

	
	
	/**
	 * DBDAO Method to remove a Company.
	 * Removes Company's coupons and all coupons purchased by Customers.
	 * Deleting from 4 tables Company, Customer_Coupon ,Company_Coupon and Coupon.
	 */
	@Override
	public void removeCompany(Company company) throws CouponSystemException{
	  Connection conn = pool.getConnection();
	  
      PreparedStatement pstmt;
      CompanyDBDAO c1 = new CompanyDBDAO();
      Collection<Coupon> coupons = c1.getCoupons(company);
        
      try{
    	  //Remove Company's coupons from Customer_Coupon table.
	      if(coupons != null){
	    	  for (Coupon coup : coupons) {
	    		long id = coup.getId();
				pstmt = conn.prepareStatement("DELETE FROM Customer_Coupon WHERE Coupon_ID = ?");
				pstmt.setLong(1, id);
				
				pstmt.executeUpdate();
			}
	      }
    	  
    	  //Remove Company's coupons from Company_Coupon table.
	     pstmt = conn.prepareStatement("DELETE FROM Company_Coupon WHERE Comp_ID = ?");
	     pstmt.setLong(1, company.getId());
	     
	     pstmt.executeUpdate();
	     
	      //Remove Company's coupons from Coupon table.
	     if(coupons != null){
	    	  for (Coupon coup : coupons) {
	    		long id = coup.getId();
				pstmt = conn.prepareStatement("DELETE FROM Coupon WHERE ID = ?");
				pstmt.setLong(1, id);
				
				pstmt.executeUpdate();
	    	  }
	     }
	     
	      //Remove Company's coupons from Company table.
	     pstmt = conn.prepareStatement("DELETE FROM Company WHERE ID = ?");
	     pstmt.setLong(1, company.getId());
	     
	     pstmt.executeUpdate();
		
	  }catch(SQLException e){
		 throw new CouponSystemException("Failed Removing the Company.");
	  }
      finally{	
	     pool.returnConnection(conn);
      }
	}

	
	
	/**
	 * DBDAO Method to update Company's Email and Password by ID.
	 * Cannot update Company's Name or ID.
	 */
	@Override
	public void updateCompany(Company company) throws CouponSystemException{
        Connection conn = pool.getConnection();
        
        PreparedStatement pstmt;
		
		try{
			pstmt = conn.prepareStatement("UPDATE Company SET Password = ? ,Email = ? WHERE (ID = ?) ");
		
			pstmt.setString(1, company.getPassword());
	        pstmt.setString(2, company.getEmail());
	        pstmt.setLong(3, company.getId());
	        
	        pstmt.executeUpdate();
	        		
		}catch(SQLException e){
			throw new CouponSystemException("Failed Updating the Company.");
		}
		finally{
			pool.returnConnection(conn);
		}
	}

	
	
	/**
	 * DBDAO Method to get Company's information by given ID number.
	 * */
	@Override
	public Company getCompany(long id) throws CouponSystemException{
        Connection conn = pool.getConnection();
        
        Statement stmt;
		ResultSet rs;
		
		Company c = new Company();
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Company WHERE (id= "+id+")");
			
			while(rs.next()){
				c.setId(rs.getLong(1));
				c.setCompName(rs.getString(2));
				c.setPassword(rs.getString(3));
				c.setEmail(rs.getString(4));
			}
		} catch (SQLException e) {
		    throw new CouponSystemException("Company was not found try different ID.");	
		}
		finally{
			pool.returnConnection(conn);
		}
		return c;
	}

	
	
	/**
	 * DBDAO Method to get all of the Companies.
	 **/
	@Override
	public Collection<Company> getAllCompanies() throws CouponSystemException{
        Connection conn = pool.getConnection();
        
		Statement stmt;
		ResultSet rs;
		Collection<Company> compList = new ArrayList<Company>();
	
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Company");
		
			while(rs.next()){
				Company c = new Company();
				
				c.setId(rs.getLong(1));
				c.setCompName(rs.getString(2));
				c.setPassword(rs.getString(3));
				c.setEmail(rs.getString(4));
				
				compList.add(c);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Failed getting all Companies.");
		}
		finally{
			pool.returnConnection(conn);			
		}
		return compList;
	}

	
	
	/**
	 * DBDAO Method to get a list of all the Coupons that belongs
	 * to a specific Company.
	 * */
	@Override
	public Collection<Coupon> getCoupons(Company company) throws CouponSystemException{
        Connection conn = pool.getConnection();
		
		Statement stmt;
		ResultSet rs;
		CouponDBDAO coupDAO = new CouponDBDAO();
		
		
		List<Long> coupsIDs = new ArrayList<Long>(); //ID's List from the join table.
		Collection<Coupon> coupSet = new HashSet<Coupon>(); //Coupons list.
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT Coupon_ID FROM Company_Coupon WHERE (Comp_ID =" + company.getId() +  ")");
		    
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
			throw new CouponSystemException("Failed getting the Company's Coupons try again.");
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
	public boolean login(String compName, String password) throws CouponSystemException{
        Connection conn = pool.getConnection();
        
        Statement stmt;
        ResultSet rs;
        
        try {
			stmt = conn.createStatement();
        	
        	rs = stmt.executeQuery("SELECT Comp_Name, Password FROM Company WHERE Comp_Name = '" + compName + "' ");
        	while(rs.next()){
        		String dbName = rs.getString(1); //Putting the Company's name into dbName
        		String dbPass = rs.getString(2); //Putting the Company's password into dbPass
             //checks if compName and password that entered equals name and password that on DB.
        		if(dbName.equals(compName) && dbPass.equals(password)){
        			return true;
        		}
        	}
		} catch (SQLException e) {
            throw new CouponSystemException("Failed to Login, Company's name not found.");
		}
        finally{
        	pool.returnConnection(conn);
        }
		return false;
	}


	
	
	/**
	 * DBDAO Method to add Company's Coupon to the join table Company_Coupon.
	 * */
	@Override
	public void addCoupon(Coupon coupon, Company company) throws CouponSystemException{
		Connection conn = pool.getConnection();
		
		PreparedStatement pstmt;
		
		try{
			pstmt = conn.prepareStatement("INSERT INTO Company_Coupon (Comp_ID, Coupon_ID) VALUES (?,?)");
			pstmt.setLong(1, company.getId());	
			pstmt.setLong(2, coupon.getId());		
			
			pstmt.executeUpdate();
			
		}catch (SQLException e){
			throw new CouponSystemException("Failed adding Coupon to Company_Coupon table.");
		}
		finally{
			pool.returnConnection(conn);
		}
	}


	/**
	 * DBDAO Method to get Company by name.
	 * To use in Coupon system only!! 
	 * */
	public Company getCompanyByName(String name) throws CouponSystemException{
        Connection conn = pool.getConnection();
        
        Statement stmt;
		ResultSet rs;
		
		Company c = new Company();
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Company WHERE (Comp_Name= '"+name+"')");
			
			while(rs.next()){
				c.setId(rs.getLong(1));
				c.setCompName(rs.getString(2));
				c.setPassword(rs.getString(3));
				c.setEmail(rs.getString(4));
			}
		} catch (SQLException e) {
		    throw new CouponSystemException("Company was not found try different name.");	
		}
		finally{
			pool.returnConnection(conn);
		}
		return c;
	} 
	
	
}