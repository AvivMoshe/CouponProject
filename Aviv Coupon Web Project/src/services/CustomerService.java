package services;

import java.util.Collection;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.*;
import javax.ws.rs.*;

import Coupon.Beans.*;
import Coupon.Exception.CouponSystemException;
import Coupon.Facade.CustomerFacade;

@Path("customer")
public class CustomerService {

	@Context
	private HttpServletRequest req;
	
	
	@POST
	@Path("Buy/{couponId}")
	@Produces(MediaType.TEXT_PLAIN)
	public String purchaseCoupon(@PathParam("couponId")String couponId){
		long id=Long.parseLong(couponId.substring(0,couponId.indexOf(" ")));
		
		CustomerFacade cf;
		cf = (CustomerFacade)req.getSession().getAttribute("customer");
		
		Coupon c;
		
		try {
			c = cf.getCouponByID(id);
			cf.purchaseCoupon(c);
		} catch (CouponSystemException e) {
			return "Purchase failed, "+e.getMessage();
		}
		return "Coupon " + c.getTitle() + " purchased successfully.";
	}
	
	
	
	@GET
	@Path("MyCoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllPurchasedCoupons(){
		
		CustomerFacade cf;
		cf = (CustomerFacade)req.getSession().getAttribute("customer");
		
		Collection<Coupon> coupList = new HashSet<>();
		
		try {
			coupList = cf.getAllPurchasedCoupons();
		} catch (CouponSystemException e) {
			return null;
		}
		return coupList;
	}
	
	
	
	@GET
	@Path("Type/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllPurchasedCouponsByType(@PathParam("type")CouponType type){
		
		CustomerFacade cf;
		cf = (CustomerFacade)req.getSession().getAttribute("customer");
		
		Collection<Coupon> coupList = new HashSet<>();
		
		try {
			coupList = cf.getAllPurchasedCouponsByType(type);
		} catch (CouponSystemException e) {
			return null;
		}
		return coupList;
	}
	
	
	
	@GET
	@Path("Price/{price}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllPurchasedCouponsByPrice(@PathParam("price")double price){
		
		CustomerFacade cf;
		cf = (CustomerFacade)req.getSession().getAttribute("customer");
		
		Collection<Coupon> coupList = new HashSet<>();
		
		try {
			coupList = cf.getAllPurchasedCouponByPrice(price);
		} catch (CouponSystemException e) {
			return null;
		}
		return coupList;
	}
	
	
	
	@GET
	@Path("getAllCoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllCoupons(){
		
		CustomerFacade cf;
		cf = (CustomerFacade)req.getSession().getAttribute("customer");
		
		try {
			return cf.getAllCoupons();
		} catch (CouponSystemException e) {
			return null;
		}
	}
	

	
	
	
	
	@GET
	@Path("getCouponID/{couponId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon getCouponByID(@PathParam("couponId")String couponId){
		long id=Long.parseLong(couponId.substring(0,couponId.indexOf(" ")));
		
		CustomerFacade cf;
		cf = (CustomerFacade)req.getSession().getAttribute("customer");
		
		try {
			
			return cf.getCouponByID(id);
		} catch (CouponSystemException e) {
			return null;
		}
		
	}
	
	
	
	
	
}