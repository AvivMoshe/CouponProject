package services;

import javax.servlet.http.HttpServletRequest;

import java.util.Collection;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import Coupon.Beans.*;
import Coupon.Exception.CouponSystemException;
import Coupon.Facade.AdminFacade;

@Path("admin")
public class AdminService {

	@Context
	private HttpServletRequest req;
	
	
	@POST
	@Path("Company/Create/{id}/{compName}/{pass}/{email}")
	@Produces(MediaType.TEXT_PLAIN)
	public String createCompany(@PathParam("id")long id ,@PathParam("compName")String compName ,@PathParam("pass")String password ,@PathParam("email")String email){
		AdminFacade af;
		
		af = (AdminFacade)req.getSession().getAttribute("admin");
		Company c = new Company(id, compName, password, email);
		try {
			af.createCompany(c);
		} catch (CouponSystemException e) {
			return "Adding company failed. "+e.getMessage();
		}
		return "Adding company " +c.getCompName()+ " succeed.";
	}
	
	
	
	@DELETE
	@Path("Company/Delete/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String removeCompany(@PathParam("id")long id){
		
		AdminFacade af;
		Company c;
		
		af = (AdminFacade)req.getSession().getAttribute("admin");
		
		try {
			c= af.getCompany(id);
			af.removeCompany(c);
		} catch (CouponSystemException e) {
			return "Company removal failed, " + e.getMessage();
		}
		return "Company: " +c.getCompName()+ " removed successfully.";
	}
	
	
	
	@PUT
	@Path("Company/Update/{id}/{pass}/{email}")   //Can update Password and Email only.
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCompany(@PathParam("id")long id ,@PathParam("pass")String password ,@PathParam("email")String email){
		
		AdminFacade af;
		af = (AdminFacade)req.getSession().getAttribute("admin");
		
		Company c;
		
		try {
			c = af.getCompany(id);
			c.setPassword(password);
			c.setEmail(email);
			af.updateCompany(c);
		} catch (CouponSystemException e) {
			return "Company update failed, "+e.getMessage();
		}
		return "Company " +c.getCompName()+ " updated successfully.";
	}
	
	
	
	@GET
	@Path("Company/Get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Company getCompany(@PathParam("id")long id){
		
		AdminFacade af;
		Company company = new Company();
		
		af = (AdminFacade)req.getSession().getAttribute("admin");
		
		try {
			company = af.getCompany(id);
			return company;
		} catch (CouponSystemException e) {
			return null;
		}		
	}
	
	
	
	@GET
	@Path("Company/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Company> getAllCompanies(){
		
		AdminFacade af;
		
		af = (AdminFacade)req.getSession().getAttribute("admin");
		
		try {
			return af.getAllCompanies();
		} catch (CouponSystemException e) {
			return null;
		}
	}
	
	
	
	
	//---------------------//
	//----- Customer ------//
	//---------------------//
	
	@POST
	@Path("Customer/Create/{id}/{name}/{pass}")
	@Produces(MediaType.TEXT_PLAIN)
	public String createCustomer(@PathParam("id")long id, @PathParam("name")String name, @PathParam("pass")String password){
		
		AdminFacade af;
		
		af = (AdminFacade)req.getSession().getAttribute("admin");
		
		Customer c = new Customer(id, name, password);
		
		try {
			af.createCustomer(c);
		} catch (CouponSystemException e) {
			return "Adding customer failed. "+e.getMessage();
		}
		return "Adding customer succeed.";
	}
	
	
	
	@DELETE
	@Path("Customer/Delete/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String removeCustomer(@PathParam("id")long id){
		
		AdminFacade af;
		Customer c;
		
		af = (AdminFacade)req.getSession().getAttribute("admin");
		
		try {
			c = af.getCustomer(id);
			af.removeCustomer(c);
		} catch (CouponSystemException e) {
			return "Customer removal failed. " +e.getMessage();
		}
		return "Customer " +c.getCustName()+ " removed successfully.";
	}
	
	
	
	@PUT
	@Path("Customer/Update/{id}/{pass}") //Can update Password only.
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCustomer(@PathParam("id")long id, @PathParam("pass")String password){
		
		AdminFacade af;
		Customer c;
		
		af = (AdminFacade)req.getSession().getAttribute("admin");
		
		try {
			c = af.getCustomer(id);
			c.setPassword(password);
			af.updateCustomer(c);
		} catch (CouponSystemException e) {
			return "Customer update failed, " +e.getMessage();
		}
		return "Customer " + c.getCustName() +" updated successfully.";
	}
	
	
	
	@GET
	@Path("Customer/Get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(@PathParam("id")long id){
		
		AdminFacade af;
		
		af = (AdminFacade)req.getSession().getAttribute("admin");
		
		try {
			return af.getCustomer(id);
		} catch (CouponSystemException e) {
			return null;
		}
	}
	
	
	
	@GET
	@Path("Customer/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> getAllCustomer(){
		
		AdminFacade af;
		
		af = (AdminFacade)req.getSession().getAttribute("admin");
		
		try {
			return af.getAllCustomer();
		} catch (CouponSystemException e) {
			return null;
		}
	}

	
	
	@GET
	@Path("getAllCoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllCoupons(){
		
		AdminFacade af;
		
		af = (AdminFacade)req.getSession().getAttribute("admin");
		
		try {
			return af.getAllCoupons();
		} catch (CouponSystemException e) {
			return null;
		}
	}
	
	
	
}