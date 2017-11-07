package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.WebInitializer;
import com.example.model.db.OrderDao;
import com.example.model.db.ProductDao;
import com.example.model.db.UserDao;
import com.example.model.pojo.Order;
import com.example.model.pojo.Product;
import com.example.model.pojo.User;
import com.example.utils.EmailSender;
import com.example.utils.Hasher;
import com.example.utils.PasswordGenerator;
import com.example.utils.StringValidator;
import com.example.utils.exceptions.IllegalDiscountException;
import com.example.utils.exceptions.NoSuchProductException;
import com.example.utils.exceptions.NotSuchUserException;

@Controller
@MultipartConfig
@RequestMapping(value = "/user")
public class UserController {

	public static final String ALL_DIGITS_REGEX = "[0-9]{1,6}+";
	public static final String ALL_DIGITS_REGEX_DISCOUNT = "[0-9]{1,2}+";
	public static final String ALL_DOUBLE_DIGIT_REGEX = "([0-9]{1,13}(\\.[0-9]*)?){1}";
	private Validator validator;
	
	@Autowired
	private UserDao ud;

	@Autowired
	private ProductDao pd;

	@Autowired
	private OrderDao orderDao;

	public UserController() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(Model m) {
		User u = new User();
		m.addAttribute("user", u);
		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginUser(@ModelAttribute User user, HttpServletRequest request, HttpSession session) {
		
		String email = user.getEmail();
		if (!UserDao.isValidEmailAddress(email)) {
			request.setAttribute("wrongEmail", true);
			return new ModelAndView("login");
		}
		try {
			String password = user.getPassword();
			password = Hasher.securePassword(password, email);
			if (password.isEmpty()) {
				return new ModelAndView("login");
			}
			user.setPassword(password);
			if (ud.userExist(user)) {
				user = ud.getUser(email);
				session.setAttribute("user", user);
				session.setMaxInactiveInterval(-1); // infinity session
				return new ModelAndView("redirect:/index");
			} else {
				request.setAttribute("wrongUser", true);
				return new ModelAndView("login");
			}
		} catch (SQLException e) {
			return CartController.sqlError;
		} catch (NoSuchAlgorithmException e) {
			return CartController.sqlError;
		} catch (UnsupportedEncodingException e) {
			return CartController.sqlError;
		} catch (IllegalDiscountException e) {
			return CartController.discountError;
		}
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(Model m) {
		User u = new User();
		m.addAttribute("user", u);
		return new ModelAndView("register");
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerUser(HttpSession session, @ModelAttribute User user, BindingResult result) {
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		for(ConstraintViolation<User> cv : violations){
			String propertyPath = cv.getPropertyPath().toString();
			String message = cv.getMessage();
			result.addError(new FieldError("user", propertyPath, message));
		}		
		if(result.hasErrors()){
			return new ModelAndView("register");
		}
		try {
			String userpass = user.getPassword();
			userpass = Hasher.securePassword(userpass, user.getEmail());
			user.setPassword(userpass);
			ud.insertUser(user);			
		} catch (SQLException e) {
			session.setAttribute("regError", true);
			return new ModelAndView("redirect:/user/register");
		} catch (NoSuchAlgorithmException e) {
			return CartController.sqlError;
		} catch (UnsupportedEncodingException e) {
			return CartController.sqlError;
		}
		if (session.getAttribute("regError") != null) {
			session.removeAttribute("regError");
		}
		return new ModelAndView("redirect:/index");
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpSession sess) {
		sess.invalidate();
		return new ModelAndView("redirect:/index");
	}

	
	@RequestMapping(value = "/profile/showOrders", method = RequestMethod.POST)
	public ModelAndView viewOrders(HttpSession session) {

		User u = (User) session.getAttribute("user");
		if (u == null) {
			return new ModelAndView("redirect:/user/login");
		}
		try {
			TreeSet<Order> orders = orderDao.getOrdersForUser(u.getId());
			if (!orders.isEmpty()) {
				session.setAttribute("orders", orders);
			}
		} catch (SQLException e) {
			return CartController.sqlError;
		} catch (IllegalDiscountException e) {
			return CartController.discountError;
		}
		return new ModelAndView("profile");
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView updateForm(Model m, HttpSession sess, HttpServletRequest req) {
		User u = (User) sess.getAttribute("user");
		m.addAttribute("user", u);
		return new ModelAndView("updateProfile");
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView viewProfile(HttpSession session, HttpServletRequest req) {
		session.removeAttribute("orders");
		
		User u = (User) session.getAttribute("user");
		if (u == null) {
			return new ModelAndView("redirect:/user/login");
		}
		if (!u.getFavorites().isEmpty()) {
			session.setAttribute("favorites", u.getFavorites());
		}
		return new ModelAndView("profile");
	}


	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public ModelAndView updateProfile(@ModelAttribute User user,HttpServletRequest req , HttpSession sess, BindingResult result) {
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		
		for(ConstraintViolation<User> cv : violations){
			String propertyPath = cv.getPropertyPath().toString();
			String message = cv.getMessage();
			result.addError(new FieldError("user", propertyPath, message));
		}
		
		if(result.hasErrors()){		
			return new ModelAndView("redirect:/user/profile");
		}
		
		ModelAndView m = this.updateProfile(user, sess);
		if(m.getViewName().equals("redirect:/user/profile")){
			m.setViewName("redirect:/index");
		}
		return m;
	}

	@RequestMapping(value = "/favorites/{page}", method = RequestMethod.GET)
	public ModelAndView viewFavorites(HttpSession session, @PathVariable("page") Integer page) {
		session.removeAttribute("categoriesD");
		session.removeAttribute("subCategories");
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return new ModelAndView("login");
		}
		
		int paging = page == null ? 0 : page;
		
		PagedListHolder<Product> productList = (PagedListHolder<Product>) session.getAttribute("productPage");
		String url = "/user/favorites";
		List<Product> products = new ArrayList<>();
		products.addAll(user.getFavorites());
		
		if(products.isEmpty()){
			session.setAttribute("favorite", false);
		}else {
			session.setAttribute("favorite", true);
		}
		
		if(productList != null){
			if(!productList.getSource().equals(products)){
				productList.setSource(products);
			}
		}else {
			productList = new PagedListHolder<>(products);
		}
		productList.resort();
		productList.setPageSize(ProductController.ITEMS_PER_PAGE);
		
		if (paging >= 0) {
			productList.setPage(paging);
		} else {
			if (paging == -1) {
				productList.nextPage();
			}
			if (paging == -2) {
				productList.previousPage();
			}
			if (paging < -2) {
				return CartController.urlError;
			}
		}
		
		session.setAttribute("url", url);
		session.setAttribute("products", products);
		session.setAttribute("productPage", productList);
		
		
		return new ModelAndView("products", "productPage", productList);
	}

	@RequestMapping(value = "/contactForm", method = RequestMethod.GET)
	public ModelAndView giveContactForm(HttpSession session, Model m) {		
		return new ModelAndView("contactForm");
	}
	
	@RequestMapping(value="/contactForm", method = RequestMethod.POST)
	public ModelAndView sendMail(HttpServletRequest req){
		ModelAndView model = new ModelAndView();
		String email = req.getParameter("email").trim();
		String name = req.getParameter("name").trim();
		String subject = req.getParameter("subject").trim();
		String describe = req.getParameter("descr").trim();
		
		if(email == null || email.isEmpty() || !UserDao.isValidEmailAddress(email)){
			model.setViewName("contactForm");
			model.addObject("email", "Невалиден имейл.");
			return model;
		}
		
		if(name == null || name.isEmpty() || !name.matches("[A-Za-z]{1,128}")){
			model.setViewName("contactForm");
			model.addObject("name", "Моля попълнете вашето име.");
			return model;
		}
		
		if(subject == null || subject.isEmpty()){
			model.setViewName("contactForm");
			model.addObject("subject", "Моля попълнете полето.");
			return model;
		}
		if(describe == null || describe.isEmpty()){
			model.setViewName("contactForm");
			model.addObject("describe", "Моля попълнете полето.");
			return model;
		}		
		
		User user = new User().setEmail(email).setFirstName(name);
		
		EmailSender.contactUs(user, subject, describe);
		return new ModelAndView("redirect:/index");
	}

	@RequestMapping(value = "/admin/removeProduct", method = RequestMethod.GET)
	public ModelAndView removeProduct(HttpSession sess) {

		if (sess.getAttribute("user") == null) {
			return new ModelAndView("redirect:/index");
		}

		User user = (User) sess.getAttribute("user");

		if (user == null || !user.isAdmin()) {
			return new ModelAndView("redirect:/index");
		}

		Product pro = (Product) sess.getAttribute("productCurrent");
		if (pro != null) {
			try {
				pd.removeProduct(pro);
			} catch (SQLException e) {
				return CartController.sqlError;
			}
			return new ModelAndView("redirect:/index");
		} else {
			return new ModelAndView("error", "error", "Проблем с конкретният продукт. Може би сесията е изтекла. Пробвайте отново.");
		}
	}

	@RequestMapping(value = "/admin/addProduct", method = RequestMethod.GET)
	public ModelAndView addProductForm(HttpSession sess) {
		User user = (User) sess.getAttribute("user");
		if (user == null || !user.isAdmin()) {
			return new ModelAndView("redirect:/user/login");
		}
		try {
			List<String> animals = pd.getAnimals();
			sess.setAttribute("animals", animals);
			List<String> subCategories = pd.getCategories();
			sess.setAttribute("subCategories", subCategories);
			List<String> brands = pd.getBrands();
			sess.setAttribute("brands", brands);
		} catch (SQLException e) {
			return CartController.sqlError;
		}
		return new ModelAndView("addproduct");
	}

	@RequestMapping(value = "/admin/addProduct", method = RequestMethod.POST)
	public ModelAndView addProduct(HttpServletResponse resp, HttpServletRequest req,
			@RequestParam("image") MultipartFile file) throws IllegalDiscountException {

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			return CartController.sqlError;
		}
		String name = req.getParameter("name");
		String animal = req.getParameter("animal");
		String category = req.getParameter("category");
		String description = req.getParameter("description");
		String brand = req.getParameter("brand");
		
		//validation
		if(!StringValidator.validate(name) 
				|| !StringValidator.validate(animal) 
				|| !StringValidator.validate(category) 
				|| !StringValidator.validate(description) 
				|| !StringValidator.validate(brand)){
			req.setAttribute("productError", "Моля, въведете валидни данни за продукта.");
			return new ModelAndView("addproduct");
		}
		name = name.trim();
		animal = animal.trim();
		category = category.trim();
		description = description.trim();
		brand = brand.trim();
		
		//validation
		if(req.getParameter("price") == null || req.getParameter("instock_count") == null || req.getParameter("discount") == null){
			return new ModelAndView("addproduct");
		}
		if(!req.getParameter("price").matches(ALL_DOUBLE_DIGIT_REGEX) || !req.getParameter("instock_count").matches(ALL_DIGITS_REGEX) || !req.getParameter("discount").matches(ALL_DIGITS_REGEX_DISCOUNT)){
			req.setAttribute("productError", "Моля, въведете валидни данни за продукта.");
			return new ModelAndView("addproduct");
		}
		req.setAttribute("productError", "Моля, въведете валидни данни за продукта.");
		
		double price = Double.parseDouble(req.getParameter("price"));
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
			return CartController.sqlError;
		} catch (IOException e1) {
			return CartController.sqlError;
		}

		Product p = new Product().setName(name).setAnimal(animal)
								.setCategory(category)
								.setPrice(price)
								.setDescription(description)
								.setBrand(brand)
								.setInStock(instock)
								.setDiscount(discount)
								.setImage(imageURL);
		
		try {
			pd.addProduct(p);
		} catch (SQLException e) {
			return CartController.sqlError;
		} catch (NoSuchProductException e) {
			return new ModelAndView("error", "error", e.getMessage());
		}
		return new ModelAndView("redirect:/user/admin/addProduct");
	}

	@RequestMapping(value = "/admin/addBrand", method = RequestMethod.POST)
	public ModelAndView addBrand(HttpServletResponse resp, HttpServletRequest req,
			@RequestParam("newBrandImage") MultipartFile brandFile) {

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			return CartController.sqlError;
		}

		String brandName = req.getParameter("newBrandname");
		
		if(!StringValidator.validate(brandName)){
			req.setAttribute("brandError", "Моля въведете валидно име за марка");
			return new ModelAndView("addproduct");
		}
		brandName = brandName.trim();
		String imageFileName = brandName.replaceAll(" ", "");
		String imageType = ".jpg";
		String imageURL = imageFileName.concat(imageType);

		File imageFile = new File(WebInitializer.BRAND_LOCATION + imageFileName + imageType);

		try {
			brandFile.transferTo(imageFile);
		} catch (IllegalStateException e1) {
			return CartController.sqlError;
		} catch (IOException e1) {
			return CartController.sqlError;
		}

		try {
			pd.insertBrand(brandName, imageURL);
		} catch (SQLException e) {
			return CartController.sqlError;
		}
		// return "redirect:/index";
		return new ModelAndView("redirect:/user/admin/addProduct");
	}

	@RequestMapping(value = "/admin/quantity", method = RequestMethod.POST)
	public ModelAndView addQuantity(HttpSession sess, HttpServletRequest req) {
		if(!req.getParameter("quantity").matches(ALL_DIGITS_REGEX)){
			return new ModelAndView("error", "error", "Моля въвеждайте коректни данни спрямо изискванията.");
		}
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		if (quantity < 1) {
			return new ModelAndView("error", "error", "Моля не въвеждайте отрицателни стойности в полетата.");
		}
		Product pro = (Product) sess.getAttribute("productCurrent");
		try {
			pd.addQuantity(pro.getId(), quantity);
		} catch (SQLException e) {
			return CartController.sqlError;
		}
		return new ModelAndView("redirect:/products/productdetail/productId/"+pro.getId() );
	}

	@RequestMapping(value = "/admin/discount", method = RequestMethod.POST)
	public ModelAndView addDiscount(HttpSession sess, HttpServletRequest req) {
		if(!req.getParameter("discount").matches(ALL_DIGITS_REGEX_DISCOUNT)){
			return CartController.discountError;
		}
		int discount = Integer.parseInt(req.getParameter("discount"));
		if (discount < 0 || discount > 99) {
			return CartController.discountError;
		}
		Product pro = (Product) sess.getAttribute("productCurrent");
		long id = pro.getId();
		try {
			pd.setInPromotion(id, discount);
			if(discount > 0){
				List<String> users = ud.emailsOfUsersLiked(id);
				for(String email : users){
					EmailSender.toPromotion(email, id);
				}
			}
		} catch (SQLException e) {
			return CartController.sqlError;
		}
		return new ModelAndView("products");
	}

	@RequestMapping(value = "/password", method = RequestMethod.GET)
	public ModelAndView forgottenPassword() {
		return new ModelAndView("password");
	}

	@RequestMapping(value = "/password", method = RequestMethod.POST)
	public ModelAndView sendPassword(HttpServletRequest req, HttpSession sess) {
		String email = req.getParameter("email").trim();
		String pass = PasswordGenerator.getRandomPass();

		if (email == null || !UserDao.isValidEmailAddress(email)) {
			return new ModelAndView("password", "pass", "Невалиден имейл.");
		}
		try {
			User user = ud.getUser(email);
			if (user.getEmail() == null || user.getEmail().isEmpty()) {	
				return new ModelAndView("password", "pass", "Несъществуващ имейл.");
			}
			sess.setAttribute("user", user);
			user.setPassword(pass);
			EmailSender.passwordTo(user);
			this.updateProfile(user, sess);
			sess.removeAttribute("user");
		} catch (SQLException e) {
			return CartController.sqlError;
		} catch (IllegalDiscountException e) {
			return CartController.discountError;
		}
		return new ModelAndView("redirect:/index");
	}
	
	@RequestMapping(value="/admin/changeStatus")
	public ModelAndView changeUserStatus(HttpServletRequest req){
		String email = req.getParameter("email");
		if(email == null || !UserDao.isValidEmailAddress(email.trim())){
			return new ModelAndView("error", "error", "Моля, въвеждайте валидни данни.");			
		}
		email = email.trim();
		try {
			ud.changeStatus(email);
		} catch (SQLException e) {
			return CartController.sqlError;
		} catch (NotSuchUserException e) {
			return new ModelAndView("profile", "error", "Несъществуващ имейл.");
		}
		req.setAttribute("success", "Успешно променихте статуса на потребител с имейл: " + email);
		return new ModelAndView("profile");
	}

	
	private ModelAndView updateProfile(User user, HttpSession sess){
		try {
			user.setId(((User) sess.getAttribute("user")).getId());
			String hashPassword = Hasher.securePassword(user.getPassword(), user.getEmail());
			user.setPassword(hashPassword);
			ud.updateUser(user);
			user = ud.getUser(user.getEmail());
			sess.setAttribute("user", user);
		} catch (SQLException e) {
			return CartController.sqlError;
		} catch (NoSuchAlgorithmException e) {
			return CartController.sqlError;
		} catch (UnsupportedEncodingException e) {
			return CartController.sqlError;
		} catch (IllegalDiscountException e) {
			return CartController.discountError;
		}
		return new ModelAndView("redirect:/user/profile");		
	}
	
}
