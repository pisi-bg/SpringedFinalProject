package com.example.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.db.UserDao;
import com.example.model.pojo.User;



@Controller
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	UserDao ud;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(Model m){
		User u = new User();
		m.addAttribute("user", u);
		return "login";
	}
	
	// this method check if login form is correctly filled and then check if
	// user exist in the database
	@RequestMapping(value= "/login", method = RequestMethod.POST)
	public String loginUser(HttpServletRequest request, HttpServletResponse response, HttpSession ses){
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
				
		// validate email and password in spring form
		
		if (!UserDao.isValidEmailAddress(email)) {
//			response.getWriter().append("Invalid email");
//			response.
			return "login";
		}
		if (password.isEmpty()) {
//			response.getWriter().append("Empty password");
			return "login";
		}
		
		User user = new User(email, password);
		try {
			if (ud.userExist(user)) {
				user = ud.getUser(email);
				ses.setAttribute("user", user);
				// TODO update session to remain logged in and
				return "products";

			} else {
				// return to login page with massage for wrong data
				return "forward:login";
			}
		} catch (SQLException e) {
			
			return "error";
		}
			
	}
	
	@RequestMapping(value="/register", method = RequestMethod.GET)
	public String register(Model m){
		User u = new User();
		//validate spring form
		m.addAttribute("user", u);
		return "register";
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute User user, HttpServletRequest request){
		
		try {
			ud.insertUser(user);
		} catch (SQLException e) {
			// TODO error page
		}		
		return "forward:index";
	}
}
