package com.example.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;


import com.example.WebInitializer;

public class ImageProvider {
	
	public static void proceedProductPicture(String url, HttpServletResponse response) throws IOException{
		
		File image = new File(WebInitializer.LOCATION + url);		
		OutputStream out = response.getOutputStream();
		Files.copy(image.toPath(), out);
	}
	
	public static void proceedBrandPicture(String url, HttpServletResponse response) throws IOException{
		File image = new File(WebInitializer.BRAND_LOCATION + url);		
		OutputStream out = response.getOutputStream();
		Files.copy(image.toPath(), out);
	}

}
