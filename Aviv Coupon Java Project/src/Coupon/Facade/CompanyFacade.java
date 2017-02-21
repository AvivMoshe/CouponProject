package Coupon.Facade;

import java.util.Collection;
import java.util.Date;
import java.util.ArrayList;

import Coupon.Beans.*;
import Coupon.DAO.*;
import Coupon.DBDAO.*;
import Coupon.Exception.CouponSystemException;


public class CompanyFacade implements CouponClientFacade{

	private CompanyDAO companyDao;
	private CouponDAO couponDao;
	private Company company;
	
	
	public CompanyFacade(){
		
	}
	
	
	public CompanyFacade(Company company) throws CouponSystemException{
		companyDao = new CompanyDBDAO();
		couponDao = new CouponDBDAO();
		this.company = company;
	}


	/**
	 * Method to create a new Coupon.
	 * Cannot create Coupon with the same ID that already exists.
	 * Cannot create Coupon with the same Title that already exists.
	 * Cannot create Coupon with amount 0 or lower.
	 * */
	public void createCoupon(Coupon coupon) throws CouponSystemException{
        if(couponDao.getAllCoupons().contains(coupon)){
        	throw new CouponSystemException("Adding Coupon failed, Coupon " +coupon.getTitle() +" already exists.");
        }else if(coupon.getAmount()<=0){
        	throw new CouponSystemException("Adding Coupon failed, Coupon amount must be over 0.");
        }else{
        	couponDao.createCoupon(coupon);
        	companyDao.addCoupon(coupon, company);
        } 
	}
	
	
	/**
	 * Method to remove a Coupon.
	 * Deleting from 3 tables Company, Customer_Coupon, Company_Coupon.
	 * */
	public void removeCoupon(Coupon coupon) throws CouponSystemException{
        if(couponDao.getAllCoupons().contains(coupon)){
        	couponDao.removeCoupon(coupon);
        }else{
        	throw new CouponSystemException("Removing Coupon failed, Coupon " + coupon.getTitle() + " doesn't exists. ");
        }
	}
	

	/**
	 * Method to update Coupon's Price and End Date only. 
	 * Cannot update Coupon Title or ID. 
	 * */
	public void updateCoupon(Coupon coupon) throws CouponSystemException{        
        if(couponDao.getAllCoupons().contains(coupon)){
        	Coupon original = coupon;
        	original.setEndDate(coupon.getEndDate());
        	original.setPrice(coupon.getPrice());
        	couponDao.updateCoupon(original);
        }else{
        	throw new CouponSystemException("Updating Coupon failed, Coupon " + coupon.getTitle() + " doesn't exists.");
        }
	}

	
	/**
	 * Method to get Company's information.
	 * */
	public Company getDetails() throws CouponSystemException{
		if(companyDao.getAllCompanies().contains(company)){
			return companyDao.getCompany(company.getId());			
		}else{
			throw new CouponSystemException("Failed getting Company's details.");
		}
	}
	

	/**
	 * Method to get a Coupon by given Coupon's ID.
	 * */
	public Coupon getCoupon(long id) throws CouponSystemException{
        for (Coupon coupon : getAllCoupon()) {
        	if(coupon.getId()==id){
        		return coupon;
        	}
        }
        throw new CouponSystemException("Failed getting Coupon try different ID.");
	}
	
	
	/**
	 * Shows all the Coupons that belongs to a specific Company.
	 * */
	public Collection<Coupon> getAllCoupon() throws CouponSystemException{
		Collection<Coupon> list = companyDao.getCoupons(company);  //All Company's Coupons list.
		
		if(list.isEmpty()){
			throw new CouponSystemException("Nothing to show: Company doesn't have any Coupons.");
		}else{
			return list;
		}
	}
	

	/**
	 * Shows all the Coupons by type that belongs to a specific Company, by giving the type of the Coupon.
	 * */
	public Collection<Coupon> getCouponByType(CouponType type) throws CouponSystemException{
        Collection<Coupon> filterdCouponList = new ArrayList<Coupon>();   //New list that will hold all of the Coupons with the type the Company entered. 
   
        for (Coupon coupon : getAllCoupon()) {
			if(coupon.getType() == type){
				filterdCouponList.add(coupon);
			}	
		}
        if(filterdCouponList.isEmpty()){
        	throw new CouponSystemException("Failed getting Coupons try different type.");
        }else{
        	return filterdCouponList;	
        }
	}
	

	/**
	 * Method to login.
	 * */
	@Override
	public CouponClientFacade login(String name, String password, ClientType type) throws CouponSystemException{

		// If User Name/Password wrong - throw exception
		if(companyDao.login(name, password)){
			return this;
		}else{
			throw new CouponSystemException("Failed login try different Name or Password.");
		}
	}
	

	/**
	 * Shows all the Coupons that belongs to a specific Company, up to a certain price.
	 * */
	public Collection<Coupon> getCouponByPrice(double price) throws CouponSystemException{               
		Collection<Coupon> filterdCouponList = new ArrayList<Coupon>(); //New list that will hold all of the Coupons up to a certain price.
   
        for (Coupon coupon : getAllCoupon()) {
        	if(coupon.getPrice() <= price){
        		filterdCouponList.add(coupon);
        	}
		}
        if(filterdCouponList.isEmpty()){
        	throw new CouponSystemException("Failed getting Coupons try different price.");
        }else{
        	return filterdCouponList;	
        }
	}
	
	
	/**
	 * Shows all the Coupons that belongs to a specific Company, up to a certain date.
	 * */	
	public Collection<Coupon> getCouponByDate(Date date) throws CouponSystemException{      
		Collection<Coupon> filterdCouponList = new ArrayList<Coupon>(); //New list that will hold all of the Coupons up to a certain date.
   
		for (Coupon coupon : getAllCoupon()) {
			if(coupon.getEndDate().before(date)){
				filterdCouponList.add(coupon);
			}
		}
		if(filterdCouponList.isEmpty()){
        	throw new CouponSystemException("Failed getting Coupons try different date.");
        }else{
        	return filterdCouponList;	
        }
	}
	
	
}