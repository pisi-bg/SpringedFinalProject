package com.example.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.db.CategoryDao;
import com.example.model.db.ProductDao;
import com.example.model.pojo.Product;

@Controller
public class WelcomeController {

	private static final int LIMIT = 5;
	
	@Autowired
	CategoryDao ctd;
	@Autowired
	ProductDao pd;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String welcome(HttpSession ses) {
		ses.setMaxInactiveInterval(45 * 60); // 45 mins
		Map<String,Integer> aqua = null;	
		Map<String,Integer> cats = null;	
		Map<String,Integer> dogs = null;	
		Map<String,Integer> little = null;	
		Map<String,Integer> birds = null;	
		Map<String,Integer> lizzards = null;	
		try {
			//<brand name, brand logo>
			Map<String, String> brands = ctd.getTopBrands(LIMIT);
			List<Product> topProducts = pd.getTopSoldProducts(LIMIT);
			aqua = ctd.getCategoriesForAnimal(1);
			cats = ctd.getCategoriesForAnimal(2);
			dogs = ctd.getCategoriesForAnimal(3);
			little = ctd.getCategoriesForAnimal(4);
			birds = ctd.getCategoriesForAnimal(5);
			lizzards = ctd.getCategoriesForAnimal(6);
			
			ses.setAttribute("aqua", aqua);
			ses.setAttribute("cats", cats);
			ses.setAttribute("dogs", dogs);
			ses.setAttribute("little", little);
			ses.setAttribute("birds", birds);
			ses.setAttribute("lizzards", lizzards);
			ses.setAttribute("brands", brands);
			ses.setAttribute("topProducts", topProducts);
		} catch (SQLException e) {
			e.printStackTrace();
			return "error";
		}	
		
		return "index";
	}
	
	
}
