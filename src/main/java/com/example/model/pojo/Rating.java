package com.example.model.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Rating implements Serializable {

	private long productId;
	private String userName;
	private double rating;
	private String comment;
	private LocalDateTime dateTime;

				//*** GETTERS ***//
	public long getProductId() {
		return productId;
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

	public String getUserName() {
		return userName;
	}

					//*** SETTERS ***//

	public Rating setProductId(long productId) {
		this.productId = productId;
		return this;
	}

	public Rating setUserName(String userName) {
		this.userName = userName;
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
