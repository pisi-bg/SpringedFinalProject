package com.example.model.pojo;

import java.io.Serializable;

public class Product implements Serializable {

	private long id;
	private String name;
	private String description;
	private double price;
	private String animal;
	private String category;
	private String brand;
	private String brandImage;
	private double rating;
	private int inStock;
	private String image;
	private int discount; // in percent

	public Product(){		
	}
	
	public Product(long id, String name, String description, double price, String animal, String category, String brand,
			String brandImage, double rating, int isStock, String image, int discount) {
		this(id, name, description, price, discount, animal, category, image, rating, brand);
		this.brandImage = brandImage;
		this.inStock = isStock;
	}

	// constructor to retrieve short info from DB for orders history

	public Product(long id, String name, String description, double price, int discount, String animal, String category,
			String image, double rating, String brand) {
		this(id, name, description, price, category, rating, image);
		this.discount = discount;
		this.animal = animal;
		this.brand = brand;
	}

	public Product(long id, String name, String description, double price, String category, double rating,
			String image) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.rating = rating;
		this.image = image;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setDiscount(int discount) {
		if (discount >= 0 && discount <= 100) {
			this.discount = discount;
		}
	}

	// for demo purpose
	@Override
	public String toString() {
		return this.name + " " + this.description + " " + this.animal + " " + this.category + " " + this.price + " " + this.discount;
	}

	@Override
	public int hashCode() {
		return (int) this.id;
	}

	@Override
	public boolean equals(Object o) {
		return this.id == ((Product) o).getId();
	}

	public double calcDiscountedPrice() {
		double newPrice = price * ((100 - discount) / 100.0);

		return newPrice;
	}

	// getters
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

	public String getBrandImage() {
		return brandImage;
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
	
	//   ** SETTERS ****
	
											// TODO VALIDATION !!!!!

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

	public Product setBrandImage(String brandImage) {
		this.brandImage = brandImage;
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
	

}
