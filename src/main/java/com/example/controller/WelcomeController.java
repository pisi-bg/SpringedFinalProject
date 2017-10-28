package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WelcomeController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String welcome(HttpSession ses) {
		ses.setMaxInactiveInterval(45 * 60); // 45 mins
		System.out.println("alooo");
		return "index";
	}
	
	
}
