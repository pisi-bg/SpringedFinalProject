package com.example.model.pojo;

import java.util.Collections;
import java.util.Set;

import org.springframework.stereotype.Component;

public class User {
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private boolean isMale;
	private boolean isAdmin;
	private Set<Product> favorites;

	// constructor with email and pass for login
	public User(String email, String pass) {
		this.email = email;
		this.password = pass;
	}
	
	// constructor for spring form
	public User() {
		
	}

	// constructor with all fields
	public User(long id, String firstName, String lastName, String email, String password, boolean isMale,
			boolean isAdmin, Set<Product> favorites) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.isMale = isMale;
		this.isAdmin = isAdmin;
		this.favorites = favorites;
	}

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

	// set id which is returned by the database
	public void setId(long id) {
		this.id = id;
	}

	// returns user id
	public long getId() {
		return id;
	}

	public Set<Product> getFavorites() {
		return Collections.unmodifiableSet(favorites);
	}

	// add favorites after adding it to DB
	public void addToFavorites(Product p) {
		this.favorites.add(p);
	}

	public void removeFromFavorites(Product p) {
		this.favorites.remove(p);
	}

	public boolean hasInFavorites(Product p) {
		if (p != null) {
			return favorites.contains(p);
		}
		return false;
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

	public void setPassword(String password) {
		this.password = password;
	}

	public void setMale(boolean isMale) {
		this.isMale = isMale;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setFavorites(Set<Product> favorites) {
		this.favorites = favorites;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", isMale=" + isMale + ", isAdmin=" + isAdmin + ", favorites=" + favorites
				+ "]";
	}
	
	
	

}
