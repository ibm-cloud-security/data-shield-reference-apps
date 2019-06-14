package core;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import utils.Constants;

@Entity // This tells Hibernate to make a table out of this class
public class Transaction {
   
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String name;

    private String email;
    
    private String campaign;
    
    private int contribution;
    
    private long date;
    
    private String type;
    
    private String status = Constants.PENDING_PROCESS;
    
    @ElementCollection
    private List<Address> address;
    
    @Embedded
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) 
    private CreditCard creditCard;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCampaign() {
		return campaign;
	}

	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}

	public int getContribution() {
		return contribution;
	}

	public void setContribution(int contribution) {
		this.contribution = contribution;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		return "ID: "+this.id+" Campaign:"+this.campaign+" Contribution:"+this.contribution+" Type:"+this.type+" Status:"+this.status;
	}
	
}

@Embeddable
class Address{
	
	private String address1;
	
	private String city;
	
	private String state;
	
	private String country;
	
	private String zipcode;

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String toString() {
		return "City:"+this.city+" State:"+this.state+" Country:"+this.country+" ZipCode:"+this.zipcode;
	}
	
}

@Embeddable
class CreditCard{
	
	private String number;
	
	private String expiry;
	
	private String cvv;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public String toString() {
	    String lastFourDigits = this.number.substring(this.number.length() - 4);
	    return lastFourDigits;
	}
	
}

/*
 
 "transactions":[

{
"name": "Eduardo Rodriguez",
"email": "erodrig@us.ibm.com",
"campaign": "Default campaign",
"contribution": 50,
"address": [
  {
    "address1": "asdg",
    "city": "asdghas",
    "state": "asdgasdg",
    "country": "Default Country",
    "zipcode": "34535"
  }
],
"creditCard": {
  "number": "5452162517899084",
  "expiry": "2019-01-29",
  "cvv": "322"
},
"date": 1548694264036,
"type": "One-Time"
},

]
 
 */

