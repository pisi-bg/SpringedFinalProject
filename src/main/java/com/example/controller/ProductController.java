package com.example.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.db.CategoryDao;
import com.example.model.db.ProductDao;
import com.example.model.db.RatingDao;
import com.example.model.db.UserDao;
import com.example.model.pojo.Product;
import com.example.model.pojo.Rating;
import com.example.model.pojo.User;
import com.example.utils.ImageProvider;

@Controller
@RequestMapping(value = "/products")
public class ProductController {

	public static final int ITEMS_PER_PAGE = 9;
	@Autowired
	ProductDao pd;
	@Autowired
	RatingDao rd;
	@Autowired
	UserDao ud;
	@Autowired
	CategoryDao ctd;

	@RequestMapping(value = "/animal/{animalId}/{page}", method = RequestMethod.GET)
	public ModelAndView productsGetAnimal(HttpServletRequest request, HttpSession sess,
			@PathVariable("animalId") Integer animalId, @PathVariable("page") Integer page) {

		sess.removeAttribute("favorite");
		sess.removeAttribute("subCategories");
		int paging = page == null ? 0 : page; // safety first

		PagedListHolder<Product> productList = (PagedListHolder<Product>) sess.getAttribute("productPage");
		String url = "products/animal/" + animalId;

		Map<String, Integer> categoriesD = null;

		try {
			categoriesD = ctd.getCategoriesForAnimal(animalId);

			List<Product> products = pd.getProductsByAnimal(animalId);

			if (productList != null) {
				if (!productList.getSource().equals(products)) {
					productList.setSource(products);
				}
			} else {
				productList = new PagedListHolder<>(products);
			}
			productList.resort();

			productList.setPageSize(ITEMS_PER_PAGE);
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
					return new ModelAndView("Error", "message", "Моля не пишете в адрес бара сами. Пробвайте отново.");
				}
			}
			sess.setAttribute("url", url);
			sess.setAttribute("productPage", productList);
			sess.setAttribute("products", products);
			sess.setAttribute("animalId", animalId);

			sess.setAttribute("categoriesD", categoriesD);
		} catch (SQLException e) {
			return new ModelAndView("error", "error", "Вътрешна грешка, моля да ни извините. Пробвайте отново.");
		}
		return new ModelAndView("products", "productPage", productList);

	}

	@RequestMapping(value = "/animal/{animalId}/category/{catId}/{page}", method = RequestMethod.GET)
	public ModelAndView productsGetSubCategory(HttpServletRequest request, HttpSession sess,
			@PathVariable("animalId") Integer animalId, @PathVariable("catId") Integer categoryId,
			@PathVariable("page") Integer page) {

		sess.removeAttribute("favorite");

		int paging = page == null ? 0 : page;

		PagedListHolder<Product> productList = (PagedListHolder<Product>) sess.getAttribute("productPage");

		String url = "products/animal/" + animalId + "/category/" + categoryId;

		try {
			Map<String, Integer> subCategories = ctd.getSubCategoriesForCategory(animalId, categoryId);
			List<Product> products = pd.getProductsByAnimalAndParentCategory(animalId, categoryId);
			if (productList != null) {
				if (!productList.getSource().equals(products)) {
					productList.setSource(products);
				}
			} else {
				productList = new PagedListHolder<Product>(products);
			}
			productList.resort();
			productList.setPageSize(ITEMS_PER_PAGE);
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
					return new ModelAndView("Error", "message", "Моля не пишете в адрес бара сами. Пробвайте отново.");
				}
			}
			sess.setAttribute("url", url);
			sess.setAttribute("productPage", productList);
			sess.setAttribute("products", products);
			sess.setAttribute("catId", categoryId);
			sess.setAttribute("subCategories", subCategories);
			// in request so every time you change animal category
			// sub-categories will be not remembered
		} catch (SQLException e) {
			return new ModelAndView("error", "error", "Вътрешна грешка, моля да ни извините. Пробвайте отново.");
		}
		return new ModelAndView("products", "productPage", productList);

	}

	@RequestMapping(value = "/subcategory/animal/{animalId}/catId/{catId}/subcatId/{subCatId}/{page}", method = RequestMethod.GET)
	public ModelAndView productsGetCategory(HttpServletRequest request, HttpSession sess,
			@PathVariable("animalId") Integer animalId, @PathVariable("catId") Integer categoryId,
			@PathVariable("subCatId") Integer subCategoryId, @PathVariable("page") Integer page) {

		sess.removeAttribute("favorite");
		int paging = page == null ? 0 : page;

		PagedListHolder<Product> productList = (PagedListHolder<Product>) sess.getAttribute("productPage");
		String url = "products/animal/" + animalId + "/category/" + categoryId + "/subcatId/" + subCategoryId;

		try {
			List<Product> products = pd.getProductsByAnimalAndSubCategory(animalId, subCategoryId);

			if (productList != null) {
				if (!productList.getSource().equals(products)) {
					productList.setSource(products);
				}
			} else {
				productList = new PagedListHolder<Product>(products);
			}
			productList.resort();
			productList.setPageSize(ITEMS_PER_PAGE);
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
					return new ModelAndView("Error", "message", "Моля не пишете в адрес бара сами. Пробвайте отново.");
				}
			}

			sess.setAttribute("url", url);
			sess.setAttribute("productPage", productList);
			sess.setAttribute("products", products);
			request.setAttribute("subCatId", categoryId);
		} catch (SQLException e) {
			return new ModelAndView("error", "error", "Вътрешна грешка, моля да ни извините. Пробвайте отново.");
		}
		return new ModelAndView("products", "productPage", productList);

	}

	@RequestMapping(value = "/productdetail/productId/{id}", method = RequestMethod.GET)
	public ModelAndView productDetailGet(HttpServletRequest request, HttpSession s,
			@PathVariable("id") Integer productId, Model m) {
		Product productCurrent = null;
		ArrayList<Rating> comments = new ArrayList<>();

		try {
			productCurrent = pd.getProduct(productId);
			s.setAttribute("productCurrent", productCurrent);
			comments.addAll(rd.getProductRatingAndComment(productId));
			s.setAttribute("comments", comments);
		} catch (SQLException e) {
			return new ModelAndView("error", "error", "Вътрешна грешка, моля да ни извините. Пробвайте отново.");
		}

		// check if product is favorite and has Rating from user
		Object o = s.getAttribute("user");
		boolean isFavorite = false;
		if (o != null) {
			User u = (User) o;
			isFavorite = u.hasInFavorites(productCurrent);
			Double ratingFromUser = null;
			try {
				ratingFromUser = rd.productHasRatingFromUser(productId, u.getId());
			} catch (SQLException e) {
				return new ModelAndView("error", "error", "Вътрешна грешка, моля да ни извините. Пробвайте отново.");
			}
			s.setAttribute("ratingFromUser", ratingFromUser);
			s.setAttribute("isFavorite", new Boolean(isFavorite));
		}
		m.addAttribute("newrating", new Rating());
		return new ModelAndView("productdetail");

	}

	@RequestMapping(value = "/addInCart/{id}", method = RequestMethod.GET)
	public ModelAndView addInCart(HttpServletRequest request, HttpSession s, @PathVariable("id") Integer productId) {

		Product pro = (Product) s.getAttribute("productCurrent");
		if (pro == null || pro.getInStock() == 0) {
			// TODO pop up
			return new ModelAndView("redirect:/products");
		}
		HashMap<Product, Integer> cart = (HashMap<Product, Integer>) s.getAttribute("cart");
		if (cart == null) {
			cart = new HashMap<>();
		}
		if (cart.containsKey(pro)) {
			Integer quantity = cart.get(pro);
			quantity++;
			if (quantity > pro.getInStock()) {
				quantity = pro.getInStock();
			}
			cart.put(pro, quantity);
		} else {
			cart.put(pro, new Integer(1));
		}
		s.setAttribute("cart", cart);
		return new ModelAndView("redirect:/products/productdetail/productId/" + pro.getId());
	}

	@RequestMapping(value = "/addRating", method = RequestMethod.POST)
	public ModelAndView addRating(HttpServletRequest request, HttpSession s, @ModelAttribute Rating rating) {
		Product pro = (Product) s.getAttribute("productCurrent");
		if (pro == null) {
			return new ModelAndView("error", "error", "Вътрешна грешка, моля да ни извините. Пробвайте отначало.");
		}
		User u = (User) s.getAttribute("user");
		if (u == null) {
			return new ModelAndView("redirect:/user/login");
		}
		rating.setProductId(pro.getId());
		rating.setUserName(u.getFirstName());
		rating.setDateTime(LocalDateTime.now());
		try {
			rd.addProductRating(rating, u);
			pro.setCountRating(pro.getCountRating() + 1);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ModelAndView("error", "error", "Вътрешна грешка, моля да ни извините. Пробвайте отново.");

		}

		return new ModelAndView("redirect:/products/productdetail/productId/" + pro.getId());
	}

	@RequestMapping(value = "/addFavorite", method = RequestMethod.GET)
	public ModelAndView addFavorite(HttpServletRequest request, HttpSession session) {
		Product product = (Product) session.getAttribute("productCurrent");
		if (product == null) {
			return new ModelAndView("error", "error", "Вътрешна грешка, моля да ни извините. Пробвайте отначало.");
		}
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return new ModelAndView("redirect:/user/login");
		}
		try {
			ud.insertFavorite(user, product.getId());
			user.addToFavorites(product);
			session.setAttribute("isFavorite", true);
		} catch (SQLException e) {
			return new ModelAndView("error", "error", "Вътрешна грешка, моля да ни извините. Пробвайте отново.");
		}

		return new ModelAndView("redirect:/products/productdetail/productId/" + product.getId());
	}

	@RequestMapping(value = "/removeFavorite", method = RequestMethod.GET)
	public ModelAndView removeFavorite(HttpServletRequest request, HttpSession session) {
		Product product = (Product) session.getAttribute("productCurrent");
		if (product == null) {
			return new ModelAndView("error", "error", "Вътрешна грешка, моля да ни извините. Пробвайте отначало.");
		}
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return new ModelAndView("redirect:/user/login");
		}

		try {
			ud.removeFavorite(user, product.getId());
			user.removeFromFavorites(product);
			session.setAttribute("isFavorite", false);
		} catch (SQLException e) {
			return new ModelAndView("error", "error", "Вътрешна грешка, моля да ни извините. Пробвайте отново.");
		}

		return new ModelAndView("redirect:/products/productdetail/productId/" + product.getId());
	}

	@RequestMapping(value = "/sort/name/{order}")
	public ModelAndView sortByName(@PathVariable String order, HttpSession sess) {
		if (!order.equals("asc") && !order.equals("desc")) {
			return new ModelAndView("Error", "message", "Please don't type in URL by yourself");
		}

		boolean sortOrder = order.equals("asc");

		PagedListHolder<Product> productList = (PagedListHolder<Product>) sess.getAttribute("productPage");

		if (productList == null) {
			return new ModelAndView("Error", "message", "Please don't type in URL by yourself");
		}

		MutableSortDefinition sort = new MutableSortDefinition("name", false, sortOrder);
		sort.setIgnoreCase(true);
		productList.setSort(sort);
		productList.resort();
		sess.setAttribute("productPage", productList);
		sess.setAttribute("products", productList.getSource());
		return new ModelAndView("products", "productPage", productList);
	}

	@RequestMapping(value = "/sort/price/{order}")
	public ModelAndView sortByPrice(HttpSession sess, @PathVariable String order) {
		if (!order.equals("asc") && !order.equals("desc")) {
			return new ModelAndView("Error", "message", "Моля не пишете в адрес бара сами. Пробвайте отново.");
		}

		boolean sortOrder = order.equals("asc");

		PagedListHolder<Product> productList = (PagedListHolder<Product>) sess.getAttribute("productPage");

		if (productList == null) {
			return new ModelAndView("error", "error", "Вътрешна грешка, моля да ни извините. Пробвайте отначало.");
		}

		MutableSortDefinition sort = new MutableSortDefinition("price", false, sortOrder);
		productList.setSort(sort);
		productList.resort();
		sess.setAttribute("productPage", productList);
		sess.setAttribute("products", productList.getSource());
		return new ModelAndView("products", "productPage", productList);
	}

	@RequestMapping(value = "/search/{page}", method = RequestMethod.POST)
	public ModelAndView searchProduct(HttpServletRequest req, HttpSession sess, @PathVariable Integer page) {

		sess.removeAttribute("categoriesD");
		sess.removeAttribute("subCategories");
		sess.removeAttribute("favorite");
		int paging = page == null ? 0 : page; // safety first

		PagedListHolder<Product> productList = (PagedListHolder<Product>) sess.getAttribute("productPage");
		String url = "products/search";

		String word = (String) sess.getAttribute("word"); // saved word from
															// last search
		String keyword = req.getParameter("keyword"); // word that is searched
		keyword = keyword.trim();
		if (keyword == null) {
			keyword = word;
		}

		String[] words = keyword.split(" ");

		if (words[0].isEmpty()) {
			return new ModelAndView("index");
		}

		try {
			List<Product> products = pd.searchProductByWord(words);
			if (productList != null) {
				if (!productList.getSource().equals(products)) {
					productList.setSource(products);
				}
			} else {
				productList = new PagedListHolder<>(products);
			}
			productList.resort();
			productList.setPageSize(ITEMS_PER_PAGE);
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
					return new ModelAndView("Error", "message", "Моля не пишете в адрес бара сами. Пробвайте отново.");
				}
			}
			sess.setAttribute("url", url);
			sess.setAttribute("word", keyword);
			sess.setAttribute("productPage", productList);
			sess.setAttribute("products", products);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ModelAndView("error", "error", "Вътрешна грешка, моля да ни извините. Пробвайте отначало.");
		}

		return new ModelAndView("products", "productPage", productList);

	}

	@RequestMapping(value = "/image/{id}")
	public void showPicture(HttpServletResponse resp, @PathVariable Integer id) {

		try {
			Product p = pd.getProduct(id);
			ImageProvider.proceedProductPicture(p.getImage(), resp);
		} catch (IOException e) {
			// in jsp will be shown atl value
		} catch (SQLException e) {
			// in jsp will be shown atl value
		}
	}

}
