package com.example.model.pojo;

import java.util.Collections;
import java.util.Set;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

public class User {
	
	private long id;
	@NotBlank(message="Ivalid input.")
//	@Required
	private String firstName;
	@NotBlank(message="Ivalid input.")
	private String lastName;
	@NotBlank(message="Please enter an email")
	@Email(message="Please enter a valid email.")
	private String email;
	@NotBlank(message="")
	@Size(min=6,max=50 , message="Ivalid password. Length must be more than 6 characters.")
	private String password;
	private boolean isMale;
	private boolean isAdmin;
	private Set<Product> favorites;

				// ***** GETTERS *** //

	// check if user is admin
	public boolean isAdmin() {
		return isAdmin;
	}

	// return user email
	public String getEmail() {
		return email;
	}

	// return user password
	public String getPassword() {
		return password;
	}

	// return true if user is male and false if user is female
	public boolean getIsMale() {
		return this.isMale;
	}

	// return user first name
	public String getFirstName() {
		return firstName;
	}

	// return user last name
	public String getLastName() {
		return lastName;
	}
	
	// returns user id
	public long getId() {
		return id;
	}

	// returns user favorites
	public Set<Product> getFavorites() {
		return Collections.unmodifiableSet(favorites);
	}

	//** SETTERS **//
	
	// set id which is returned by the database
	public User setId(long id) {
		this.id = id;
		return this;
	}
	
	public User setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public User setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public User setIsMale(boolean isMale) {
		this.isMale = isMale;
		return this;
	}

	public User setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
		return this;
	}

	public User setFavorites(Set<Product> favorites) {
		this.favorites = favorites;
		return this;
	}


	
				// *** ADDITIONAL METHODS **//
	/**
	 * Add <code>Product</code> to collection of favorite products;
	 * @param product POJO of type <code>Product</code>
	 */
	public void addToFavorites(Product product) {
		this.favorites.add(product);
	}

	/**
	 * Removes <code>Product</code> from collection of favorite products
	 * @param product POJO of type <code>Product</code>
	 */
	public void removeFromFavorites(Product product) {
		this.favorites.remove(product);
	}

	/**
	 * Check if given product is already into collection ot favorite products
	 * @param product POJO of type <code>Product</code>
	 * @return <code>boolean</code> true - if given product is into user favorites, false - otherwise;
	 */
	public boolean hasInFavorites(Product product) {
		if (product != null) {
			return favorites.contains(product);
		}
		return false;
	}

	
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", isMale=" + isMale + ", isAdmin=" + isAdmin + ", favorites=" + favorites
				+ "]";
	}	

}
