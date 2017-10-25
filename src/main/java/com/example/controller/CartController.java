package com.example.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.dao.CartDao;
import model.pojo.Product;

@Controller
@RequestMapping(value = "/cart")
public class CartController {

	@RequestMapping(value = "/cart", method = RequestMethod.POST)
	public String login(HttpRequest request, HttpSession s) {

		// retrieve all products that have been chosen for purchase
		Object oCart = s.getAttribute("cart");
		HashMap<Product, Integer> cart = null;
		if (oCart != null) {
			cart = (HashMap<Product, Integer>) oCart;
			double priceForCart = CartDao.getInstance().calculatePriceForCart(cart);
			s.setAttribute("priceForCart", priceForCart);
		}
		return "cart";
	}

}
