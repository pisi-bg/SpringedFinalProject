package com.example.controller;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.db.ProductDao;
import com.example.model.pojo.Product;
import com.example.model.pojo.User;

@Controller
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	ProductDao pd;

	@RequestMapping(value = "/animal/{animalId}", method = RequestMethod.GET)
	public String productsGetAnimal(HttpServletRequest request, HttpSession s,
			@PathVariable("animalId") Integer animalId) {
		try {
			List<Product> products = pd.getProductsByAnimal(animalId);
			s.setAttribute("products", products);
			s.setAttribute("animal", animalId);
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
	public String productDetailGet(HttpServletRequest request, HttpSession s, @PathVariable("id") Integer productId) {
		Product productCurrent = null;
		try {
			productCurrent = pd.getProduct(productId);
			request.getSession().setAttribute("productCurrent", productCurrent);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// check if product is Favorite
		Object o = s.getAttribute("user");
		boolean isFavorite = false;
		if (o != null) {
			User u = (User) o;
			isFavorite = u.hasInFavorites(productCurrent);
		}
		s.setAttribute("isFavorite", new Boolean(isFavorite));
		return "productdetail";
	}

	@RequestMapping(value = "/addInCart/{id}", method = RequestMethod.GET)
	public String addInCart(HttpServletRequest request, HttpSession s, @PathVariable("id") Integer productId) {

		Object o = request.getSession().getAttribute("cart");
		HashSet<Product> cart;
		if (o == null) {
			cart = new HashSet<Product>();
			request.getSession().setAttribute("cart", cart);
		} else {
			cart = (HashSet<Product>) o;
		}

		return "productdetail";
	}

}