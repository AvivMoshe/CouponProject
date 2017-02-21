package services;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName="sessionCheck")
public class LoginFilter implements Filter{


	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filter) throws IOException, ServletException {
		
		HttpSession s = ((HttpServletRequest)req).getSession(false);
		
		if(s!=null){
			filter.doFilter(req, res);
			
		}else{
			((HttpServletResponse)res).sendRedirect("Login1.html");
		}
		
	}


	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

	
	@Override
	public void destroy() {
		
	}
	
	
}