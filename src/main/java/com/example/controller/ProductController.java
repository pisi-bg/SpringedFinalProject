package com.example.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.db.ProductDao;
import com.example.model.pojo.Product;

@Controller
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	ProductDao pd;

	@RequestMapping(value = "/animal/{animalId}", method = RequestMethod.GET)
	public String productsGet(HttpServletRequest request, HttpSession s, @PathVariable("animalId") Integer animalId) {
		try {
			HashMap<String, ArrayList<Product>> products = pd.getProductsByAnimal(animalId);
			s.setAttribute("products", products);
			s.setAttribute("animal", animalId);
		} catch (SQLException e) {
			// TODO redirect to error page and re-throw e;
			e.printStackTrace();
		}

		return "products";
	}

	@RequestMapping(value = "/category/animal/{animalId}/{id}", method = RequestMethod.GET)
	public String productsGet(HttpServletRequest request, HttpSession s, @PathVariable("animalId") Integer animalId,
			@PathVariable("id") Integer categoryId) {
		// int category = Integer.parseInt(request.getParameter("id"));
		// int animal = Integer.parseInt(request.getParameter("animalId"));
		Map<String, List<Product>> products = new HashMap<>();

		try {
			products = pd.getProductsByAnimalAndParentCategory(animalId, categoryId);
			s.setAttribute("productsForCategory", products);
			request.setAttribute("id", categoryId);
			// in request so every time you change animal category
			// sub-categories will be not remembered
		} catch (SQLException e) {
			// TODO re-direct to error page and re-throw e;
			e.printStackTrace();
		}
		return "parentCategory";

	}

	// productdetail/productId/${pro.id}

	// @RequestMapping(value = "productdetail/productId/{id}", method =
	// RequestMethod.GET)
	// public String productsGet(HttpServletRequest request, HttpSession s,
	// @PathVariable("id") Integer productId) {
	//
	// // get product details
	// // long productId = Long.parseLong(request.getParameter("productId"));
	// Product productCurrent = null;
	// try {
	// productCurrent = pd.getProduct(productId);
	// request.getSession().setAttribute("productCurrent", productCurrent);
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// //response.getWriter().append("sql" + e.getMessage());
	// }
	//
	// // check if product is Favorite
	// Object o = s.getAttribute("user");
	// boolean isFavorite = false;
	// if (o != null) {
	// User u = (User) o;
	// isFavorite = u.hasInFavorites(productCurrent);
	// }
	// // request.setCharacterEncoding("UTF-8");
	// request.getSession().setAttribute("isFavorite", new Boolean(isFavorite));
	// request.getRequestDispatcher("productdetail.jsp").forward(request,
	// response);
	//
	// }

}