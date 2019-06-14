package core;

public class BillingInfo {
	
	private int id;
	private String name;
	private String creditCardNumber;
	private String expiry;
	private String cvv;
	
	public BillingInfo() {
		
	}
	
	public BillingInfo(int id, String name, String creditCardNumber, String expiry, String cvv) {
		this.id = id;
		this.name = name;
		this.creditCardNumber = creditCardNumber;
		this.expiry = expiry;
		this.cvv = cvv;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public String getExpiry() {
		return expiry;
	}
	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	
	@Override
    public String toString() {
        return String.format("ID: "+getId());
    }
}
