package com.example.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.db.DBManager;
import com.example.model.db.DeliveryInfoDao;
import com.example.model.db.OrderDao;
import com.example.model.db.ProductDao;
import com.example.model.pojo.DeliveryInfo;
import com.example.model.pojo.Order;
import com.example.model.pojo.Product;
import com.example.model.pojo.User;
import com.example.utils.NotEnoughQuantityException;

@Controller
@RequestMapping(value = "/cart")
public class CartController {

	// @Autowired
	// CartDao cartDao;
	@Autowired
	ProductDao productDao;
	@Autowired
	OrderDao orderDao;
	@Autowired
	DeliveryInfoDao deliveryInfoDao;
	@Autowired
	DBManager DBmanager;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viewCart(HttpSession s) {

		HashMap<Product, Integer> cart = (HashMap<Product, Integer>) s.getAttribute("cart");

		if (cart != null) {
			double priceForCart = 0;
			Iterator<Entry<Product, Integer>> entryIt = cart.entrySet().iterator();
			while (entryIt.hasNext()) {
				Entry<Product, Integer> entry = entryIt.next();
				Product product = entry.getKey();
				int quantity = entry.getValue();
				if (product.getInStock() == 0) {
					entryIt.remove();
					break;
				} else if (quantity > product.getInStock()) {
					quantity = product.getInStock();
					cart.put(product, quantity);
				}
				double productPrice = product.getPrice();
				if (product.getDiscount() != 0) {
					productPrice = product.calcDiscountedPrice();
				}
				priceForCart += (productPrice * quantity);
			}
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
			if (quantity > productCurrent.getInStock()) {
				quantity = productCurrent.getInStock();
				session.setAttribute("productNotEnoughQuantity", productCurrent);
			}
			cart.replace(productCurrent, quantity);
			session.setAttribute("cart", cart);
		} catch (SQLException e) {
			return "error";
		}
		return "forward:/cart/view";
	}

	@RequestMapping(value = "/deliveryInfo", method = RequestMethod.GET)
	public String viewDeliveryInfo(HttpSession session, HttpServletRequest request) {

		// check if logged
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}
		ArrayList<String> cities = null;
		ArrayList<DeliveryInfo> deliveries = null;

		String idx = request.getParameter("idxDeliveryInfo");

		try {
			cities = orderDao.getCitiesNames();
			deliveries = deliveryInfoDao.getListDeliveryInfosForUser(user.getId());
			if (deliveries != null && !deliveries.isEmpty()) {
				session.setAttribute("deliveries", deliveries);
				if (idx != null) {
					Integer idxDeliveryInfo = Integer.parseInt(idx);
					DeliveryInfo selectedDelInfo = deliveries.get(idxDeliveryInfo);
					session.setAttribute("selectedDelInfo", selectedDelInfo);
				}
			}

		} catch (SQLException e) {
			return "error";
		}
		request.setAttribute("cities", cities);
		return "deliveryInfo";
	}

	@RequestMapping(value = "/newOrder", method = RequestMethod.POST)
	public String createNewOrder(HttpSession session, HttpServletRequest request) {

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String phone = request.getParameter("phone");
		String city = request.getParameter("city");
		String address = request.getParameter("address");
		int zip = Integer.parseInt(request.getParameter("zip"));
		String note = request.getParameter("note");
		// TODO validation

		DeliveryInfo deliveryInfo = new DeliveryInfo(address, zip, city, firstName, lastName, phone, note);

		HashMap<Product, Integer> cart = (HashMap<Product, Integer>) session.getAttribute("cart");
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}
		Double priceForCart = (Double) session.getAttribute("priceForCart");

		Connection con = DBmanager.getAdminCon();
		ResultSet rs = null;

		try {
			con.setAutoCommit(false);
			deliveryInfoDao.insertDelivInfoOrder(deliveryInfo);
			Order order = new Order(user, LocalDateTime.now(), priceForCart, deliveryInfo.getDeliveryInfoId(), cart);
			orderDao.insertOrderForUser(order);
			long orderId = order.getOrderId();
			orderDao.insertProductsFromOrder(orderId, cart);
			return "redirect:/cart/thanks";
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO error page....
				return "error";
			}
			return "error";
		} catch (NotEnoughQuantityException eq) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO error page....
				return "error";
			}
			return "error";

		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				return "error";
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					return "error";
				}
			}
		}
	}

	@RequestMapping(value = "/thanks", method = RequestMethod.GET)
	public String thanksSimple(HttpSession session, HttpServletRequest request) {

		session.setAttribute("cart", null);
		session.setAttribute("priceForCart", null);
		session.setAttribute("productCurrent", null);
		User u = (User) session.getAttribute("user");

		session.setAttribute("favorites", u.getFavorites());
		return "thanks";

	}

	@RequestMapping(value = "/thanks", method = RequestMethod.POST)
	public String thanksInfo(HttpSession session, HttpServletRequest request) {

		User u = (User) session.getAttribute("user");

		try {
			TreeSet<Order> orders = orderDao.getOrdersForUser(u.getId());
			session.setAttribute("orders", orders);
		} catch (SQLException e) {
			e.printStackTrace();
			return "error3";
		}
		return "thanks";

	}

}
