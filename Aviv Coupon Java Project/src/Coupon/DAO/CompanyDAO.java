package Coupon.DAO;

import java.util.Collection;

import Coupon.Beans.*;
import Coupon.Exception.CouponSystemException;

public interface CompanyDAO {

	 public void createCompany(Company company) throws CouponSystemException;
	 public void removeCompany(Company company) throws CouponSystemException;
	 public void updateCompany(Company company) throws CouponSystemException;
	 public Company getCompany(long id) throws CouponSystemException;
	 public Collection<Company> getAllCompanies() throws CouponSystemException;
	 public Collection<Coupon> getCoupons(Company company) throws CouponSystemException;
     public boolean login(String compName,String password) throws CouponSystemException;

     
     public void addCoupon(Coupon coupon, Company company) throws CouponSystemException;
     public Company getCompanyByName(String name) throws CouponSystemException;
}