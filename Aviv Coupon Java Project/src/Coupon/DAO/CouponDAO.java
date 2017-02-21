package Coupon.DAO;

import java.sql.Date;
import java.util.Collection;

import Coupon.Beans.*;
import Coupon.Exception.CouponSystemException;

public interface CouponDAO {

	public void createCoupon(Coupon Coupon) throws CouponSystemException;
	public void removeCoupon(Coupon Coupon) throws CouponSystemException;
	public void updateCoupon(Coupon Coupon) throws CouponSystemException;
	public Coupon getCoupon(long id) throws CouponSystemException;
	public Collection<Coupon> getAllCoupons() throws CouponSystemException;
	public Collection<Coupon> getCouponByType(CouponType type) throws CouponSystemException;
    
	public void updateCouponAmount(Coupon coupon) throws CouponSystemException;
	public Collection<Coupon> getCouponsByDate(Date today) throws CouponSystemException;
}