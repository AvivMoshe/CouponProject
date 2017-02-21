package Coupon.System;

import java.util.Calendar;
import java.util.Date;

import Coupon.Beans.*;
import Coupon.Exception.CouponSystemException;
import Coupon.Facade.*;

public class MainTest {

	public static void main(String[] args) {

	try{
		System.out.println("Doing daily task and deleting Coupons that already expired.");
		
		CouponSystem system = CouponSystem.getInstance();  //when calling CouponSystem instance daily task will be started.
		
		
		AdminFacade admin = (AdminFacade)system.login("admin", "1234", ClientType.ADMIN);
		
//================================================================================		
		///////////////////////////
		///// Admin Section ///////
		/// Company management ////
		///////////////////////////	
	    
		Company comp = new Company(1, "Nike", "1592", "Nike@gmail.com");
		Company comp2 = new Company(2, "Fila", "125sb", "Fila@gmail.com");
		
//		System.out.println("Creating new company");
//		admin.createCompany(comp);
//		
//		System.out.println("Getting company by ID: "+ admin.getCompany(1));
//		
//		System.out.println("Getting all companies: "+ admin.getAllCompanies().toString());
		
		/**updates company details and view details of specific company:  */
//		System.out.println("Before email's update: "+ admin.getCompany(comp.getId()).toString());
//		comp.setEmail("aaaa@walla.com");
//		
//		admin.updateCompany(comp);
//		
//		System.out.println("After email's update: "+ admin.getCompany(comp.getId()).toString());
		
		
//		admin.removeCompany(comp);
//		System.out.println("Company removed.");
		
		
		
//================================================================================		
		///////////////////////////
		///// Admin Section ///////
		/// Customer management ///
		///////////////////////////	
		
		Customer cust = new Customer(10, "Aviv", "aaf2tg");
		Customer cust2 = new Customer(11, "David", "15a2d");
		
//		System.out.println("Creating new customer");
//		admin.createCustomer(cust);
//		
//		System.out.println("Getting customer by ID= "+ cust.getId() + ": " + admin.getCustomer(10));
//		
//		System.out.println("Getting all customers" + admin.getAllCustomer());
		
		/**updates customer details and view details of specific customer:  */
//		System.out.println("Before password change: " + admin.getCustomer(cust.getId()).toString());
//		cust.setPassword("123456");
//		
//		admin.updateCustomer(cust);
//		System.out.println("After password change: " + admin.getCustomer(cust.getId()).toString());
		
		
//		admin.removeCustomer(cust);
//	    System.out.println("Customer removed.");
		
		
		
//================================================================================		
		///////////////////////////
		///// Company Section /////
		///////////////////////////
		
//		CompanyFacade company = (CompanyFacade)system.login("Nike", "11234", ClientType.COMPANY);
//		CompanyFacade company2 = (CompanyFacade)system.login("Fila", "125sb", ClientType.COMPANY);
		
		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		Calendar calNew = Calendar.getInstance();
		
		cal.set(2016, 11, 01);
		cal2.set(2016, 12, 27);
		calNew.set(2017, 01, 20);
		Date StartDate = cal.getTime();
		Date endDate = cal2.getTime();
		Date newEndDate = calNew.getTime();
		
		Coupon coup = new Coupon(20, "Hamburger", StartDate, endDate, 10, CouponType.FOOD, "Big Hamburger in BBB.", 100, "null.jpg");
		Coupon coup2 = new Coupon(21, "Breakfast", StartDate, endDate, 15, CouponType.RESTAURANTS, "Breakfast for couple.", 150, "null.jpg");
		Coupon coup3 = new Coupon(22, "Dinner", StartDate, endDate, 12, CouponType.RESTAURANTS, "Dinner for couple.", 150, "null.jpg");
		Coupon coup4 = new Coupon(22, "Computer", StartDate, endDate, 18, CouponType.ELECTRICITY, "New gaming Laptop.", 500, "null.jpg");
		
//		System.out.println("Creating new coupon.");
//		company.createCoupon(coup2);
		
//		System.out.println("Company details: "+ company.getDetails());
//		
//		System.out.println("Company's coupon by coupon's ID: "+ company.getCoupon(20));   
		
//		System.out.println("Company's all coupons: "+ company.getAllCoupon());
		
		/**updates coupon price and end date: */
//		System.out.println("Before coupon update: "+"Price= "+ coup.getPrice()+ " ____   Date= " + coup.getEndDate());
//		
//		coup.setPrice(120);
//		coup.setEndDate(newEndDate);
//		company.updateCoupon(coup);
//		
//		System.out.println("After coupon update: "+ "Price="+ coup.getPrice() + " ____   Date= " + coup.getEndDate());
		
//		System.out.println("Company's coupons by type: "+ company.getCouponByType(CouponType.FOOD));
		
//		System.out.println("Company's coupons by price: "+ company.getCouponByPrice(220.5));
		
//		System.out.println("Company's coupons by date: "+ company.getCouponByDate(newEndDate));
		
//		company.removeCoupon(coup);
//		System.out.println("Coupon removed.");
		
		
		
//================================================================================		
		///////////////////////////
		//// Customer Section /////
		///////////////////////////
		
		/**With updated password:: */
		CustomerFacade customer = (CustomerFacade) system.login("Aviv", "123456", ClientType.CUSTOMER);
//		CustomerFacade customer2 = (CustomerFacade) system.login("David", "15a2d", ClientType.CUSTOMER);
		
//		System.out.println("Customer purchasing coupon.");
//		customer.purchaseCoupon(coup2);            
//		
//		System.out.println("Getting all customer's purchased coupons: "+ customer.getAllPurchasedCoupons());
		
//		System.out.println("Getting all customer's purchased coupons by type: "+ customer.getAllPurchasedCouponsByType(CouponType.FOOD));
		
//		System.out.println("Getting all customer's purchased coupons by price: "+ customer.getAllPurchasedCouponByPrice(202.5));
		
	
	}catch(CouponSystemException e){
		System.out.println(e.getMessage());
	}
	
//================================================================================	
		//////////////////////////
		// System shutting down //
	    //////////////////////////
	
	finally{
		try {
			System.out.println("System shutting down..");	
			CouponSystem.getInstance().shutdown();
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}	
		
    }//End of main.
}