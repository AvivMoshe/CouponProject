package Coupon.System;

import java.sql.Date;
import java.util.*;

import Coupon.Beans.*;
import Coupon.DAO.*;
import Coupon.DBDAO.*;
import Coupon.Exception.CouponSystemException;


public class DailyCouponExpirationTask implements Runnable{
	
	private CouponDAO couponDAO;
	private boolean quit = false;
	private final int DAY = 1000*60*60*24; //Day in MilliSeconds
	
	
//CTOR	
	public DailyCouponExpirationTask() throws CouponSystemException {
		couponDAO = new CouponDBDAO();		
		quit = true;
	}
	
	
	public boolean getQuit(){ 
		return quit;
    }
	
	
	public void setQuit (boolean quit) {
		this.quit = quit;
	}		

	
	/**
	 * Removing Coupons that expired.
	 * */
	@Override
	public void run(){		
	  while (quit){
		try{
			java.sql.Date today = new Date(Calendar.getInstance().getTimeInMillis());			
			Collection<Coupon> outOfDateCoupons = couponDAO.getCouponsByDate(today);			
			
			if(outOfDateCoupons!=null){
			for (Coupon c : outOfDateCoupons){
				couponDAO.removeCoupon(c);
				}
			}
			Thread.sleep(DAY); //Wait "24 hrs".
		}catch(InterruptedException e){
			//Do nothing - We're taking the system down.
			
	  	}catch(CouponSystemException e){
	  		try {
				throw new CouponSystemException("Failed removing Coupons by daily mission.");
			} catch (CouponSystemException e1) {
				e1.printStackTrace();
			}
	  	}
	  }			
   }		
	
	
	/**
	 * Method to stop the daily task.
	 * Sets quit false.
	 * */
	public void stopTask(){		
		setQuit(false);	
	}
	

}