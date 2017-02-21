package Coupon.Facade;

import Coupon.Beans.*;
import Coupon.DAO.*;
import Coupon.DBDAO.*;

import java.util.Collection;

import Coupon.Exception.CouponSystemException;


public class AdminFacade implements CouponClientFacade {

	private CompanyDAO companyDao = new CompanyDBDAO();
	private CustomerDAO customerDao = new CustomerDBDAO();
	private CouponDAO couponDao = new CouponDBDAO();
	
	public AdminFacade() throws CouponSystemException{
		
	}
	
	
	/**
	 * Method to create a new Company
	 * Cannot create a Company with the same Name or ID that already exists.
	 */
	public void createCompany(Company newCompany) throws CouponSystemException{
		if(companyDao.getAllCompanies().contains(newCompany)){
			throw new CouponSystemException("Company already exists. Try different Name or ID.");
		}else{
			companyDao.createCompany(newCompany);
		} 
	}
	
	
	/**
	 * Method to remove a Company.
	 * Removes Company's coupons and all Customer's coupons too.
	 * Deleting from 3 tables Company, Customer_Coupon, Company_Coupon.
	 */
	public void removeCompany(Company company) throws CouponSystemException{
		if(companyDao.getAllCompanies().contains(company)){
			companyDao.removeCompany(company);
		}else{
			throw new CouponSystemException("The Company you wish to remove was not found. Try again.");
		}
	}
	
	
	/**
	 * Method to update Company's Email and Password by ID.
	 * Cannot update Company's Name or ID.
	 */
	public void updateCompany(Company company) throws CouponSystemException{
		Company originalCompany = company;
		if(companyDao.getAllCompanies().contains(company)){
			companyDao.getCompany(company.getId());
			originalCompany.setPassword(company.getPassword());
			originalCompany.setEmail(company.getEmail());
			companyDao.updateCompany(originalCompany);
		}else{
			throw new CouponSystemException("The Company you wish to update was not found. Try again.");
		}
	}
	
	
	/**
	 * Method to get Company's information by given ID number.
	 * */
	public Company getCompany(long id) throws CouponSystemException{
        Company company;
        company = companyDao.getCompany(id);
        if(company.getId() == 0){
        	throw new CouponSystemException("Company was not found, try different ID.");
        }
        return company;
	}
	
	
	/**
	 * Method to get all of the Companies.
	 **/
	public Collection<Company> getAllCompanies() throws CouponSystemException{
		Collection<Company> companies = companyDao.getAllCompanies();
		
		if(companies.isEmpty()){
			throw new CouponSystemException("Nothing to show: there are no Companies.");
		}
        return companies;
	}
	
	
	/**
	 * Method to create a new Customer.
	 * Cannot create a Customer with the same Name or ID that already exists.
	 * */
	public void createCustomer(Customer customer) throws CouponSystemException{
        if(customerDao.getAllCustomers().contains(customer)){
        	throw new CouponSystemException("Customer already exists. Try different Name or ID.");
        }else{
        	customerDao.createCustomer(customer);
        }
	}
	
	
	/**
	 * Method to remove a Customer.
	 * Removes Customer's coupons history.
	 * Deleting from 2 tables Customer, Customer_Coupon.
	 */
	public void removeCustomer(Customer customer) throws CouponSystemException{
       if(customerDao.getAllCustomers().contains(customer)){
    	   customerDao.removeCustomer(customer);
       }else{
    	   throw new CouponSystemException("The Customer you wish to remove was not found. Try again.");
       }
	}
	
	
	/**
	 * Method to update Customer's Password by ID.
	 * Cannot update Customer's Name or ID.
	 */
	public void updateCustomer(Customer customer) throws CouponSystemException{
		Customer originalCustomer = customer;
       if(customerDao.getAllCustomers().contains(customer)){
    	   customerDao.getCustomer(customer.getId());
		   originalCustomer.setPassword(customer.getPassword());
		   customerDao.updateCustomer(originalCustomer);
       }else{
    	   throw new CouponSystemException("The Customer you wish to update was not found. Try again.");
       }
	}
	
	
	/**
	 * Method to get Customer's information by given ID number.
	 * */
	public Customer getCustomer(long id) throws CouponSystemException{
       Customer customer;
 
       customer = customerDao.getCustomer(id);
       if(customer.getId() == 0){
    	   throw new CouponSystemException("Customer was not found, try different ID.");
       }
       return customer;
	}
	
	
	/**
	 * Method to get all of the Customers.
	 **/
	public Collection<Customer> getAllCustomer() throws CouponSystemException{
       Collection<Customer> customers = customerDao.getAllCustomers();
       
       if(customers.isEmpty()){
    	   throw new CouponSystemException("Nothing to show: there are no Customers.");
       }
       return customers;
	}
	
	
	/**
	 * Method to login.
	 * */
	@Override
	public CouponClientFacade login(String name, String password, ClientType type) throws CouponSystemException{

		// If User Name/Password wrong - throw exception
		if(name.equals("admin") && password.equals("1234")){
			return this;
		}else{
			throw new CouponSystemException("Failed login try different Name or Password.");
		}
			
    }

	
	
	
	
	/**
	 * Method to get all of the Coupons that on the system. 
	 * Used only in web project!
	 * */
	public Collection<Coupon> getAllCoupons() throws CouponSystemException{
		Collection<Coupon> coupList = couponDao.getAllCoupons();
		
		if(coupList.isEmpty()){
			throw new CouponSystemException("Nothing to show: there are no Coupons.");
		}
		return coupList;
	}
	
}