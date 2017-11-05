package com.example.model.pojo;

import java.io.Serializable;

import com.example.utils.exceptions.IllegalDiscountException;

public class Product implements Serializable {

	private long id;
	private String name;
	private String description;
	private double price;
	private String animal;
	private String category;
	private String brand;
	private double rating;
	private int countRating;
	private int inStock;
	private String image;
	private int discount; // in percent

				//*** GETTERS ***//
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public String getAnimal() {
		return animal;
	}

	public String getCategory() {
		return category;
	}

	public String getBrand() {
		return brand;
	}

	public double getRating() {
		return rating;
	}

	public int getInStock() {
		return inStock;
	}

	public String getImage() {
		return image;
	}

	public int getDiscount() {
		return discount;
	}

	public int getCountRating() {
		return countRating;
	}

	// ** SETTERS ****

	public Product setName(String name) {
		this.name = name;
		return this;
	}

	public Product setDescription(String description) {
		this.description = description;
		return this;
	}

	public Product setPrice(double price) {
		this.price = price;
		return this;
	}

	public Product setAnimal(String animal) {
		this.animal = animal;
		return this;
	}

	public Product setCategory(String category) {
		this.category = category;
		return this;
	}

	public Product setBrand(String brand) {
		this.brand = brand;
		return this;
	}

	public Product setRating(double rating) {
		this.rating = rating;
		return this;
	}

	public Product setInStock(int isStock) {
		this.inStock = isStock;
		return this;
	}

	public Product setImage(String image) {
		this.image = image;
		return this;
	}

	public Product setCountRating(int countRating) {
		if (countRating >= 0) {
			this.countRating = countRating;
		}
		return this;
	}
	public Product setId(long id) {
		this.id = id;
		return this;
	}

	public Product setDiscount(int discount) throws IllegalDiscountException {
		if (discount >= 0 && discount <= 100) {
			this.discount = discount;
		} else {
			throw new IllegalDiscountException("Невалидни данни за отстъпка. Моля въведете стойност между 0-99");
		}
		return this;
	}

	
				//*** ADDITTIONAL METHODS ***//
		@Override
		public String toString() {
			return this.name + " " + this.description + " " + this.animal + " " + this.category + " " + this.price + " "
					+ this.discount;
		}

		public double calcDiscountedPrice() {
			double newPrice = price * ((100 - discount) / 100.0);
			return newPrice;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (id ^ (id >>> 32));
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
			Product other = (Product) obj;
			if (id != other.id)
				return false;
			return true;
		}

}
