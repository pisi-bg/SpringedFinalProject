package com.example.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryDao {

	@Autowired
	DBManager db;

	/**
	 * This method returns Map<name, logo_url> of the top brands by sells count.
	 * @param limit count of the returned brands
	 * @return <code>Map<String , String></code>
	 * @throws SQLException
	 */
	public Map<String, Integer> getTopBrands(int limit) throws SQLException {
		String query = "SELECT b.brand_name AS name, b.brand_id AS id, SUM(op.product_quantity) AS sum  FROM pisi.brands AS b "
					+ "JOIN pisi.products AS p ON (b.brand_id = p.brand_id) "
					+ "JOIN pisi.orders_has_products AS op ON(p.product_id = op.product_id) " 
					+ " GROUP BY b.brand_name "
					+ "ORDER BY sum DESC LIMIT ?";
		Map<String, Integer> brands = new LinkedHashMap();
		Connection con = db.getConnection();
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setInt(1, limit);
			rs = stmt.executeQuery();
			while (rs.next()) {
				brands.put(rs.getString("name"), rs.getInt("id"));
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return brands;
	}

	/**
	 * This method returns Map<name, id> of the categories by given animal ID;
	 * @param animalId ID number of the animal
	 * @return <code>Map<String , Integer></code>
	 * @throws SQLException
	 */
	public Map<String, Integer> getCategoriesForAnimal(int animalId) throws SQLException {
		String query = "SELECT DISTINCT(pc.category_name) AS name, pc.product_category_id AS id "
				+ "FROM pisi.products AS p "
				+ "JOIN pisi.product_categories AS c ON(p.product_category_id = c.product_category_id) "
				+ "JOIN pisi.product_categories AS pc ON(pc.product_category_id = c.parent_category_id) "
				+ "WHERE p.animal_id = ? AND pc.parent_category_id IS NULL " 
				+ "ORDER BY id ASC";
		Connection con = db.getConnection();
		ResultSet rs = null;
		Map<String, Integer> categories = new HashMap<>();

		try (PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setInt(1, animalId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				categories.put(rs.getString("name"), rs.getInt("id"));
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return categories;
	}

	/**
	 * This method returns Map<name , id> with sub-categories by given parent-category ID and animal ID;
	 * @param animalId ID of the animal;
	 * @param categoryId ID of the parent-category;
	 * @return <code>Map<String , Integer></code>
	 * @throws SQLException
	 */
	public Map<String, Integer> getSubCategoriesForCategory(int animalId, int categoryId) throws SQLException {
		String query = "SELECT DISTINCT(p.product_category_id) AS id, c.category_name AS name FROM pisi.products AS p "
					+ "JOIN pisi.product_categories AS c ON(p.product_category_id = c.product_category_id) "
					+ "WHERE animal_id = ? AND parent_category_id = ?";
		Connection con = db.getConnection();
		Map<String, Integer> subCategories = new HashMap<>();
		ResultSet rs = null;
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setInt(1, animalId);
			stmt.setInt(2, categoryId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				subCategories.put(rs.getString("name"), rs.getInt("id"));
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return subCategories;
	}

}
