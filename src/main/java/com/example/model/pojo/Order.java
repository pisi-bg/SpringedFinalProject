package com.example.model.pojo;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Order {

	private long orderId;
	private User user;
	private LocalDateTime dateTime;
	private double finalPrice;
	private DeliveryInfo deliveryInfo;
	private HashMap<Product, Integer> products;

	// *** GETTERS ***//

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

	public DeliveryInfo getDeliveryInfo() {
		return deliveryInfo;
	}

	public Map<Product, Integer> getProducts() {
		return (Map<Product, Integer>) Collections.unmodifiableMap(products);
	}

	// *** SETTERS ***//

	public Order setId(long orderId) {
		this.orderId = orderId;
		return this;
	}

	public Order setOrderId(long orderId) {
		this.orderId = orderId;
		return this;
	}

	public Order setUser(User user) {
		this.user = user;
		return this;
	}

	public Order setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
		return this;
	}

	public Order setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
		return this;
	}

	public Order setDeliveryInfo(DeliveryInfo deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
		return this;
	}

	public Order setProducts(HashMap<Product, Integer> products) {
		this.products = products;
		return this;
	}

	// *** ADDITTIONAL METHODS ***//
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

	public static double calculatePriceForCart(HashMap<Product, Integer> products) {
		double cartPrice = 0;
		for (Entry<Product, Integer> entry : products.entrySet()) {
			Product product = entry.getKey();
			int quantity = entry.getValue();
			double productPrice = product.getPrice();
			if (product.getDiscount() != 0) {
				productPrice = product.calcDiscountedPrice();
			}
			cartPrice += (productPrice * quantity);
		}
		return cartPrice;
	}

}
