package com.example.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.db.CartDao;
import com.example.model.db.DeliveryInfoDao;
import com.example.model.db.OrderDao;
import com.example.model.db.ProductDao;
import com.example.model.pojo.DeliveryInfo;
import com.example.model.pojo.Product;
import com.example.model.pojo.User;

@Controller
@RequestMapping(value = "/cart")
public class CartController {

	@Autowired
	CartDao cartDao;
	@Autowired
	ProductDao productDao;
	@Autowired
	OrderDao orderDao;
	@Autowired
	DeliveryInfoDao deliveryInfoDao;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viewCart(HttpSession s) {

		HashMap<Product, Integer> cart = (HashMap<Product, Integer>) s.getAttribute("cart");
		if (cart != null) {
			double priceForCart = cartDao.calculatePriceForCart(cart);
			s.setAttribute("priceForCart", priceForCart);
		} else {
			cart = new HashMap<>();
		}
		return "cart";
	}

	@RequestMapping(value = "/removeFromCart/{productId}", method = RequestMethod.POST)
	public String removeFromCart(HttpSession session, @PathVariable("productId") Integer productId) {

		Product productCurrent = null;
		HashMap<Product, Integer> cart = (HashMap<Product, Integer>) session.getAttribute("cart");
		try {
			productCurrent = productDao.getProduct(productId);
			cart.remove(productCurrent);
			session.setAttribute("cart", cart);
			double priceForCart = cartDao.calculatePriceForCart(cart);
			session.setAttribute("priceForCart", priceForCart);
		} catch (SQLException e) {
			return "error";
		}
		return "redirect:/cart/view";
	}

	@RequestMapping(value = "/updateCart", method = RequestMethod.GET)
	public String updateCart(HttpSession session, HttpServletRequest request) {
		Long productId = Long.parseLong(request.getParameter("productId"));
		Product productCurrent = null;
		HashMap<Product, Integer> cart = (HashMap<Product, Integer>) session.getAttribute("cart");
		int quantity = Integer.parseInt(request.getParameter("count"));
		try {
			productCurrent = productDao.getProduct(productId);
			cart.replace(productCurrent, quantity);
			session.setAttribute("cart", cart);
		} catch (SQLException e) {
			return "error";
		}
		return "forward:/cart/view";
	}

	@RequestMapping(value = "/deliveryInfo", method = RequestMethod.GET)
	public String viewDeliveryInfo(HttpSession session, HttpServletRequest request, Model m) {

		// check if logged
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}
		ArrayList<String> cities = null;
		ArrayList<DeliveryInfo> deliveries = null;		
		Integer indexDeliveryInfoId = (Integer) request.getAttribute("indexDeliveryInfoId");
		try {
			cities = orderDao.getCitiesNames();
			deliveries = deliveryInfoDao.getListDeliveryInfosForUser(user.getId());
			if (deliveries != null && !deliveries.isEmpty()) {
				session.setAttribute("deliveries", deliveries);
				if (indexDeliveryInfoId != null) {
					DeliveryInfo selectedDelInfo = deliveries.get(indexDeliveryInfoId);
					// session.setAttribute("selectedDelInfo", selectedDelInfo);
					m.addAttribute("deliveryInfo", selectedDelInfo);
				}
			} else {
				m.addAttribute("deliveryInfo", new DeliveryInfo());
			}

		} catch (SQLException e) {
			return "error";
		}
		request.setAttribute("cities", cities);
		return "deliveryInfo";
	}

	@RequestMapping(value = "/deliveryInfo", method = RequestMethod.POST)
	public String addExistingDeliveryInfo(HttpSession session, HttpServletRequest request) {
		int indexDeliveryInfoId = (int) session.getAttribute("idxDeliveryInfo");
		request.setAttribute("indexDeliveryInfoId", indexDeliveryInfoId);
		return "redirect:/deliveryInfo";
	}

	@RequestMapping(value = "/newDeliveryInfo", method = RequestMethod.POST)
	public String addNewDeliveryInfo(HttpSession session, HttpServletRequest request,
			@ModelAttribute DeliveryInfo deliveryInfo) {

		return "deliveryInfo";
	}

}
