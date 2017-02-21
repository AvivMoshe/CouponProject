package Coupon.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import Coupon.Exception.CouponSystemException;


public class ConnectionPool {

	private  static ConnectionPool instance ; //Singleton
	private  Set<Connection> connections = new HashSet<>();
	private  String url = "jdbc:derby://localhost:1527/CouponDB;create=true";
	
	
// CTOR
	public ConnectionPool() throws CouponSystemException{
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (ClassNotFoundException e) {
			throw new CouponSystemException("There has been a general problem connecting to DB. Contact your administrator.");
		}
		for (int i = 0; i < 11; i++) {
			try {
				connections.add(DriverManager.getConnection(url));
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Problem with the connections.");
			}
		}
	}

	
    public static ConnectionPool getInstance() throws CouponSystemException{
    	if(instance==null){
    		instance= new ConnectionPool();
    	}
		return instance;
    }
    
	
    
// Methods:
    
    /**
     * Method to get a connection to the DB.
     * */
	public synchronized Connection getConnection() throws CouponSystemException{
		while (connections.isEmpty()) {
			try {
				wait();         //if no connections available, wait till a connection returns to the pool.
			} catch (InterruptedException e) {
				throw new CouponSystemException("There has been a general problem connecting to DB. Contact your administrator.");
			}
		}
		
		Iterator<Connection> itCon = connections.iterator();
		Connection con = itCon.next();
		itCon.remove();
		return con;
	}

	
    /**
     * Method to return the connection to the DB.
     * */
	public synchronized void returnConnection(Connection con) {
		connections.remove(con);
		notify();
	}

	
    /**
     * Method to close all DB connections.
     * */
	public void closeAllConnections() throws CouponSystemException{
		for (Connection connection : connections) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CouponSystemException("Problem while trying to close all connections.");
			}
		}
	}

}