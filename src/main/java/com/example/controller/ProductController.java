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
	public String productsGetAnimal(HttpServletRequest request, HttpSession s,
			@PathVariable("animalId") Integer animalId) {
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

	// /products/category/animal/${sessionScope.animal }/category/1
	@RequestMapping(value = "/animal/{animalId}/category/{catId}", method = RequestMethod.GET)
	public String productsGetSubCategory(HttpServletRequest request, HttpSession s,
			@PathVariable("animalId") Integer animalId, @PathVariable("catId") Integer categoryId) {
		// int category = Integer.parseInt(request.getParameter("id"));
		// int animal = Integer.parseInt(request.getParameter("animalId"));
		Map<String, List<Product>> products = new HashMap<>();

		try {
			products = pd.getProductsByAnimalAndParentCategory(animalId, categoryId);
			s.setAttribute("products", products);
			request.setAttribute("id", categoryId);
			// in request so every time you change animal category
			// sub-categories will be not remembered
		} catch (SQLException e) {
			// TODO re-direct to error page and re-throw e;
			e.printStackTrace();
		}
		return "parentCategory";

	}

	// http://localhost:8080/ProjectPisi/subcategory/animal/3/catId/1/subcatId/6
	@RequestMapping(value = "/subcategory/animal/{animalId}/catId/{catId}/subcatId/{subCatId}", method = RequestMethod.GET)
	public String productsGetCategory(HttpServletRequest request, HttpSession s,
			@PathVariable("animalId") Integer animalId, @PathVariable("catId") Integer categoryId,
			@PathVariable("subCatId") Integer subCategoryId) {

		// int animal = Integer.parseInt(request.getParameter("animal"));
		// int id = Integer.parseInt(request.getParameter("subId"));
		// Object temp = request.getAttribute("categoryId"); // idea of this is
		// to be set in
		// request so when subCategories.jsp check for
		// id to represent matching sub-categories, unfortunately it didn't work
		// .... CHECK IT LATER !!!

		try {
			List<Product> products = pd.getProductsByAnimalAndSubCategory(animalId, subCategoryId);
			request.getSession().setAttribute("productsS", products);
			request.setAttribute("id", categoryId);
			System.out.println("vlazoh v productsGetCategory");
		} catch (SQLException e) {
			// TODO redirect to error page and re-throw e;
			e.printStackTrace();
		}
		return "subCategory";

	}

	// productdetail/productId/${pro.id}

	// @RequestMapping(value = "productdetail/productId/{id}", method =
	// RequestMethod.GET)
	// public String productDetailGet(HttpServletRequest request, HttpSession s,
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