package com.example.model.pojo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

public class DeliveryInfo {

	private long deliveryInfoId;
	@NotBlank(message="Моля, попълнете валиден адрес.")
	private String address;
	@Min(value= 1000 , message="Моля, въведете валиден пощенски код.")
	@Max(value = 9999, message ="Моля, въведете валиден пощенски код.")
	private int zipCode;
	@NotBlank(message="Моля, попълнете валиден град.")
	private String city;
	@NotBlank(message="Моля, попълнете валидно име.")
	private String recieverFirstName;
	@NotBlank(message="Моля, попълнете валидно име.")
	private String recieverLastName;
	@Min(value= 111111111 , message="Моля, въведете валиден телефонен номер без 0 отпред.")
	@Max(value= 999999999 , message="Моля, въведете валиден телефонен номер без 0 отпред.")
	private long recieverPhone = 8;
	private String notes;

	public DeliveryInfo() {
		
	}

	// constructor to send info in DB
	public DeliveryInfo(String address, int zipCode, String city, String recieverFirstName, String recieverLastName,
			long recieverPhone, String notes) {
		this.address = address;
		this.zipCode = zipCode;
		this.city = city;
		this.recieverFirstName = recieverFirstName;
		this.recieverLastName = recieverLastName;
		this.recieverPhone = recieverPhone;
		this.notes = notes;
	}

	// constructor to retrieve info from DB
	public DeliveryInfo(long deliveryInfoId, String address, int zipCode, String city, String recieverFirstName,
			String recieverLastName, long recieverPhone, String notes) {
		this.deliveryInfoId = deliveryInfoId;
		this.address = address;
		this.zipCode = zipCode;
		this.city = city;
		this.recieverFirstName = recieverFirstName;
		this.recieverLastName = recieverLastName;
		this.recieverPhone = recieverPhone;
		this.notes = notes;
	}

	public void setDeliveryInfoId(long deliveryInfoId) {
		this.deliveryInfoId = deliveryInfoId;
		System.out.println("Deliveryinfo id set");
	}

	public long getDeliveryInfoId() {
		return deliveryInfoId;
	}

	public String getAddress() {
		return address;
	}

	public int getZipCode() {
		return zipCode;
	}

	public String getCity() {
		return city;
	}

	public String getRecieverFirstName() {
		return recieverFirstName;
	}

	public String getRecieverLastName() {
		return recieverLastName;
	}

	public long getRecieverPhone() {
		return recieverPhone;
	}

	public String getNotes() {
		return notes;
	}
	
	
	
	

	public void setAddress(String address) {
		this.address = address;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setRecieverFirstName(String recieverFirstName) {
		this.recieverFirstName = recieverFirstName;
	}

	public void setRecieverLastName(String recieverLastName) {
		this.recieverLastName = recieverLastName;
	}

	public void setRecieverPhone(long recieverPhone) {
		this.recieverPhone = recieverPhone;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
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
		DeliveryInfo other = (DeliveryInfo) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		return true;
	}

	// getters

}
