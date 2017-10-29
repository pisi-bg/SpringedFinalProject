package com.example.model.pojo;

import java.time.LocalDateTime;

public class Rating {

	private long productId;
	private long userId;
	private double rating;
	private String comment;
	private LocalDateTime dateTime;

	// getters
	public long getProductId() {
		return productId;
	}

	public long getUserId() {
		return userId;
	}

	public double getRating() {
		return rating;
	}

	public String getComment() {
		return comment;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	// setters

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

}
