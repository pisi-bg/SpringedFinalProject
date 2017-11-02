package com.example.model.pojo;

public class DeliveryInfo {

	private long deliveryInfoId;
	private String address;
	private int zipCode;
	private String city;
	private String recieverFirstName;
	private String recieverLastName;
	private String recieverPhone;
	private String notes;

	public DeliveryInfo() {
		
	}

	// constructor to send info in DB
	public DeliveryInfo(String address, int zipCode, String city, String recieverFirstName, String recieverLastName,
			String recieverPhone, String notes) {
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
			String recieverLastName, String recieverPhone, String notes) {
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

	public String getRecieverPhone() {
		return recieverPhone;
	}

	public String getNotes() {
		return notes;
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
