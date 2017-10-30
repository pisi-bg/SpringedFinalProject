package com.example.model.pojo;

import java.time.LocalDateTime;

public class Rating {

	private long productId;
	private String userEmail;
	private double rating;
	private String comment;
	private LocalDateTime dateTime;

	// getters
	public long getProductId() {
		return productId;
	}

	public String getUserEmail() {
		return userEmail;
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

	public Rating setProductId(long productId) {
		this.productId = productId;
		return this;
	}

	public Rating setUserEmail(String userEmail) {
		this.userEmail = userEmail;
		return this;
	}

	public Rating setRating(double rating) {
		this.rating = rating;
		return this;
	}

	public Rating setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public Rating setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
		return this;
	}

}
