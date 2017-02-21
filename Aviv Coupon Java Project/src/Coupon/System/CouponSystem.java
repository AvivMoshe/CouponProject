package Coupon.System;

import Coupon.Beans.ClientType;
import Coupon.DAO.*;
import Coupon.DB.ConnectionPool;
import Coupon.DBDAO.*;
import Coupon.Facade.*;
import Coupon.Exception.CouponSystemException;

public class CouponSystem implements CouponClientFacade{

	private DailyCouponExpirationTask task;
	private Thread t;
	private static CouponSystem instance = new CouponSystem(); //Singleton
	private CouponClientFacade client;
	
	
	public static CouponSystem getInstance() throws CouponSystemException{
		return instance;
    }
	
	
//CTOR	
	private CouponSystem(){
		try {
			task= new DailyCouponExpirationTask();
			t= new Thread(task);
			t.start();
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Login method that checks which client type you are using.
	 * */
	@Override
	public CouponClientFacade login(String name, String password, ClientType type) throws CouponSystemException {
		switch (type)
		{
		case ADMIN:
			client = new AdminFacade();		
			break;
		case COMPANY:
			CompanyDAO companyDB = new CompanyDBDAO();
			client = new CompanyFacade(companyDB.getCompanyByName(name));
			break;
		case CUSTOMER:
			CustomerDAO customerDB = new CustomerDBDAO();
			client = new CustomerFacade(customerDB.getCustomerByName(name));
			break;
		default:
			throw new CouponSystemException("User does'nt exsits, try again.");		
		}
		client.login(name, password, type);
        return client; 
	}
	
	
	/**
	 * Method to shut down the system.
	 * Close all connections and stops the Daily coupon expiration task.
	 * */	
	public void shutdown() throws CouponSystemException{
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			task.stopTask();
			t.interrupt();
			pool.closeAllConnections();
		} catch (CouponSystemException e) {
			throw new CouponSystemException("Failed closing all connections or DailyTask problem.");
		}
	}


}