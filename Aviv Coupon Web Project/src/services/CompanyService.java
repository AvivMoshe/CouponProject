package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import Coupon.Beans.*;
import Coupon.Exception.CouponSystemException;
import Coupon.Facade.CompanyFacade;

@Path("company")
public class CompanyService {

	@Context
	private HttpServletRequest req;
	
	
	@POST
	@Path("Create/{id}/{title}/{startD}/{startM}/{startY}/{endD}/{endM}/{endY}/{amount}/{type}/{message}/{price}/{image}")
	@Produces(MediaType.TEXT_PLAIN)
	public String createCoupon(@PathParam("id")long id, @PathParam("title")String title, @PathParam("startD")int startD, @PathParam("startM")int startM, @PathParam("startY")int startY, @PathParam("endD")int endD, @PathParam("endM")int endM, @PathParam("endY")int endY, @PathParam("amount")int amount, @PathParam("type")CouponType type, @PathParam("message")String message, @PathParam("price")double price, @PathParam("image")String image){
		
		CompanyFacade cf;
		Coupon c = new Coupon();
		
		
		Calendar cal = Calendar.getInstance();
		cal.set(startY, startM-1, startD);
		Date startDate = cal.getTime();
		cal.set(endY, endM, endD);
		Date endDate = cal.getTime();
		
		
		cf = (CompanyFacade)req.getSession().getAttribute("company");
		
		try {
			c.setId(id);
			c.setTitle(title);
			c.setStartDate(startDate);
			c.setEndDate(endDate);
			c.setAmount(amount);
			c.setType(type);
			c.setMessage(message);
			c.setPrice(price);
			c.setImage(image);
		
			cf.createCoupon(c);
		} catch (CouponSystemException e) {
			return e.getMessage();
		}
		return "Coupon " +c.getTitle()+ " created successfully";
	}
	
	
	
	@POST
	@Path("Delete/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String removeCoupon(@PathParam("id")long id){
		
		CompanyFacade cf;
		Coupon c;
		
		cf = (CompanyFacade)req.getSession().getAttribute("company");
		
		try {
			c = cf.getCoupon(id);
			cf.removeCoupon(c);
		} catch (CouponSystemException e) {
			return "Coupon removal failed, " + e.getMessage();
		}
		return "Coupon "+ c.getTitle() + " removed successfully.";
	}
	
	
	
	@PUT
	@Path("Update/{id}/{price}/{day}/{month}/{year}")  //Can update coupon's Price and EndDate only.
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCoupon(@PathParam("id")long id, @PathParam("price")double price, @PathParam("day")int day, @PathParam("month")int month, @PathParam("year")int year){
		
		CompanyFacade cf;
		Coupon c;
		
		cf = (CompanyFacade)req.getSession().getAttribute("company");
		
		Calendar calc = Calendar.getInstance();
		calc.set(year, month-1, day);
		Date endDate = calc.getTime();
		
		try {
			c = cf.getCoupon(id);
			c.setPrice(price);
			c.setEndDate(endDate);
			cf.updateCoupon(c);
		} catch (CouponSystemException e) {
			return "Coupon updating failed, "+e.getMessage();
		}
		return "Coupon "+c.getTitle() + " updated successfully";
	}
	
	
	
	@GET
	@Path("Get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon getCoupon(@PathParam("id")long id){
		
		CompanyFacade cf;
		Coupon c = new Coupon();
		
		cf = (CompanyFacade)req.getSession().getAttribute("company");
		
		
		try {
			c = cf.getCoupon(id);
		} catch (CouponSystemException e) {
			return null;
		}
		return c;
	}
	
	
	
	@GET
	@Path("getAllCoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllCoupon(){
		
		CompanyFacade cf;
		
		cf = (CompanyFacade)req.getSession().getAttribute("company");
		
		Collection<Coupon> coupList = new HashSet<>();
		
		try {
			coupList = cf.getAllCoupon();
		} catch (CouponSystemException e) {
			return null;
		}
		return coupList;
	}
	
	
	
	@GET
	@Path("Type/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponByType(@PathParam("type")CouponType ct){
		
		CompanyFacade cf;
		
		cf = (CompanyFacade)req.getSession().getAttribute("company");
		
		Collection<Coupon> coupList = new HashSet<>();
		
		try {
			coupList = cf.getCouponByType(ct);
		} catch (CouponSystemException e) {
			return null;
		}
		return coupList;
	}
	
	
	
	@GET
	@Path("Price/{price}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponByPrice(@PathParam("price")double price){
		
		CompanyFacade cf;
		
		cf = (CompanyFacade)req.getSession().getAttribute("company");
		
		Collection<Coupon> coupList = new HashSet<>();
		
		try {
			coupList = cf.getCouponByPrice(price);
		} catch (CouponSystemException e) {
			return null;
		}
		return coupList;
	}

	
	
	@GET
	@Path("Date/{d}/{m}/{y}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponByDate(@PathParam("d")int d, @PathParam("m")int m, @PathParam("y")int y){
		
		CompanyFacade cf;
		
		cf = (CompanyFacade)req.getSession().getAttribute("company");
		
		Collection<Coupon> coupons = new HashSet<Coupon>();		
		Calendar cal = Calendar.getInstance();
		cal.set(y, m-1, d);
		Date currentDate = cal.getTime();

		try {		
			coupons = cf.getCouponByDate(currentDate);
		}catch (CouponSystemException e) {
			return null;
		} 
		return coupons; 
	}
	
	
	
	@GET
	@Path("getDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Company getDetails(){
		
		CompanyFacade cf;
		
		cf = (CompanyFacade)req.getSession().getAttribute("company");
		
		try {
			return cf.getDetails();
		} catch (CouponSystemException e) {
			return null;
		}
	}
	
}