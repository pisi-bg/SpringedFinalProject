package com.example.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.db.ProductDao;
import com.example.model.db.RatingDao;
import com.example.model.db.UserDao;
import com.example.model.pojo.Product;
import com.example.model.pojo.Rating;
import com.example.model.pojo.User;
import com.example.utils.EmailSender;

@Controller
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	ProductDao pd;
	@Autowired
	RatingDao rd;
	@Autowired
	UserDao ud;

	@RequestMapping(value = "/animal/{animalId}", method = RequestMethod.GET)
	public String productsGetAnimal(HttpServletRequest request, HttpSession s,
			@PathVariable("animalId") Integer animalId) {
		try {
			List<Product> products = pd.getProductsByAnimal(animalId);
			s.setAttribute("products", products);
			s.setAttribute("animalId", animalId);
		} catch (SQLException e) {
			// TODO redirect to error page and re-throw e;
			e.printStackTrace();
		}
		return "products";
	}

	@RequestMapping(value = "/animal/{animalId}/category/{catId}", method = RequestMethod.GET)
	public String productsGetSubCategory(HttpServletRequest request, HttpSession s,
			@PathVariable("animalId") Integer animalId, @PathVariable("catId") Integer categoryId) {

		try {
			List<Product> products = pd.getProductsByAnimalAndParentCategory(animalId, categoryId);
			s.setAttribute("products", products);
			request.setAttribute("catId", categoryId);
			// in request so every time you change animal category
			// sub-categories will be not remembered
		} catch (SQLException e) {
			// TODO re-direct to error page and re-throw e;
			e.printStackTrace();
		}
		return "products";

	}

	@RequestMapping(value = "/subcategory/animal/{animalId}/catId/{catId}/subcatId/{subCatId}", method = RequestMethod.GET)
	public String productsGetCategory(HttpServletRequest request, HttpSession s,
			@PathVariable("animalId") Integer animalId, @PathVariable("catId") Integer categoryId,
			@PathVariable("subCatId") Integer subCategoryId) {
		try {
			List<Product> products = pd.getProductsByAnimalAndSubCategory(animalId, subCategoryId);
			request.getSession().setAttribute("products", products);
			request.setAttribute("subCatId", categoryId);
		} catch (SQLException e) {
			// TODO redirect to error page and re-throw e;
			e.printStackTrace();
		}
		return "products";

	}

	// /product/productdetail/productId/${pro.id}
	@RequestMapping(value = "/productdetail/productId/{id}", method = RequestMethod.GET)
	public String productDetailGet(HttpServletRequest request, HttpSession s, @PathVariable("id") Integer productId,
			Model m) {

		Product productCurrent = null;
		try {
			productCurrent = pd.getProduct(productId);
			s.setAttribute("productCurrent", productCurrent);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// check if product is favorite and has Rating from user
		Object o = s.getAttribute("user");
		boolean isFavorite = false;
		if (o != null) {
			User u = (User) o;
			isFavorite = u.hasInFavorites(productCurrent);
			Double ratingFromUser = null;
			try {
				ratingFromUser = rd.productHasRatingFromUser(productCurrent.getId(), u.getId());
			} catch (SQLException e) {
				return "error";
			}
			s.setAttribute("ratingFromUser", ratingFromUser);
			s.setAttribute("isFavorite", new Boolean(isFavorite));
		}
		m.addAttribute("newrating", new Rating());
		return "productdetail";

	}

	@RequestMapping(value = "/addInCart/{id}", method = RequestMethod.GET)
	public String addInCart(HttpServletRequest request, HttpSession s, @PathVariable("id") Integer productId) {

		Product pro = (Product) s.getAttribute("productCurrent");
		if (pro == null) {
			return "error";
		}
		HashMap<Product, Integer> cart = (HashMap<Product, Integer>) s.getAttribute("cart");
		if (cart == null) {
			cart = new HashMap<>();
		}
		if (cart.containsKey(pro)) {
			Integer quantity = cart.get(pro);
			quantity++;
			cart.put(pro, quantity);
		} else {
			cart.put(pro, new Integer(1));
		}
		s.setAttribute("cart", cart);
		return "redirect:/products/productdetail/productId/" + pro.getId();
	}

	@RequestMapping(value = "/addRating", method = RequestMethod.POST)
	public String addRating(HttpServletRequest request, HttpSession s, @ModelAttribute Rating rating) {
		Product pro = (Product) s.getAttribute("productCurrent");
		if (pro == null) {
			return "error";
		}
		User u = (User) s.getAttribute("user");
		if (u == null) {
			return "error";
		}
		rating.setProductId(pro.getId());
		rating.setUserId(u.getId());
		rating.setDateTime(LocalDateTime.now());
		try {
			rd.addProductRating(rating);
		} catch (SQLException e) {
			return "error";
		}

		return "redirect:/products/productdetail/productId/" + pro.getId();
	}

	@RequestMapping(value = "/addFavorite", method = RequestMethod.GET)
	public String addFavorit(HttpServletRequest request, HttpSession session) {
		Product product = (Product) session.getAttribute("productCurrent");
		if (product == null) {
			return "error";
		}
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}
		try {
			ud.insertFavorite(user, product.getId());
			user.addToFavorites(product);
			session.setAttribute("isFavorite", true);
		} catch (SQLException e) {
			return "error";
		}

		return "redirect:/products/productdetail/productId/" + product.getId();
	}

	@RequestMapping(value = "/removeFavorite", method = RequestMethod.GET)
	public String removeFavorit(HttpServletRequest request, HttpSession session) {
		Product product = (Product) session.getAttribute("productCurrent");
		if (product == null) {
			return "error";
		}
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/user/login";
		}

		try {
			ud.removeFavorite(user, product.getId());
			user.removeFromFavorites(product);
			session.setAttribute("isFavorite", false);
		} catch (SQLException e) {
			return "error";
		}

		return "redirect:/products/productdetail/productId/" + product.getId();
	}

	@RequestMapping(value = "/sort/name/{order}")
	public String sortByName(@PathVariable String order, HttpSession sess) {

		if (!order.equals("asc") && !order.equals("desc")) {
			return "error1";
		}

		Collection<Product> products = (Collection) sess.getAttribute("products");
		if (products == null) {
			return "error2";
		}
		TreeSet<Product> ordered = new TreeSet<>(new Comparator<Product>() {

			@Override
			public int compare(Product o1, Product o2) {
				if (order.equals("asc")) {
					if (o1.getName().equals(o2.getName())) {
						return -1;
					}
					return o1.getName().compareToIgnoreCase(o2.getName());
				} else {
					if (o1.getName().equals(o2.getName())) {
						return -1;
					}
					return o2.getName().compareToIgnoreCase(o1.getName());
				}

			}
		});

		ordered.addAll(products);

		sess.setAttribute("products", ordered);
		return "products";
	}

	@RequestMapping(value = "/sort/price/{order}")
	public String sortByPrice(HttpSession sess, @PathVariable String order) {

		if (!order.equals("asc") && !order.equals("desc")) {
			return "error1";
		}
		Collection products = (Collection) sess.getAttribute("products");
		if (products == null) {
			return "error2";
		}
		TreeSet<Product> ordered = new TreeSet<>(new Comparator<Product>() {

			@Override
			public int compare(Product o1, Product o2) {
				if (order.equals("asc")) {
					if (o1.getPrice() == o2.getPrice()) {
						return -1;
					}
					return Double.compare(o1.getPrice(), o2.getPrice());
				} else {
					if (o1.getPrice() == o2.getPrice()) {
						return -1;
					}
					return Double.compare(o2.getPrice(), o1.getPrice());
				}
			}
		});

		ordered.addAll(products);
		sess.setAttribute("products", ordered);
		return "products";
	}

	@RequestMapping(value = "/search")
	public String searchProduct(HttpServletRequest req, HttpSession sess) {
		if (req.getParameter("word") == null || sess.getAttribute("products") == null) {
			return "index";
		}
		String[] words = req.getParameter("word").split(" ");
		Collection<Product> products = null;

		try {
			products = pd.searchProductByWord(words);
			sess.setAttribute("products", products);
		} catch (SQLException e) {
			e.printStackTrace();
			return "error";
		}
		return "products";
	}

	@RequestMapping(value = "/test")
	public String testEmail() {
		EmailSender.toPromotion();
		return "index";
	}

}
