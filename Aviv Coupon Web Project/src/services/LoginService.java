package services;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Coupon.Beans.ClientType;
import Coupon.Exception.CouponSystemException;
import Coupon.Facade.AdminFacade;
import Coupon.Facade.CompanyFacade;
import Coupon.Facade.CustomerFacade;
import Coupon.System.CouponSystem;

/**
 * Servlet implementation class LoginService
 */
public class LoginService extends HttpServlet {

	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String user = req.getParameter("user");
		String pass = req.getParameter("pass");
		String type = req.getParameter("role");
		
		ClientType t = ClientType.valueOf(type);
		
		
		switch (t) {
		case ADMIN:
			try {
				AdminFacade af = (AdminFacade) CouponSystem.getInstance().login(user, pass, ClientType.ADMIN);
				
				if(af!=null){
					if(req.getSession(false) !=null){
						req.getSession().invalidate();
					}else{
						req.getSession(true).setAttribute("admin", af);
						res.sendRedirect("admin.html");
					}
				}
			} catch (CouponSystemException e) {
				res.sendRedirect("badlogin.html");
			}
			break;
			
		case COMPANY:
			try {
				CompanyFacade cf = (CompanyFacade) CouponSystem.getInstance().login(user, pass, ClientType.COMPANY);
				
				if(cf!=null){
					if(req.getSession(false) !=null){
						req.getSession().invalidate();
					}else{
						req.getSession(true).setAttribute("company", cf);
						res.sendRedirect("comp.html");
					}
				}
			} catch (CouponSystemException e) {
				res.sendRedirect("badlogin.html");
			}
			break;
			
		case CUSTOMER:
			try {
				CustomerFacade cf = (CustomerFacade) CouponSystem.getInstance().login(user, pass, ClientType.CUSTOMER);
				
				if(cf!=null){
					if(req.getSession(false) !=null){
						req.getSession().invalidate();
					}else{
						req.getSession(true).setAttribute("customer", cf);
						res.sendRedirect("cust.html");
					}
				}
			} catch (CouponSystemException e) {
				res.sendRedirect("badlogin.html");
			}
			break;
		}
			
		
	
	}
	
}
