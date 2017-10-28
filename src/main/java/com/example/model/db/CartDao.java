package com.example.model.db;

import java.util.HashMap;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.pojo.Product;

@Component
public class CartDao {

	@Autowired
	DBManager db;

	private static CartDao instance;

	// public static double calcProductPricePerQuantity(Product product, int
	// quantity) {
	// double productPrice = product.getPrice();
	// if (product.getDiscount() != 0) {
	// productPrice = product.calcDiscountedPrice();
	// }
	// return productPrice * quantity;
	// }

	public double calculatePriceForCart(HashMap<Product, Integer> products) {
		if (products != null) {
			double cartPrice = 0;
			for (Entry<Product, Integer> entry : products.entrySet()) {
				Product product = entry.getKey();
				int quantity = entry.getValue();
				double productPrice = product.getPrice();
				if (product.getDiscount() != 0) {
					productPrice = product.calcDiscountedPrice();
				}
				cartPrice += (productPrice * quantity);
			}
			return cartPrice;
		} else
			// throw exception
			return 0;

	}

	// for testing
	// public static void main(String[] args) throws SQLException {
	// HashMap<Product, Integer> prod = new HashMap<>();
	// for (int i = 0; i < 5; i++) {
	// Product p = ProductDao.getInstance().getProduct(i);
	// prod.put(p, 3 + i);
	// }
	// System.out.println(CartDao.getInstance().calculatePriceForCart(prod));
	// }

}
