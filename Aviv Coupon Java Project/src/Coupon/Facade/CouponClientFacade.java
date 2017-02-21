package Coupon.Facade;

import Coupon.Beans.ClientType;
import Coupon.Exception.CouponSystemException;

public interface CouponClientFacade {

	
	public CouponClientFacade login(String name, String password, ClientType type) throws CouponSystemException;



}