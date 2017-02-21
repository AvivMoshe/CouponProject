package Coupon.DBDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import Coupon.Exception.CouponSystemException;
import Coupon.Beans.*;
import Coupon.DAO.*;
import Coupon.DB.*;


public class CouponDBDAO implements CouponDAO{
	
	private ConnectionPool pool;

	
	public CouponDBDAO() throws CouponSystemException{
		pool = ConnectionPool.getInstance();	
	}
	
	
	
	/**
	 * DBDAO Method to create a new Coupon.
	 * Cannot create Coupon with the same ID that already exists
	 * because it's a primary key.
	 * */
	@Override
	public void createCoupon(Coupon coupon) throws CouponSystemException{
		Connection conn = pool.getConnection();
		
		PreparedStatement pstmt;
		
		try {
			pstmt = conn.prepareStatement("INSERT INTO Coupon (ID, Title, Start_Date, End_Date, Amount, Type, Message, Price, Image) VALUES (?,?,?,?,?,?,?,?,?)");
			
			pstmt.setLong(1, coupon.getId());
			pstmt.setString(2, coupon.getTitle());
			pstmt.setDate(3, new java.sql.Date(coupon.getStartDate().getTime()));
			pstmt.setDate(4, new java.sql.Date(coupon.getEndDate().getTime()));
			pstmt.setInt(5, coupon.getAmount());
			pstmt.setString(6, coupon.getType().name());
			pstmt.setString(7, coupon.getMessage());
			pstmt.setDouble(8, coupon.getPrice());
			pstmt.setString(9, coupon.getImage());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
            throw new CouponSystemException("Failed creating a new Coupon.");
		}
		finally{
			pool.returnConnection(conn);		
		}
	}

	
	
	/**
	 * DBDAO Method to remove a Coupon.
	 * Deleting from 3 tables Customer_Coupon, Company_Coupon and Coupon.
	 * */
	@Override
	public void removeCoupon(Coupon coupon) throws CouponSystemException{
		Connection conn = pool.getConnection();
		
		PreparedStatement pstmt;
		
		try {
			 //Remove Coupons from Customer_Coupon table.
			pstmt = conn.prepareStatement("DELETE FROM Customer_Coupon WHERE Coupon_ID = ?");
			pstmt.setLong(1, coupon.getId());
					
			pstmt.executeUpdate();
			
			 //Remove Coupons from Company_Coupon table.
			pstmt = conn.prepareStatement("DELETE FROM Company_Coupon WHERE Coupon_ID = ?");
		    pstmt.setLong(1, coupon.getId());
		     
		    pstmt.executeUpdate();
			
		    
		     //Remove Coupon from Coupon table.
		    pstmt = conn.prepareStatement("DELETE FROM Coupon WHERE ID = ?");
		    pstmt.setLong(1, coupon.getId());
		     
		    pstmt.executeUpdate();
			
		} catch (SQLException e) {
            throw new CouponSystemException("Failed removing the Coupon.");
		}
		finally{
			pool.returnConnection(conn);		
		}
	}

	
	
	/**
	 * DBDAO Method to update Coupon's Price and End Date only. 
	 * Cannot update Coupon's Title or ID. 
	 * */
	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException{
		Connection conn = pool.getConnection();
		
		PreparedStatement pstmt;
		try{
			pstmt = conn.prepareStatement("UPDATE Coupon SET End_Date = ? ,Price = ? WHERE (ID = ?) ");
		
			pstmt.setDate(1, new java.sql.Date(coupon.getEndDate().getTime()));
			pstmt.setDouble(2, coupon.getPrice());
			pstmt.setLong(3, coupon.getId());
	        
			pstmt.executeUpdate();
			
		}catch(SQLException e){
			throw new CouponSystemException("Failed updating the Coupon.");
		}
		finally{
			pool.returnConnection(conn);
		}		
	}

	
	
	/**
	 * DBDAO Method to get a Coupon by given Coupon's ID.
	 * */
	@Override
	public Coupon getCoupon(long id) throws CouponSystemException{
		Connection conn = pool.getConnection();
		
		Statement stmt;
		ResultSet rs;
		
		Coupon c = new Coupon();
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Coupon WHERE (id= "+id+")");
			
			if(rs.next()){
				c.setId(rs.getLong(1));
				c.setTitle(rs.getString(2));
				c.setStartDate(rs.getDate(3));
				c.setEndDate(rs.getDate(4));
				c.setAmount(rs.getInt(5));
				c.setType(CouponType.valueOf(rs.getString(6)));
				c.setMessage(rs.getString(7));
				c.setPrice(rs.getDouble(8));
				c.setImage(rs.getString(9));
			}else{
				throw new CouponSystemException("Coupon wasn't found try different ID.");
			}
			
		} catch (SQLException e) {
            throw new CouponSystemException("Failed getting the Coupon.");
		}
		finally{
			pool.returnConnection(conn);		
		}
		return c;	
	}

	
	
	/**
	 * DBDAO Method to get all the Coupons.
	 * Shows all the Coupons.
	 * */
	@Override
	public Collection<Coupon> getAllCoupons() throws CouponSystemException{
		Connection conn = pool.getConnection();
		
		Statement stmt;
		ResultSet rs;
		Collection<Coupon> coupList = new ArrayList<Coupon>();
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Coupon");
		
			while(rs.next()){
				Coupon c = new Coupon();
				
				c.setId(rs.getLong(1));
				c.setTitle(rs.getString(2));
				c.setStartDate(rs.getDate(3));
				c.setEndDate(rs.getDate(4));
				c.setAmount(rs.getInt(5));
				c.setType(CouponType.valueOf(rs.getString(6)));
				c.setMessage(rs.getString(7));
				c.setPrice(rs.getDouble(8));
				c.setImage(rs.getString(9));
				
				coupList.add(c);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Failed getting all Coupons.");
		}
		finally{
			pool.returnConnection(conn);		
		}
		return coupList;
	}

	
	
	/**
	 * DBDAO Method to get Coupons by given Type.
	 * */
	@Override
	public Collection<Coupon> getCouponByType(CouponType Types) throws CouponSystemException{
        Connection conn = pool.getConnection();
		
		Statement stmt;
		ResultSet rs;
		Collection<Coupon> coupList = new ArrayList<Coupon>();
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Coupon WHERE (Type= '"+Types.name()+"')");
			
			while(rs.next()){
				Coupon c = new Coupon();
				c.setId(rs.getLong(1));
				c.setTitle(rs.getString(2));
				c.setStartDate(rs.getDate(3));
				c.setEndDate(rs.getDate(4));
				c.setAmount(rs.getInt(5));
				c.setType(CouponType.valueOf(rs.getString(6)));
				c.setMessage(rs.getString(7));
				c.setPrice(rs.getDouble(8));
				c.setImage(rs.getString(9));
				
				coupList.add(c);
			}
		} catch (SQLException e) {
            throw new CouponSystemException("Failed getting the Coupon try different Type.");
		}
		finally{
			pool.returnConnection(conn);		
		}
		return coupList;		
	}
	
	
	
	/**
	 * DBDAO Method to update Coupon amount.
	 * Only used when Customer purchase a coupon.
	 * */
	@Override
	public void updateCouponAmount(Coupon coupon) throws CouponSystemException{
		Connection conn = pool.getConnection();
		
		PreparedStatement pstmt;
		try{
			pstmt = conn.prepareStatement("UPDATE Coupon SET Amount = ? WHERE (ID = ?) ");
		
			pstmt.setInt(1, coupon.getAmount());
			pstmt.setLong(2, coupon.getId());
	        
			pstmt.executeUpdate();
			
		
		}catch(SQLException e){
			throw new CouponSystemException("Failed updating Coupon's amount.");
		}
		finally{
			pool.returnConnection(conn);
		}
	}
	
	
	/**
	 * DBDAO Method to get a Coupon by given Type.
	 * Only used in Daily Coupon Expiration Task.
	 * */
	public Collection<Coupon> getCouponsByDate(Date today) throws CouponSystemException{
		Connection conn = pool.getConnection();
		
		Statement stmt;
		ResultSet rs;
		Collection<Coupon> coupList = new ArrayList<Coupon>();
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Coupon WHERE (End_Date <= '"+today+"')");
			int count=0;
			while(rs.next()){
				count++;
				Coupon c = new Coupon();
				c.setId(rs.getLong(1));
				c.setTitle(rs.getString(2));
				c.setStartDate(rs.getDate(3));
				c.setEndDate(rs.getDate(4));
				c.setAmount(rs.getInt(5));
				c.setType(CouponType.valueOf(rs.getString(6)));
				c.setMessage(rs.getString(7));
				c.setPrice(rs.getDouble(8));
				c.setImage(rs.getString(9));
				
				coupList.add(c);
			}
			if(count==0){
				return null;
			}
		} catch (SQLException e) {
            throw new CouponSystemException("Failed getting the Coupon try different Date.");
		}
		finally{
			pool.returnConnection(conn);		
		}
		return coupList;
	}
	

}