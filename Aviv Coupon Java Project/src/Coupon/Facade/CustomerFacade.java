package Coupon.Facade;

import java.util.ArrayList;
import java.util.Collection;

import Coupon.Beans.*;
import Coupon.DAO.*;
import Coupon.DBDAO.*;
import Coupon.Exception.CouponSystemException;


public class CustomerFacade implements CouponClientFacade{

	private CustomerDAO customerDao;
	private CouponDAO couponDao;
	private Customer customer;
	
	
	public CustomerFacade(){
		
		
	}
	
	
	public CustomerFacade(Customer customer) throws CouponSystemException{
		customerDao = new CustomerDBDAO();
		couponDao = new CouponDBDAO();
		this.customer = customer;
	}
	
	
	/**
	 * Method to purchase Coupon.
	 * Cannot buy a Coupon that already bought or amount = 0 or date expired. 
	 * */
	public Coupon purchaseCoupon(Coupon coupon) throws CouponSystemException{
		
		java.util.Date now = new java.util.Date();    //Updating the date that is today.
		
		if(coupon.getAmount()==0){	
			throw new CouponSystemException("Coupon amount is 0 try different Coupon.");
			
		}else if(customerDao.getCoupons(customer).contains(coupon)){
			throw new CouponSystemException("This Coupon already on Customer's list you cannot buy another!"); 
			
	    }else if(coupon.getEndDate().before(now)){
	    	throw new CouponSystemException("This Coupon is expired try different Coupon.");
	    	
		}else if(!couponDao.getAllCoupons().contains(coupon)){
			throw new CouponSystemException("Coupon cannot be found try different Coupon.");
		
		}else{
			customerDao.purchaseCoupon(customer, coupon);
			coupon.setAmount(coupon.getAmount()-1);
            couponDao.updateCouponAmount(coupon);
			return coupon;
		}
	}
	
	
	/**
	 * Shows all the Coupons that purchased by a specific Customer.
	 * */	
	public Collection<Coupon> getAllPurchasedCoupons() throws CouponSystemException{
		if(customerDao.getCoupons(customer).isEmpty()){
			throw new CouponSystemException("Nothing to show: Customer didn't buy any Coupons.");
		}else{
			return customerDao.getCoupons(customer);
		}
	}
	

	/**
	 * Shows all the Coupons that purchased by a specific Customer, by given Coupon's type.
	 * */	
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type) throws CouponSystemException{
      Collection<Coupon> CouponList = new ArrayList<>(); //New list that will hold all of the Coupons with the type the Customer entered.
    
      for (Coupon coupon : getAllPurchasedCoupons()) {
    	  if(coupon.getType() == type){
    		  CouponList.add(coupon);
    	  }
      }
      if(CouponList.isEmpty()){
    	  throw new CouponSystemException("Failed getting Coupons try different type.");
      }else{
    	  return CouponList;  
      }
	}
	
	
	/**
	 * Shows all the Coupons that purchased by a specific Customer, up to a certain price.
	 * */	
	public Collection<Coupon> getAllPurchasedCouponByPrice(Double price) throws CouponSystemException{
		Collection<Coupon> CouponList = new ArrayList<>(); //New list that will hold all of the Coupons up to a certain price the Customer entered.
        
        for (Coupon coupon : getAllPurchasedCoupons()) {
			if(coupon.getPrice() <= price){
				CouponList.add(coupon);
			}	
		}
        if(CouponList.isEmpty()){
        	throw new CouponSystemException("Failed getting Coupons try different price.");
        }else{
        	return CouponList;  
        }
	}
	

	/**
	 * Method to login.
	 * */
	@Override
	public CouponClientFacade login(String name, String password, ClientType type) throws CouponSystemException{
		
		// If User Name/Password wrong - throw exception
		if(customerDao.login(name, password)){
			return this;
		}else{
			throw new CouponSystemException("Failed login try different Name or Password.");
		}
    }
	
	
	
	
	/**
	 * Method to get Coupon by ID.
	 * Used only in web system!!
	 * */
	public Coupon getCouponByID(long id) throws CouponSystemException{
        for (Coupon coupon : couponDao.getAllCoupons()) {
        	if(coupon.getId()==id){
        		return coupon;
        	}
        }
        throw new CouponSystemException("Failed getting Coupon try different ID.");
	}
	
	
	/**
	 * Method to get all of the Coupons.
	 * Used only in web project!!
	 * */
	public Collection<Coupon> getAllCoupons() throws CouponSystemException{
		Collection<Coupon> coupList = couponDao.getAllCoupons();
		
		if(coupList.isEmpty()){
			throw new CouponSystemException("Nothing to show: there are no Coupons.");
		}
		return coupList;
	}
	
	
}