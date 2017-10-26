package com.example.controller;

import java.sql.SQLException;

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
	public String loginUser(@ModelAttribute User user, HttpServletRequest request, HttpServletResponse response, HttpSession ses){
		
		String email = user.getEmail();
		String password = user.getPassword();
		
		// validate email and password in spring form
		
		if (!UserDao.isValidEmailAddress(email)) {
			request.setAttribute("wrongEmail", true);
			return "login";
		}
		
		if (password.isEmpty()) {
			return "login";
		}
		
		try {
			if (ud.userExist(user)) {
				user = ud.getUser(email);
				ses.setAttribute("user", user);
				ses.setMaxInactiveInterval(-1); 
				// TODO update session to remain logged in and
				return "products";

			} else {
				request.setAttribute("wrongUser", true);
				return "login"; 
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
	public String registerUser(@ModelAttribute User user){
		
		try {
			ud.insertUser(user);
		} catch (SQLException e) {
			// TODO error page
		}		
		return "forward:index";
	}

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(HttpSession sess){
		sess.invalidate();
		return "index";
	}
	
	@RequestMapping(value="/profile", method = RequestMethod.GET)
	public String viewProfile(){
		return "profile";
	}
	
	@RequestMapping(value="/update", method = RequestMethod.GET)
	public String updateForm(Model m, HttpSession sess){		
		User u = (User) sess.getAttribute("user");
		m.addAttribute("user", u);
		return "updateProfile";
	}
	
	@RequestMapping(value="/profile", method = RequestMethod.POST)
	public String updateProfile(@ModelAttribute User user, HttpSession sess){
		try {
			user.setId(((User) sess.getAttribute("user")).getId());
			ud.updateUser(user);
			sess.setAttribute("user", user);
		} catch (SQLException e) {
			// TODO error page
			e.printStackTrace();
		}
		return "redirect:/user/profile";
	}
}
