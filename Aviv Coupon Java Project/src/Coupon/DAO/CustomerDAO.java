package Coupon.DAO;

import java.util.Collection;

import Coupon.Beans.*;
import Coupon.Exception.CouponSystemException;

public interface CustomerDAO {

   public void createCustomer(Customer customer) throws CouponSystemException;
   public void removeCustomer(Customer customer) throws CouponSystemException;
   public void updateCustomer(Customer customer) throws CouponSystemException;
   public Customer getCustomer(long id) throws CouponSystemException;
   public Collection<Customer> getAllCustomers() throws CouponSystemException;
   public Collection<Coupon> getCoupons(Customer customer) throws CouponSystemException;
   public boolean login(String custName,String password) throws CouponSystemException;
   
   public void purchaseCoupon(Customer customer , Coupon coupon) throws CouponSystemException;
   public Customer getCustomerByName(String name) throws CouponSystemException;
}