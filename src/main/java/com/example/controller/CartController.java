package com.example.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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

	private Validator validator;

	@Autowired
	ProductDao productDao;
	@Autowired
	OrderDao orderDao;
	@Autowired
	DeliveryInfoDao deliveryInfoDao;
	@Autowired
	DBManager DBmanager;

	public CartController() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView viewCart(HttpSession s) {

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
		return new ModelAndView("cart");
	}

	@RequestMapping(value = "/removeFromCart/{productId}", method = RequestMethod.POST)
	public ModelAndView removeFromCart(HttpSession session, @PathVariable("productId") Integer productId) {

		Product productCurrent = null;
		HashMap<Product, Integer> cart = (HashMap<Product, Integer>) session.getAttribute("cart");
		try {
			productCurrent = productDao.getProduct(productId);
			cart.remove(productCurrent);
			session.setAttribute("cart", cart);
		} catch (SQLException e) {

		}
		return new ModelAndView("redirect:/cart/view");
	}

	@RequestMapping(value = "/updateCart", method = RequestMethod.GET)
	public ModelAndView updateCart(HttpSession session, HttpServletRequest request) {
		Long productId = Long.parseLong(request.getParameter("productId"));
		Product productCurrent = null;
		HashMap<Product, Integer> cart = (HashMap<Product, Integer>) session.getAttribute("cart");
		try {
			int quantity = Integer.parseInt(request.getParameter("count"));
			productCurrent = productDao.getProduct(productId);
			if (quantity > productCurrent.getInStock()) {
				quantity = productCurrent.getInStock();
				session.setAttribute("productNotEnoughQuantity", productCurrent);
			}
			cart.replace(productCurrent, quantity);
			session.setAttribute("cart", cart);
		} catch (SQLException e) {
			return new ModelAndView("error", "error", "Вътрешна грешка, моля да ни извините. Пробвайте отново.");
		}catch (NumberFormatException e) {
			return new ModelAndView("error", "error", "Моля въвеждайте валидни данни в полетата за количество.");
		}
		return new ModelAndView("forward:/cart/view");
	}

	@RequestMapping(value = "/deliveryInfo", method = RequestMethod.GET)
	public ModelAndView viewDeliveryInfo(HttpSession session, HttpServletRequest request, Model m) {

		DeliveryInfo selectedDelInfo = new DeliveryInfo();
		// check if logged
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return new ModelAndView("redirect:/user/login");
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
					selectedDelInfo = deliveries.get(idxDeliveryInfo);
					// session.setAttribute("selectedDelInfo", selectedDelInfo);
					// pazi go che shte trqbva
				}
			}

		} catch (SQLException e) {
			return new ModelAndView("error", "error", "Вътрешна грешка, моля да ни извините. Пробвайте отново.");
		}
		m.addAttribute("deliveryInfo", selectedDelInfo);
		session.setAttribute("cities", cities);
		return new ModelAndView("deliveryInfo");
	}

	@RequestMapping(value = "/deliveryInfo", method = RequestMethod.POST)
	public ModelAndView createNewOrder(HttpSession session, HttpServletRequest request,
			@ModelAttribute DeliveryInfo deliveryInfo, BindingResult result) {

		Set<ConstraintViolation<DeliveryInfo>> violations = validator.validate(deliveryInfo);
		for (ConstraintViolation<DeliveryInfo> cv : violations) {
			String propertyPath = cv.getPropertyPath().toString();
			String message = cv.getMessage();
			result.addError(new FieldError("deliveryInfo", propertyPath, message));
		}

		if (result.hasErrors()) {
			return new ModelAndView("deliveryInfo");
		}

		HashMap<Product, Integer> cart = (HashMap<Product, Integer>) session.getAttribute("cart");
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return new ModelAndView("redirect:/user/login");
		}
		Double priceForCart = (Double) session.getAttribute("priceForCart");

		Connection con = DBmanager.getAdminCon();
		ResultSet rs = null;

		try {
			con.setAutoCommit(false);
			deliveryInfoDao.insertDelivInfoOrder(deliveryInfo);
			Order order = new Order(user, LocalDateTime.now(), priceForCart, deliveryInfo, cart);
			orderDao.insertOrderForUser(order);
			long orderId = order.getOrderId();
			orderDao.insertProductsFromOrder(orderId, cart);
			return new ModelAndView("redirect:/cart/thanks");
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				return new ModelAndView("error", "error",
						"Вътрешна грешка, моля да ни извините. Проверете в профила си дали поръчката ви е приета.");
			}
			return new ModelAndView("error", "error",
					"Вътрешна грешка, моля да ни извините. Проверете в профила си дали поръчката ви е приета.");
		} catch (NotEnoughQuantityException eq) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				return new ModelAndView("error", "error", "Не достатъчно количество, моля да ни извините.");
			}
			return new ModelAndView("error", "error", "Не достатъчно количество, моля да ни извините.");

		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				return new ModelAndView("error", "error",
						"Вътрешна грешка, моля да ни извините. Проверете в профила си дали поръчката ви е приета.");
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					return new ModelAndView("error", "error",
							"Вътрешна грешка, моля да ни извините. Проверете в профила си дали поръчката ви е приета.");
				}
			}
		}
	}

	@RequestMapping(value = "/thanks", method = RequestMethod.GET)
	public ModelAndView thanksSimple(HttpSession session, HttpServletRequest request) {

		session.setAttribute("cart", null);
		session.setAttribute("priceForCart", null);
		session.setAttribute("productCurrent", null);
		User u = (User) session.getAttribute("user");

		return new ModelAndView("thanks");

	}

	@RequestMapping(value = "/thanks", method = RequestMethod.POST)
	public ModelAndView thanksInfo(HttpSession session, HttpServletRequest request) {

		User u = (User) session.getAttribute("user");

		try {
			TreeSet<Order> orders = orderDao.getOrdersForUser(u.getId());
			session.setAttribute("orders", orders);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ModelAndView("error", "error", "Вътрешна грешка, моля да ни извините. Пробвайте отново.");
		}
		return new ModelAndView("thanks");

	}

}
