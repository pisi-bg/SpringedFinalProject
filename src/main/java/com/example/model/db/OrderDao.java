package com.example.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.pojo.DeliveryInfo;
import com.example.model.pojo.Order;
import com.example.model.pojo.Product;
import com.example.model.pojo.User;
import com.example.utils.DateTimeConvertor;
import com.example.utils.exceptions.IllegalDiscountException;
import com.example.utils.exceptions.NotEnoughQuantityException;

@Component
public class OrderDao {

	@Autowired
	RatingDao rd;

	@Autowired
	ProductDao productDao;

	@Autowired
	DeliveryInfoDao deliveryInfoDao;

	@Autowired
	DBManager DBmanager;

	@Autowired
	UserDao userDao;

	/**
	 * Returns 10 <code>TreeSet<Order></code> sorted by date of creation;
	 * @param user_id ID number of the logged User;
	 * @return <code>TreeSet<Order></code> - may be empty;
	 * @throws SQLException
	 * @throws IllegalDiscountException 
	 */
	public TreeSet<Order> getOrdersForUser(long user_id) throws SQLException, IllegalDiscountException {
		Connection con = DBmanager.getConnection();
		String query = "SELECT p.order_id AS id, p.dateTime_created AS date, p.final_price AS price, p.delivery_info_id AS delInfoId "
					+ "FROM pisi.orders as p WHERE user_id = ? ORDER BY p.dateTime_created DESC LIMIT 10";

		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, user_id);
			rs = stmt.executeQuery();
			TreeSet<Order> orders = new TreeSet<>(new Comparator<Order>() {
				@Override
				public int compare(Order o1, Order o2) {
					return o2.getDateTime().compareTo(o1.getDateTime());
				}
			});

			while (rs.next()) {
				HashMap<Product, Integer> products = this.getProductsForOrder(rs.getLong("id"));
				User user = userDao.getUserByID(user_id);
				LocalDateTime date = DateTimeConvertor.sqlToLocalDateTime(rs.getString("date"));
				DeliveryInfo delivery = deliveryInfoDao.getDeliveryInfo(rs.getInt("delInfoId"));
				Order order = new Order().setId(rs.getLong("id"))
										.setUser(user)
										.setDateTime(date)
										.setFinalPrice(rs.getBigDecimal("price").doubleValue())
										.setProducts(products)
										.setDeliveryInfo(delivery);	
				orders.add(order);
			}
			return orders;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

	}

	/**
	 * Returns all products and their quantities for a given order;
	 * @param orderId ID number of the requested <code>Order</code>
	 * @return <code>HashSet<Product,Integer></code>
	 * @throws SQLException
	 * @throws IllegalDiscountException 
	 */
	public HashMap<Product, Integer> getProductsForOrder(long orderId) throws SQLException, IllegalDiscountException {
		Connection con = DBmanager.getConnection();
		String query = "SELECT p.product_id AS product_id,  p.product_name AS name, p.price AS price,"
					+ "p.discount AS discount, p.description AS description, c.category_name AS category, "
					+ "an.animal_name AS animal, p.image_url AS image, b.brand_name AS brand, op.product_quantity as quantity "
					+ "FROM pisi.orders_has_products AS op " 
					+ "JOIN pisi.products AS p USING (product_id)"
					+ "JOIN pisi.animals AS an ON (p.animal_id = an.animal_id) "
					+ "JOIN pisi.product_categories AS c ON(p.product_category_id = c.product_category_id) "
					+ "JOIN pisi.product_categories AS pc ON(c.parent_category_id = pc.product_category_id) "
					+ "JOIN pisi.brands AS b USING (brand_id) "
					+ "WHERE op.order_id = ? ORDER BY op.product_quantity";
		ResultSet rs = null;
		
		try(PreparedStatement ps = con.prepareStatement(query);){
			ps.setLong(1, orderId);
			rs = ps.executeQuery();
			HashMap<Product, Integer> productsForOrder = new HashMap<Product, Integer>();
			while (rs.next()) {
				double rating = rd.getProductRating(rs.getLong("product_id"));
				Product product = new Product().setId(rs.getLong("product_id"))
												.setName(rs.getString("name"))
												.setPrice(rs.getDouble("price"))
												.setDescription(rs.getString("description"))
												.setDiscount(rs.getInt("discount"))
												.setAnimal(rs.getString("animal"))
												.setCategory(rs.getString("category"))
												.setImage(rs.getString("image")).setRating(rating)
												.setBrand(rs.getString("brand"));
				productsForOrder.put(product, rs.getInt("quantity"));
			}
			return productsForOrder;
		}finally {
			if(rs != null){
				rs.close();
			}
		}
	}

	/**
	 * Insert given <code>Order</code> info into DB;
	 * @param order POJO <code>Order</code> to be insert;
	 * @throws SQLException
	 */
	public void insertOrderForUser(Order order) throws SQLException {
		Connection con = DBmanager.getAdminCon();
		String query = "INSERT INTO pisi.orders ( user_id, dateTime_created , final_price, delivery_info_id) "
					+ " VALUES (?,?,?,?)";
		ResultSet rs = null;

		try (PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			ps.setLong(1, order.getUser().getId());
			ps.setString(2, DateTimeConvertor.localDateTimeToSql(order.getDateTime()));
			ps.setDouble(3, order.getFinalPrice());
			ps.setLong(4, order.getDeliveryInfo().getDeliveryInfoId());

			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			rs.next();
			order.setId(rs.getInt(1));

		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	/**
	 * Insert products from cart to order info in DB;
	 * @param orderId ID number of the <code>Order</code>
	 * @param cart - <code>HashMap<Product,Integer></code> representing shopping cart from session;
	 * @throws SQLException
	 * @throws NotEnoughQuantityException - when there isn't enough quantity of a <code>Product</code> from cart
	 */
	public void insertProductsFromOrder(long orderId, HashMap<Product, Integer> cart)
			throws SQLException, NotEnoughQuantityException {
		Connection con = DBmanager.getAdminCon();

		for (Entry<Product, Integer> entry : cart.entrySet()) {
			Product product = entry.getKey();
			int quantity = (int) entry.getValue();
			productDao.removeQuantity(product.getId(), quantity);
			
			String query = "INSERT INTO pisi.orders_has_products (product_id, order_id, product_quantity) "
							+ " VALUES (?,?,?)";

			try (PreparedStatement ps = con.prepareStatement(query);) {
				ps.setLong(1, product.getId());
				ps.setLong(2, orderId);
				ps.setInt(3, quantity);
				ps.executeUpdate();
			} finally {}
		}
	}

	/**
	 * Returns all city name from DB;
	 * @return <code>ArrayList<String></code>
	 * @throws SQLException
	 */
	public ArrayList<String> getCitiesNames() throws SQLException {
		ArrayList<String> cities = new ArrayList<>();
		Connection con = DBmanager.getConnection();
		String query = "SELECT city_name FROM pisi.cities;";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			rs = stmt.executeQuery();
			while (rs.next()) {
				cities.add(rs.getString("city_name"));
			}
			return cities;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

}
