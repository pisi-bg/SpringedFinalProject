package com.example.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.db.ProductDao;
import com.example.model.pojo.Product;


@Controller
@RequestMapping(value= "/page")
public class TestPaginationController {
	
	@Autowired
	ProductDao pd;
	
	@RequestMapping(value={"/{page}" , "/{text}",""}, method = RequestMethod.GET )
	public ModelAndView handleRequest(HttpSession sess, HttpServletRequest request, HttpServletResponse response, @PathVariable Integer page) throws Exception {
		PagedListHolder<Product> productList;
				
		if(sess.getAttribute("paging") == null){
			productList = new PagedListHolder(pd.getProductsByAnimal(3));
		}else {
			productList = (PagedListHolder<Product>) sess.getAttribute("paging");
		}

		
		 productList.setPageSize(5);
		
		 sess.setAttribute("paging", productList);
		 
//		if(text != null){
//			if(text.equals("next")){
//				productList.nextPage();
//			}else {
//				if(text.equals("prev")){
//					productList.previousPage();
//				}else {
//					return new ModelAndView("Error", "message", "Please don't type in URL by yourself");
//				}
//			}
//		}
		
		if(page != null){
			if(page >= 0){
				productList.setPage(page);
				System.out.println(productList.toString());
			}else {
				if(page == -1){
					productList.nextPage();
				}
				if(page == -2){
					productList.previousPage();
				}
				if(page < -2){
					return new ModelAndView("Error", "message", "Please don't type in URL by yourself");
				}
			}
		}
		
		return new ModelAndView("testing","productList",productList);
			
	}
	
}
