package com.example.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.db.CartDao;
import com.example.model.pojo.Product;

@Controller
@RequestMapping(value = "/cart")
public class CartController {

	@Autowired
	CartDao cd;
	

	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String cart() {

//		Object oCart = s.getAttribute("cart");
//		HashMap<Product, Integer> cart = null;
//		if (oCart != null) {
//			cart = (HashMap<Product, Integer>) oCart;
//			double priceForCart = cd.calculatePriceForCart(cart);
//			s.setAttribute("priceForCart", priceForCart);
//		} else {
//			cart = new HashMap<>();
//		}
		return "cart";

		// retrieve all products that have been chosen for purchase
		// Object oCart = s.getAttribute("cart");
		// System.out.println("opalaaAAAAAAAAAAAAAAAAAAA");
		// HashMap<Product, Integer> cart = null;
		// if (oCart != null) {
		// cart = (HashMap<Product, Integer>) oCart;
		// double priceForCart = cd.calculatePriceForCart(cart);
		// s.setAttribute("priceForCart", priceForCart);
		// } else {
		// cart = new HashMap<>();
		// }
		// return "cart";
	}
	
	

}
