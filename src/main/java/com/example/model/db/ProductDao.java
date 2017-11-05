package com.example.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.pojo.Product;
import com.example.utils.exceptions.IllegalDiscountException;
import com.example.utils.exceptions.NoSuchProductException;
import com.example.utils.exceptions.NotEnoughQuantityException;

@Component
public class ProductDao {

	@Autowired
	DBManager db;
	@Autowired
	RatingDao rd;
	

	// returns list of products for specific animal category type
	public ArrayList<Product> getProductsByAnimal(int animalId) throws SQLException, IllegalDiscountException {
		ArrayList<Product> products = new ArrayList<>();

		Connection con = db.getConnection();
		String query = "SELECT p.product_id AS id, p.product_name AS name , c.category_name AS category , p.price AS price, "
						+ "p.description AS description, pc.category_name AS parent_category, p.image_url AS image, p.discount AS discount "
						+ "FROM pisi.products AS p " 
						+ "JOIN pisi.animals AS a ON (p.animal_id = a.animal_id) "
						+ "JOIN pisi.product_categories AS c ON(p.product_category_id = c.product_category_id) "
						+ "JOIN pisi.product_categories AS pc ON(c.parent_category_id = pc.product_category_id) "
						+ "JOIN pisi.brands AS b ON(p.brand_id = b.brand_id) " + "WHERE p.animal_id = ?;";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setInt(1, animalId);
			rs = stmt.executeQuery();
			String category = null;
			while (rs.next()) {
				double rating = rd.getProductRating(rs.getLong("id"));
				Product product = new Product().setId(rs.getLong("id"))
										.setName(rs.getString("name"))
										.setDescription(rs.getString("description"))
										.setPrice(rs.getDouble("price"))
										.setCategory(category)
										.setRating(rating)
										.setImage(rs.getString("image"))
										.setDiscount(rs.getInt("discount"));						
				products.add(product);
			}
			return products;
		}finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	// returns list of products for specific animal category type and given parent category ID
	public List<Product> getProductsByAnimalAndParentCategory(long animalId, long parentCategoryId)	throws SQLException, IllegalDiscountException {
		ArrayList<Product> products = new ArrayList<>();
		Connection con = db.getConnection();
		String query = "SELECT p.product_id as id, p.product_name as name , a.animal_name as animal, c.category_name as category , p.price,"
						+ " p.description, pc.category_name as parent_category, p.image_url as image, p.discount AS discount "
						+ "	FROM pisi.products as p" + " JOIN pisi.animals as a ON (p.animal_id = a.animal_id)"
						+ " JOIN pisi.product_categories as c ON(p.product_category_id = c.product_category_id)"
						+ " JOIN pisi.product_categories as pc ON(c.parent_category_id = pc.product_category_id)"
						+ " JOIN pisi.brands as b ON(p.brand_id = b.brand_id)"
						+ " WHERE c.parent_category_id = ? AND p.animal_id = ?;";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, parentCategoryId);
			stmt.setLong(2, animalId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				double rating = rd.getProductRating(rs.getLong("id"));
				
				Product product = new Product().setId(rs.getLong("id"))
											.setName(rs.getString("name"))
											.setDescription(rs.getString("description"))
											.setPrice(rs.getDouble("price"))
											.setCategory(rs.getString("category"))
											.setRating(rating)
											.setImage(rs.getString("image"))
											.setDiscount(rs.getInt("discount"));				
				products.add(product);
			}
			return products;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	// returns list of products for specific animal category type and sub-category id;
	public List<Product> getProductsByAnimalAndSubCategory(long animalId, long categoryId) throws SQLException, IllegalDiscountException {
		ArrayList<Product> products = new ArrayList<>();
		Connection con = db.getConnection();
		String query = "SELECT p.product_id as id, p.product_name as name , a.animal_name as animal, c.category_name as category , "
						+ "p.price, p.description, pc.category_name as parent_category, p.image_url as image, p.discount AS discount "
						+ "FROM pisi.products as p JOIN pisi.animals as a ON (p.animal_id = a.animal_id) "
						+ "JOIN pisi.product_categories as c ON(p.product_category_id = c.product_category_id) "
						+ "JOIN pisi.product_categories as pc ON(c.parent_category_id = pc.product_category_id) "
						+ "JOIN pisi.brands as b ON(p.brand_id = b.brand_id) "
						+ "WHERE p.product_category_id = ? AND p.animal_id = ?;";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, categoryId);
			stmt.setLong(2, animalId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				double rating = rd.getProductRating(rs.getLong("id"));
				Product product = new Product().setId(rs.getLong("id"))
												.setName(rs.getString("name"))
												.setDescription(rs.getString("description"))
												.setPrice(rs.getDouble("price"))
												.setCategory(rs.getString("category"))
												.setRating(rating)
												.setImage(rs.getString("image"))
												.setDiscount(rs.getInt("discount"));						
				products.add(product);
			}
			return products;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	// this method returns a product by its ID;
	public Product getProduct(long productId) throws SQLException, NoSuchProductException, IllegalDiscountException {
		Connection con = db.getConnection();
		String query = "SELECT p.product_id as id , p.product_name as name, p.description as description, p.instock_count as unit, "
						+ " c.category_name as category, a.animal_name as animal, p.price as price, p.discount as discount,"
						+ " b.brand_name as brand, b.logo_image as brandlogo, p.image_url as image , AVG(r.rating) as rating "
						+ "	FROM pisi.products as p"
						+ " JOIN pisi.product_categories AS c ON (p.product_category_id = c.product_category_id)"
						+ " JOIN pisi.brands AS b ON (p.brand_id = b.brand_id)"
						+ " LEFT JOIN pisi.ratings as r ON (p.product_id = r. product_id) "
						+ " JOIN pisi.animals as a ON(p.animal_id = a.animal_id) WHERE p.product_id = ?;";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, productId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				double rating = rs.getDouble("rating");

				Product product = new Product().setId(rs.getLong("id"))
												.setName(rs.getString("name"))
												.setDescription(rs.getString("description"))
												.setPrice(rs.getDouble("price"))
												.setAnimal(rs.getString("animal"))
												.setCategory(rs.getString("category"))
												.setBrand(rs.getString("brand"))
												.setRating(rating)
												.setInStock(rs.getInt("unit"))
												.setImage(rs.getString("image"))
												.setDiscount(rs.getInt("discount"));
				
				product.setCountRating(rd.getCountOfRatings(rs.getLong("id")));
				return product;
			} else {
				throw new NoSuchProductException("Няма продукт с подадените данни.");
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

	}

	public List<Product> getProductsByBrand(int brand_id) throws SQLException, IllegalDiscountException {
		ArrayList<Product> products = new ArrayList<>();
		Connection con = db.getConnection();
		String query = "SELECT p.product_id as id, p.product_name as name , a.animal_name as animal, c.category_name as category , "
						+ "p.price, p.description, pc.category_name as parent_category, p.image_url as image, p.discount AS discount "
						+ "FROM pisi.products as p JOIN pisi.animals as a ON (p.animal_id = a.animal_id) "
						+ "JOIN pisi.product_categories as c ON(p.product_category_id = c.product_category_id) "
						+ "JOIN pisi.product_categories as pc ON(c.parent_category_id = pc.product_category_id) "
						+ "JOIN pisi.brands as b ON(p.brand_id = b.brand_id) " + "WHERE p.brand_id =?;";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setInt(1, brand_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				double rating = rd.getProductRating(rs.getLong("id"));
				Product product = new Product().setId(rs.getLong("id"))
												.setName(rs.getString("name"))
												.setDescription(rs.getString("description"))
												.setPrice(rs.getDouble("price"))
												.setCategory(rs.getString("category"))
												.setRating(rating)
												.setImage(rs.getString("image"))
												.setDiscount(rs.getInt("discount"));												
				products.add(product);
			}
			return products;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	public List<Product> getProductsInPromotion() throws SQLException, IllegalDiscountException {
		ArrayList<Product> products = new ArrayList<>();
		Connection con = db.getConnection();
		String query = "SELECT p.product_id as id, p.product_name as name , a.animal_name as animal, c.category_name as category , "
						+ "p.price, p.description, pc.category_name as parent_category, p.image_url as image, p.discount AS discount  "
						+ "FROM pisi.products as p JOIN pisi.animals as a ON (p.animal_id = a.animal_id) "
						+ "JOIN pisi.product_categories as c ON(p.product_category_id = c.product_category_id) "
						+ "JOIN pisi.product_categories as pc ON(c.parent_category_id = pc.product_category_id) "
						+ "JOIN pisi.brands as b ON(p.brand_id = b.brand_id) " + "WHERE NOT (p.discount =0);";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			rs = stmt.executeQuery();
			while (rs.next()) {
				double rating = rd.getProductRating(rs.getLong("id"));
				Product product = new Product().setId(rs.getLong("id"))
												.setName(rs.getString("name"))
												.setDescription(rs.getString("description"))
												.setPrice(rs.getDouble("price"))
												.setCategory(rs.getString("category"))
												.setRating(rating)
												.setImage(rs.getString("image"))
												.setDiscount(rs.getInt("discount"));													
				products.add(product);
			}
			return products;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	public Set<Product> getFavorites(long userId) throws SQLException, IllegalDiscountException {
		Set<Product> favorites = new HashSet<Product>();
		Connection con = db.getConnection();
		String query = " SELECT p.product_id AS id , p.product_name AS name, p.description AS description,  "
						+ " c.category_name AS category, a.animal_name AS animal, p.instock_count AS inStock, p.price AS price, "
						+ " m.brand_name AS brand, p.image_url AS image, p.discount AS discount  "
						+ " FROM pisi.users_has_favorites AS cf " 
						+ " JOIN pisi.products AS p ON(cf.product_id = p.product_id )"
						+ " JOIN pisi.product_categories AS c ON (p.product_category_id = c.product_category_id) "
						+ "	JOIN pisi.brands AS m ON (p.brand_id = m.brand_id)"
						+ "	JOIN pisi.animals AS a ON(p.animal_id = a.animal_id)" 
						+ " WHERE cf.user_id = ? ;";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, userId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				double rating = rd.getProductRating(rs.getLong("id"));				
				Product product = new Product().setId(rs.getLong("id"))
												.setName(rs.getString("name"))
												.setDescription(rs.getString("description"))
												.setPrice(rs.getLong("price"))
												.setAnimal(rs.getString("animal"))
												.setCategory(rs.getString("category"))
												.setBrand(rs.getString("brand"))
												.setRating(rating)
												.setInStock(rs.getInt("inStock"))
												.setImage(rs.getString("image"))
												.setDiscount(rs.getInt("discount"));						
				favorites.add(product);
			}
			return favorites;
		} finally {
			if(rs!= null){
				rs.close();
			}
		}
	}

	public List<Product> searchProductByWord(String[] word) throws SQLException {
		ArrayList<Product> products = new ArrayList<>();
		Connection con = db.getConnection();
		String query = "SELECT p.product_id AS id, p.product_name AS name , a.animal_name AS animal, c.category_name AS category , "
						+ "p.price, p.description, pc.category_name AS parent_category, p.image_url AS image "
						+ "FROM pisi.products AS p JOIN pisi.animals AS a ON (p.animal_id = a.animal_id) "
						+ "JOIN pisi.product_categories AS c ON(p.product_category_id = c.product_category_id) "
						+ "JOIN pisi.product_categories AS pc ON(c.parent_category_id = pc.product_category_id) "
						+ "JOIN pisi.brands AS b ON(p.brand_id = b.brand_id)"
						+ "WHERE p.product_name LIKE ? OR p.description LIKE ? ";

		if (word.length != 1) {
			for (int i = 0; i < word.length - 1; i++) {
				query = query.concat(" or p.product_name LIKE ? OR p.description LIKE ?");
			}
		}
		query = query.concat(";");

		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			int control = 1;
			for (int i = 0; i < word.length; i++) {
				stmt.setString(control++, "%" + word[i] + "%");
				stmt.setString(control++, "%" + word[i] + "%");
			}
			rs = stmt.executeQuery();

			while (rs.next()) {

				double rating = rd.getProductRating(rs.getLong("id"));
				Product p = new Product().setId(rs.getLong("id"))
										.setName(rs.getString("name"))
										.setDescription(rs.getString("description"))
										.setPrice(rs.getDouble("price"))
										.setCategory(rs.getString("category"))
										.setRating(rating)
										.setImage(rs.getString("image"));
				products.add(p);
			}
			return products;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	public List<Product> getTopSoldProducts(int countLimit) throws SQLException, IllegalDiscountException {
		ArrayList<Product> products = new ArrayList<>();
		Connection con = db.getConnection();
		String query = "SELECT  op.product_id as id, p.product_name as name , a.animal_name as animal, "
						+ "c.category_name as category , p.price as price, p.description as description, p.discount AS discount, "
						+ "p.image_url as image, SUM(op.product_quantity) as countSold " 
						+ "FROM orders_has_products as op "
						+ "JOIN pisi.products as p ON(op.product_id = p.product_id) "
						+ "JOIN pisi.animals as a ON (p.animal_id = a.animal_id) "
						+ "JOIN pisi.product_categories as c ON(p.product_category_id = c.product_category_id) "
						+ "JOIN pisi.product_categories as pc ON(c.parent_category_id = pc.product_category_id) "
						+ "JOIN pisi.brands as b ON(p.brand_id = b.brand_id) "
						+ "GROUP by op.product_id ORDER BY SUM(op.product_quantity) desc LIMIT ?;";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setInt(1, countLimit);
			rs = stmt.executeQuery();
			while (rs.next()) {
				double rating = rd.getProductRating(rs.getLong("id"));
				
				Product product = new Product().setId(rs.getLong("id"))
												.setName(rs.getString("name"))
												.setDescription(rs.getString("description"))
												.setPrice(rs.getDouble("price"))
												.setCategory(rs.getString("category"))
												.setRating(rating)
												.setImage(rs.getString("image"))
												.setDiscount(rs.getInt("discount"));				
				products.add(product);
			}
			return products;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	public List<Product> getTopSoldProductsByAnimal(int countLimit, int animalId) throws SQLException, IllegalDiscountException {
		ArrayList<Product> products = new ArrayList<>();
		Connection con = db.getConnection();
		String query = "SELECT  op.product_id AS product_id, p.product_name AS name , a.animal_name AS animal,"
						+ "c.category_name AS category , p.price, p.description, pc.category_name AS parent_category,"
						+ "p.image_url AS image, a.animal_id AS animal, SUM(op.product_quantity) AS countSold, p.discount AS discount"
						+ "FROM orders_has_products AS op" 
						+ "JOIN pisi.products as p ON(op.product_id = p.product_id)"
						+ "JOIN pisi.animals as a ON (p.animal_id = a.animal_id) "
						+ "JOIN pisi.product_categories as c ON(p.product_category_id = c.product_category_id)"
						+ "JOIN pisi.product_categories as pc ON(c.parent_category_id = pc.product_category_id) "
						+ "JOIN pisi.brands as b ON(p.brand_id = b.brand_id)" 
						+ "GROUP by op.product_id HAVING a.animal_id=?"
						+ "ORDER BY SUM(op.product_quantity)  DESC  LIMIT ? ;";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setInt(1, animalId);
			stmt.setInt(1, countLimit);
			rs = stmt.executeQuery();
			while (rs.next()) {
				double rating = rd.getProductRating(rs.getLong("id"));				
				Product product = new Product().setId(rs.getLong("id"))
												.setName(rs.getString("name"))
												.setDescription(rs.getString("description"))
												.setPrice(rs.getDouble("price"))
												.setCategory(rs.getString("category"))
												.setRating(rating)
												.setImage(rs.getString("image"))
												.setDiscount(rs.getInt("discount"));				
				products.add(product);
			}
			return products;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	// updating in stock quantity for product
	public synchronized void removeQuantity(long productId, int quantityToRemove) throws SQLException, NotEnoughQuantityException {
		Connection con = db.getAdminCon();

		String updateQuery = "UPDATE pisi.products SET instock_count = instock_count - ? WHERE product_id = ? ";
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = con.prepareStatement(updateQuery);
			stmt.setLong(1, quantityToRemove);
			stmt.setLong(2, productId);
			stmt.executeUpdate();			
		} catch (SQLException e) {
			throw new NotEnoughQuantityException(NotEnoughQuantityException.NOT_ENOUGH_QUANTITY);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
	}

	// ***ADMIN operations***

	// add discount of product price
	public boolean setInPromotion(long productId, int percentDiscount) throws SQLException {
		Connection con = db.getAdminCon();
		String query = "UPDATE pisi.products SET discount = ? WHERE product_id = ? ";

		try (PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			stmt.setLong(1, percentDiscount);
			stmt.setLong(2, productId);
			return stmt.executeUpdate() == 1 ? true : false;
		} finally{}
	}

	// adding quantity from admin
	public boolean addQuantity(long product_id, int quantity) throws SQLException {
		Connection con = db.getAdminCon();
		String query = "UPDATE pisi.products SET instock_count= instock_count + ? WHERE product_id=?;";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setInt(1, quantity);
			stmt.setLong(2, product_id);
			return stmt.executeUpdate() == 1 ? true : false;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	// adding product from admin
	public synchronized void addProduct(Product p) throws SQLException, NoSuchProductException {
		Connection con = db.getAdminCon();
		String query = "INSERT INTO pisi.products (product_name, animal_id, product_category_id, price, description, brand_id, instock_count, discount, image_url) "
					+ "VALUES (?,?,?,?,?,?,?,?,?)";
		ResultSet rs = null;

		con.setAutoCommit(false);
		try (PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {

			int brandId = getBrandId(p.getBrand());
			int animalId = retrieveAnimalId(p.getAnimal());
			int categoryId = retrieveCategoryId(p.getCategory());

			if (categoryId == -1 || animalId == -1 || brandId <= 0) {
				throw new NoSuchProductException("Не валидно въведени данни за продъкт, моля поправете.");
			}

			ps.setString(1, p.getName());
			ps.setInt(2, animalId);
			ps.setInt(3, categoryId);
			ps.setDouble(4, p.getPrice());
			ps.setString(5, p.getDescription());
			ps.setInt(6, brandId);
			ps.setInt(7, p.getInStock());
			ps.setInt(8, p.getDiscount());
			ps.setString(9, p.getImage());
			ps.executeUpdate();
			con.commit();
			rs = ps.getGeneratedKeys();
			rs.next();
			p.setId(rs.getLong(1));
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				throw e1;
			}
			throw e;
		} catch (NoSuchProductException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				throw e1;
			}
			throw e;
		} finally {
			con.setAutoCommit(true);
			if (rs != null) {
				rs.close();
			}
		}
	}

	// remove product from admin
	public synchronized void removeProduct(Product p) throws SQLException {
		Connection con = db.getAdminCon();

		String queryTwo = "DELETE FROM pisi.ratings WHERE product_id=?;";
		String queryThree = "DELETE FROM pisi.users_has_favorites WHERE product_id=?;";
		String queryFour = "DELETE FROM pisi.orders_has_products WHERE product_id=?;";
		String queryOne = "DELETE FROM pisi.products WHERE product_id=?;";

		con.setAutoCommit(false);

		try (PreparedStatement psTwo = con.prepareStatement(queryTwo);
				PreparedStatement psThree = con.prepareStatement(queryThree);
				PreparedStatement psFour = con.prepareStatement(queryFour);
				PreparedStatement psOne = con.prepareStatement(queryOne);) {

			psTwo.setLong(1, p.getId());
			psTwo.executeUpdate();

			psThree.setLong(1, p.getId());
			psThree.executeUpdate();

			psFour.setLong(1, p.getId());
			psFour.executeUpdate();

			psOne.setLong(1, p.getId());
			psOne.executeUpdate();

			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} finally{}
			throw e;
		} finally {
			con.setAutoCommit(true);
		}
	}

	// this method returns the id of brand if it exists in the database
	public int getBrandId(String brandName) throws SQLException {
		Connection con = db.getConnection();
		String query = "SELECT brand_id AS id FROM pisi.brands WHERE brand_name = ? ";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setString(1, brandName);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("id");
			}
			return -1;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	// inserts new brand used when inserting new product if necessary

	public int insertBrand(String brandName, String brandImgUrl) throws SQLException {
		Connection con = db.getAdminCon();
		String query = "INSERT INTO pisi.brands (brand_name, logo_image) VALUES (?,?)";
		ResultSet rs = null;
		try {
			PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, brandName);
			ps.setString(2, brandImgUrl);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	// this method returns the animal id
	public int retrieveAnimalId(String animalName) throws SQLException {
		Connection con = db.getConnection();
		String query = "SELECT animal_id AS id FROM pisi.animals WHERE animal_name = ?";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setString(1, animalName.toUpperCase());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("id");
			}
			return -1;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	// this method returns the animal id
	public int retrieveCategoryId(String categoryName) throws SQLException {
		Connection con = db.getConnection();
		String query = "SELECT product_category_id as id FROM pisi.product_categories WHERE category_name = ?";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setString(1, categoryName.toUpperCase());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("id");
			}
			return -1;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

	}

	// this method returns all parent categories
	public List<String> getMainCategories() throws SQLException {
		Connection con = db.getConnection();
		List<String> categories = new ArrayList<>();
		String query = "SELECT category_name AS name FROM pisi.product_categories WHERE parent_category_id IS NULL";
		ResultSet rs = null;
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			rs = stmt.executeQuery();
			while (rs.next()) {
				categories.add(rs.getString("name"));
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return categories;
	}

	// this method returns all sub categories
	public List<String> getCategories() throws SQLException {
		Connection con = db.getConnection();
		List<String> categories = new ArrayList<>();
		String query = "SELECT category_name AS name FROM pisi.product_categories WHERE parent_category_id IS NOT NULL";
		ResultSet rs = null;
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			rs = stmt.executeQuery();
			while (rs.next()) {
				categories.add(rs.getString("name"));
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		Collections.sort(categories);
		return categories;
	}

	// this method returns all brands name
	public List<String> getBrands() throws SQLException {
		Connection con = db.getConnection();
		List<String> brands = new ArrayList<>();
		String query = "SELECT brand_name AS name FROM pisi.brands";
		ResultSet rs = null;
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			rs = stmt.executeQuery();
			while (rs.next()) {
				brands.add(rs.getString("name"));
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		Collections.sort(brands);
		return brands;
	}

	// this method returns all animals name
	public List<String> getAnimals() throws SQLException {
		Connection con = db.getConnection();
		List<String> animals = new ArrayList<>();
		String query = "SELECT animal_name AS name FROM pisi.animals";
		ResultSet rs = null;
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			rs = stmt.executeQuery();
			while (rs.next()) {
				animals.add(rs.getString("name"));
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		Collections.sort(animals);
		return animals;
	}
}
