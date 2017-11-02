package com.example.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

	public static String securePassword(String passwordToHash, String salt)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String generatedPassword = null;
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.update(salt.getBytes("UTF-8"));
		byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		generatedPassword = sb.toString();
		return generatedPassword;
	}

	// testing demo ....
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String a = Hasher.securePassword("t", "t@t.t");
		System.out.println(a);
		System.out.println(a.length());

	}
}
