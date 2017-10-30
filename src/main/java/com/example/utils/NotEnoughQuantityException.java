package com.example.utils;

@SuppressWarnings("serial")
public class NotEnoughQuantityException extends Exception {

	public static final String NOT_ENOUGH_QUANTITY = "Not enough quantity";

	public NotEnoughQuantityException(String message) {
		super(message);
	}
}
