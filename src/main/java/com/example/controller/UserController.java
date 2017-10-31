package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.WebInitializer;
import com.example.model.db.OrderDao;
import com.example.model.db.ProductDao;
import com.example.model.db.UserDao;
import com.example.model.pojo.Order;
import com.example.model.pojo.Product;
import com.example.model.pojo.User;
import com.example.utils.EmailSender;

@Controller
@MultipartConfig
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	UserDao ud;

	@Autowired
	ProductDao pd;

	@Autowired
	OrderDao orderDao;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model m) {
		User u = new User();
		m.addAttribute("user", u);
		return "login";
	}

	// this method check if login form is correctly filled and then check if
	// user exist in the database
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUser(@ModelAttribute User user, HttpServletRequest request, HttpServletResponse response,
			HttpSession ses) {

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
				ses.setMaxInactiveInterval(-1); // infinity session
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

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model m) {
		User u = new User();
		// validate spring form
		m.addAttribute("user", u);
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute User user) {

		try {
			ud.insertUser(user);
		} catch (SQLException e) {
			// TODO error page
		}
		return "forward:index";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession sess) {
		sess.invalidate();
		return "index";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String viewProfile(HttpSession session) {

		User u = (User) session.getAttribute("user");
		if (u == null) {
			return "redirect:/user/login";
		}
		if (!u.getFavorites().isEmpty()) {
			session.setAttribute("favorites", u.getFavorites());
		}
		return "profile";
	}

	@RequestMapping(value = "/profile/showOrders", method = RequestMethod.POST)
	public String viewOrders(HttpSession session) {

		User u = (User) session.getAttribute("user");
		if (u == null) {
			return "redirect:/user/login";
		}
		try {
			TreeSet<Order> orders = orderDao.getOrdersForUser(u.getId());
			if (!orders.isEmpty()) {
				session.setAttribute("orders", orders);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "error3";
		}
		return "profile";
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateForm(Model m, HttpSession sess, HttpServletRequest req) {
		User u = (User) sess.getAttribute("user");
		req.setAttribute("update", 1);
		m.addAttribute("user", u);
		return "updateProfile";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String updateProfile(@ModelAttribute User user, HttpSession sess) {
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

	@RequestMapping(value = "/favorites", method = RequestMethod.GET)
	public String viewFavorites(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}
		if (!user.getFavorites().isEmpty()) {
			session.setAttribute("favorites", user.getFavorites());
		}
		return "favorites";
	}

	@RequestMapping(value = "/contactForm", method = RequestMethod.GET)
	public String sendMail(HttpSession session, Model m) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}

		// session.setAttribute("favorites", user.getFavorites());

		return "contactForm";
	}

	@RequestMapping(value = "/admin/removeProduct", method = RequestMethod.GET)
	public String removeProduct(HttpSession sess) {

		if (sess.getAttribute("user") == null) {
			return "index";
		}

		User user = (User) sess.getAttribute("user");

		if (user == null || !user.isAdmin()) {
			return "forward:index";
		}

		Product pro = (Product) sess.getAttribute("productCurrent");
		if (pro != null) {
			try {
				pd.removeProduct(pro);
			} catch (SQLException e) {
				// TODO error page
				e.printStackTrace();
			}
			return "index";
		} else {

			// make an error page ....
			return "error";
		}
	}

	@RequestMapping(value = "/admin/addProduct", method = RequestMethod.GET)
	public String addProductForm(HttpSession sess) {
		User user = (User) sess.getAttribute("user");
		if (user == null || !user.isAdmin()) {
			return "redirect:/user/login";
		}
		try {
			List<String> animals = pd.getAnimals();
			sess.setAttribute("animals", animals);
			// mainCategories = pd.getMainCategories();
			// sess.setAttribute("mainCategories", mainCategories);
			List<String> subCategories = pd.getCategories();
			sess.setAttribute("subCategories", subCategories);
			List<String> brands = pd.getBrands();
			sess.setAttribute("brands", brands);
		} catch (SQLException e) {
			return "error";
		}
		return "addproduct";
	}

	@RequestMapping(value = "/admin/addProduct", method = RequestMethod.POST)
	public String addProduct(HttpServletResponse resp, HttpServletRequest req,
			@RequestParam("image") MultipartFile file) {

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return "encodingError";
		}
		// check for categories capslock s

		String name = req.getParameter("name");
		String animal = req.getParameter("animal");
		String category = req.getParameter("category");
		double price = Double.parseDouble(req.getParameter("price"));
		String description = req.getParameter("description");
		String brand = req.getParameter("brand");
		int instock = Integer.parseInt(req.getParameter("instock_count"));
		int discount = 0;
		if (req.getParameter("discount") != null) {
			discount = Integer.parseInt(req.getParameter("discount"));
		}

		String imageFileName = name.replaceAll(" ", "");
		String imageType = ".jpg";
		String imageURL = imageFileName.concat(imageType);

		File imageFile = new File(WebInitializer.LOCATION + imageFileName + imageType);

		try {
			file.transferTo(imageFile);
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Product p = new Product();
		p.setName(name);
		p.setAnimal(animal);
		p.setCategory(category);
		p.setPrice(price);
		p.setDescription(description);
		p.setBrand(brand);
		p.setInStock(instock);
		p.setDiscount(discount);
		p.setImage(imageURL);

		try {
			pd.addProduct(p);
		} catch (SQLException e) {
			e.printStackTrace();
			return "error";
		}
		// return "index";
		return "redirect:/user/admin/addProduct";
	}

	@RequestMapping(value = "/admin/addBrand", method = RequestMethod.POST)
	public String addBrand(HttpServletResponse resp, HttpServletRequest req,
			@RequestParam("newBrandImage") MultipartFile brandFile) {

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return "encodingError";
		}

		String brandName = req.getParameter("newBrandname");

		String imageFileName = brandName.replaceAll(" ", "");
		String imageType = ".jpg";
		String imageURL = imageFileName.concat(imageType);

		File imageFile = new File(WebInitializer.BRAND_LOCATION + imageFileName + imageType);

		try {
			brandFile.transferTo(imageFile);
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			pd.insertBrand(brandName, imageURL);
		} catch (SQLException e) {
			e.printStackTrace();
			return "error";
		}
		// return "index";
		return "redirect:/user/admin/addProduct";
	}

	@RequestMapping(value = "/admin/quantity", method = RequestMethod.POST)
	public String addQuantity(HttpSession sess, HttpServletRequest req) {
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		if (quantity < 1) {

			return "error";
		}
		Product pro = (Product) sess.getAttribute("productCurrent");
		try {
			pd.addQuantity(pro.getId(), quantity);
		} catch (SQLException e) {
			e.printStackTrace();
			return "sqlError";
		}
		return "index";
	}

	@RequestMapping(value = "/admin/discount", method = RequestMethod.POST)
	public String addDiscount(HttpSession sess, HttpServletRequest req) {
		int discount = Integer.parseInt(req.getParameter("discount"));
		if (discount < 0 || discount > 99) {
			return "error";
		}
		Product pro = (Product) sess.getAttribute("productCurrent");
		long id = pro.getId();
		try {
			pd.setInPromotion(id, discount);
		} catch (SQLException e) {
			return "error";
		}
		return "products";
	}

	@RequestMapping(value = "/password", method = RequestMethod.GET)
	public String forgottenPassword() {
		return "password";
	}

	@RequestMapping(value = "/password", method = RequestMethod.POST)
	public String sendPassword(HttpServletRequest req) {
		String email = req.getParameter("email");

		if (email == null || !UserDao.isValidEmailAddress(email)) {
			return "error1";
		}
		try {
			User user = ud.getUser(email);
			EmailSender.passwordTo(user);
		} catch (SQLException e) {
			return "error2";
		}
		return "index";
	}

}
