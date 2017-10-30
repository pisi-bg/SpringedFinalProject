package com.example.model.pojo;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Order {

	private long orderId;
	private User user;
	private LocalDateTime dateTime;
	private double finalPrice;
	private long deliveryInfoId;
	private HashMap<Product, Integer> products;

	// constructor to send info in DB
	public Order(User user, LocalDateTime datetime, double finalPrice, long deliveryInfoId,
			HashMap<Product, Integer> products) {
		this.user = user;
		this.dateTime = datetime;
		this.finalPrice = finalPrice;
		this.products = products;
		this.deliveryInfoId = deliveryInfoId;
	}

	// constructor to retrieve info from DB
	public Order(long orderId, long user_id, LocalDateTime datetime, double finalPrice,
			HashMap<Product, Integer> products) {
		this.orderId = orderId;
		this.user = user;
		this.dateTime = datetime;
		this.finalPrice = finalPrice;
		this.products = products;
		this.deliveryInfoId = deliveryInfoId;
	}

	public void setId(long orderId) {
		this.orderId = orderId;
	}

	// getters

	public User getUser() {
		return user;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public double getFinalPrice() {
		return finalPrice;
	}

	public long getOrderId() {
		return orderId;
	}

	public long getDeliveryInfoId() {
		return deliveryInfoId;
	}

	public Map<Product, Integer> getProducts() {
		return (Map<Product, Integer>) Collections.unmodifiableMap(products);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (orderId ^ (orderId >>> 32));
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
		Order other = (Order) obj;
		if (orderId != other.orderId)
			return false;
		return true;
	}

}
