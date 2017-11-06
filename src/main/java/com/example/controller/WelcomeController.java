package com.example.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.db.CategoryDao;
import com.example.model.db.ProductDao;
import com.example.model.pojo.Product;
import com.example.utils.exceptions.IllegalDiscountException;

@Controller
public class WelcomeController {

	private static final int LIMIT = 6;

	@Autowired
	CategoryDao ctd;
	@Autowired
	ProductDao pd;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView welcome(HttpSession ses) {
		ses.setMaxInactiveInterval(45 * 60); // 45 mins
		Map<String, Integer> aqua = null;
		Map<String, Integer> cats = null;
		Map<String, Integer> dogs = null;
		Map<String, Integer> little = null;
		Map<String, Integer> birds = null;
		Map<String, Integer> lizzards = null;
		try {
			// <brand name, brand logo>
			Map<String, Integer> brands = ctd.getTopBrands(LIMIT);
			List<Product> topProducts = pd.getTopSoldProducts(LIMIT);
			aqua = ctd.getCategoriesForAnimal(1);
			cats = ctd.getCategoriesForAnimal(2);
			dogs = ctd.getCategoriesForAnimal(3);
			little = ctd.getCategoriesForAnimal(4);
			birds = ctd.getCategoriesForAnimal(5);
			lizzards = ctd.getCategoriesForAnimal(6);

			ses.setAttribute("1", aqua);
			ses.setAttribute("2", cats);
			ses.setAttribute("3", dogs);
			ses.setAttribute("4", little);
			ses.setAttribute("5", birds);
			ses.setAttribute("6", lizzards);
			ses.setAttribute("brands", brands);
			ses.setAttribute("topProducts", topProducts);
		} catch (SQLException e) {
			return CartController.sqlError;
		} catch (IllegalDiscountException e) {
			return CartController.discountError;
		}

		return new ModelAndView("index");
	}
	
	@RequestMapping(value="/error")
	public String error(HttpServletRequest req){
		req.setAttribute("error", "Greshka");
		return "error";
	}

}
