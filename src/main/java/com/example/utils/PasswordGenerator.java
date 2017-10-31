package com.example.utils;

import java.util.Random;

public class PasswordGenerator {
	private static final String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	
	public static String getRandomPass(){
		String pass = new String();
		for (int i = 0; i < 8; i++) {
			char c = symbols.charAt(new Random().nextInt(symbols.length()));
			pass = pass.concat(String.valueOf(c));
		}
		return pass;
	}
	
}
