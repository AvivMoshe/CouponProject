package Coupon.Beans;

import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer {
	
	
	private long id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;
	
	
//CTOR	
    public Customer() {
		super();
	}
    
    
    public Customer(long id, String custName, String password) {
		super();
		this.id = id;
		this.custName = custName;
		this.password = password;
	}

    

//Getters and Setters:	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Collection<Coupon> getCoupons() {
		return coupons;
	}
	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}
	

	
//To String:
    @Override
	public String toString() {
		return "Customer [ID=" + id + ", CustName=" + custName + ", Password=" + password + "] ";
	}


 
//Overriding hashCode and equals
//Customers cannot can't have the same Name or ID.
//Because ID is a primary key in our table we don't need to override it.
    @Override
    public int hashCode() {
    	final int prime = 31;
    	int result = 1;
    	result = prime * result + ((custName == null) ? 0 : custName.hashCode());
    	return result;
    }
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (custName == null) {
			if (other.custName != null)
				return false;
		} else if (!custName.equals(other.custName))
			return false;
		return true;
	}
	
}