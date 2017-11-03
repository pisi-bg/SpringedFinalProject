package com.example.utils;


public class StringValidator {
	
	public static boolean validate(String string){
		if(string != null){
			string = string.trim();
			if( string.isEmpty()){
				return false;
			}
		}else {
			return false;
		}
		return true;
	}

}
