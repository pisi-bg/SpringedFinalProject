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
import com.example.utils.exceptions.IllegalDiscountException;
import com.example.utils.exceptions.NoSuchCityException;
import com.example.utils.exceptions.NoSuchProductException;
import com.example.utils.exceptions.NotEnoughQuantityException;

@Controller
@RequestMapping(value = "/cart")
public class CartController {

	// validator for spring forms
	private Validator validator;
	public static ModelAndView sqlError;
	public static ModelAndView discountError; 
	public static ModelAndView urlError;

	@Autowired
	ProductDao productDao;
	@Autowired
	OrderDao orderDao;
	@Autowired
	DeliveryInfoDao deliveryInfoDao;
	@Autowired
	DBManager DBmanager;

	// constructor
	public CartController() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		this.sqlError = new ModelAndView("error", "error", "Вътрешна грешка, моля да ни извините. Пробвайте отново.");
		this.discountError = new ModelAndView("error", "error", "Не валидни данни за отстъпка на продукт!");
		this.urlError = new ModelAndView("error", "error", "Моля не пишете в адрес бара сами. Пробвайте отново.");
	}

	@RequestMapping(value = "/removeFromCart/{productId}", method = RequestMethod.POST)
	public ModelAndView removeFromCart(HttpSession session, @PathVariable("productId") Integer productId) {
		Product productCurrent = null;
		HashMap<Product, Integer> cart = (HashMap<Product, Integer>) session.getAttribute("cart");
		Iterator<Entry<Product, Integer>> it = cart.entrySet().iterator();
		while(it.hasNext()){
			Entry<Product, Integer> entry = it.next();
			if (entry.getKey().getId()==productId){
				it.remove();
				break;
			}
		}
//		try {
//			productCurrent = productDao.getProduct(productId);
//			if (productCurrent.equals((Product) session.getAttribute("productNotEnoughQuantity"))) {
//				session.removeAttribute("productNotEnoughQuantity");
//			}
//			cart.remove(productCurrent);
//			session.setAttribute("cart", cart);
//		} catch (SQLException e) {
//			return sqlError;
//		} catch (NoSuchProductException e) {
//			return new ModelAndView("error","error", e.getMessage());
//		} catch (IllegalDiscountException e) {
//			return discountError;
//		}
		return new ModelAndView("redirect:/cart/view");
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView viewCart(HttpSession s) {
		s.removeAttribute("productNotEnoughQuantity");
		HashMap<Product, Integer> cart = (HashMap<Product, Integer>) s.getAttribute("cart");
		if (cart != null) {
			Iterator<Entry<Product, Integer>> entryIt = cart.entrySet().iterator();
			while (entryIt.hasNext()) {
				Entry<Product, Integer> entry = entryIt.next();
				Product product = entry.getKey();
				int quantity = entry.getValue();
				if (quantity > product.getInStock()) {
					quantity = product.getInStock();
					cart.put(product, quantity);
				}
			}
			double priceForCart = Order.calculatePriceForCart(cart);
			s.setAttribute("priceForCart", priceForCart);
		} else {
			cart = new HashMap<>();
		}
		return new ModelAndView("cart");
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
			double priceForCart = Order.calculatePriceForCart(cart);
			session.setAttribute("priceForCart", priceForCart);
			session.setAttribute("cart", cart);
		} catch (SQLException e) {
			return sqlError;
		} catch (NumberFormatException e) {
			return new ModelAndView("error", "error", "Моля въвеждайте валидни данни в полетата за количество.");
		} catch (NoSuchProductException e) {
			return new ModelAndView("error", "error", e.getMessage());
		} catch (IllegalDiscountException e) {
			return discountError;
		}
		return new ModelAndView("cart");
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
			deliveries = deliveryInfoDao.getDeliveriesInfosForUser(user.getId());
			if (deliveries != null && !deliveries.isEmpty()) {
				session.setAttribute("deliveries", deliveries);
				if (idx != null) {
					Integer idxDeliveryInfo = Integer.parseInt(idx);
					selectedDelInfo = deliveries.get(idxDeliveryInfo);
				}
			}
		} catch (SQLException e) {
			return sqlError;
		} catch (NumberFormatException e) {
			return new ModelAndView("error", "error", "Моля въвеждайте валидни данни в полетата.");
		}
		m.addAttribute("deliveryInfo", selectedDelInfo);
		session.setAttribute("cities", cities);
		return new ModelAndView("deliveryInfo");
	}

	@RequestMapping(value = "/deliveryInfo", method = RequestMethod.POST)
	public ModelAndView createNewOrder(HttpSession session, HttpServletRequest request,
			@ModelAttribute DeliveryInfo deliveryInfo, BindingResult result) {
		session.removeAttribute("productNotEnoughQuantity");
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
			deliveryInfoDao.insertDelivInfoOrder(deliveryInfo); //user, LocalDateTime.now(), priceForCart, deliveryInfo, cart
			Order order = new Order().setUser(user)
									.setDateTime(LocalDateTime.now())
									.setFinalPrice(priceForCart)
									.setDeliveryInfo(deliveryInfo)
									.setProducts(cart);
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

		} catch (NoSuchCityException e) {
			return new ModelAndView("error", "error", "Нямаме град с такова име в системата си. Моля не преправяйте страницата ни.");
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				return new ModelAndView("error", "error","Вътрешна грешка, моля да ни извините. Проверете в профила си дали поръчката ви е приета.");
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
		} catch (IllegalDiscountException e) {
			return discountError;
		}
		return new ModelAndView("thanks");

	}

}
