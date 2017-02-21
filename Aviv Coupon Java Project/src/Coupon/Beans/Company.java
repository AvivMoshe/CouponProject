package Coupon.Beans;

import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Company {
	

	private long id;
	private String compName;
	private String password;
	private String email;
    private Collection<Coupon> coupons;

    
//CTOR     
    public Company() {
		super();
	}
    
    
    public Company(long id, String compName, String password, String email) {
		super();
		this.id = id;
		this.compName = compName;
		this.password = password;
		this.email = email;
	}

    

//Getters and Setters:	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
		return "Company [ID=" + id + ", CompName=" + compName + ", Password=" + password + ", Email=" + email + "] ";
	}


   
//Overriding hashCode and equals
//Companies can't have the same Name or ID.
//Because ID is a primary key in our table we don't need to override it. 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((compName == null) ? 0 : compName.hashCode());
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
		Company other = (Company) obj;
		if (compName == null) {
			if (other.compName != null)
				return false;
		} else if (!compName.equals(other.compName))
			return false;
		return true;
	}
	
}