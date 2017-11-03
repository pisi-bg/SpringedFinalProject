package com.example.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.pojo.Order;
import com.example.model.pojo.Product;
import com.example.utils.DateTimeConvertor;
import com.example.utils.NotEnoughQuantityException;

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

	public TreeSet<Order> getOrdersForUser(long user_id) throws SQLException {
		Connection con = DBmanager.getConnection();
		String query = "SELECT * FROM pisi.orders as p WHERE user_id = ? order by p.dateTime_created DESC LIMIT 10";

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
				HashMap<Product, Integer> products = this.getProductsForOrder(rs.getLong("order_id"));
				orders.add(new Order(rs.getLong("order_id"), userDao.getUserByID(user_id),
						DateTimeConvertor.sqlToLocalDateTime(rs.getString("dateTime_created")),
						rs.getBigDecimal("final_price").doubleValue(), products,
						deliveryInfoDao.getDeliveryInfo(rs.getInt("delivery_info_id"))));

			}
			return orders;
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

	}

	public HashMap<Product, Integer> getProductsForOrder(long orderId) throws SQLException {
		Connection con = DBmanager.getConnection();
		PreparedStatement ps = con
				.prepareStatement("SELECT p.product_id AS product_id,  p.product_name AS name, p.price AS price,"
						+ "p.discount AS discount, p.description AS description, c.category_name AS category, "
						+ "an.animal_name AS animal, p.image_url AS image, b.brand_name AS brand, op.product_quantity as quantity "
						+ "FROM pisi.orders_has_products AS op " + "JOIN pisi.products AS p USING (product_id)"
						+ "JOIN pisi.animals AS an ON (p.animal_id = an.animal_id) "
						+ "JOIN pisi.product_categories AS c ON(p.product_category_id = c.product_category_id) "
						+ "JOIN pisi.product_categories AS pc ON(c.parent_category_id = pc.product_category_id) "
						+ "JOIN pisi.brands AS b USING (brand_id) WHERE op.order_id = ? ORDER BY op.product_quantity");
		ps.setLong(1, orderId);
		ResultSet rs = ps.executeQuery();
		HashMap<Product, Integer> productsForOrder = new HashMap<Product, Integer>();
		while (rs.next()) {
			double rating = rd.getProductRating(rs.getLong("product_id"));
			productsForOrder.put(new Product().setId(rs.getLong("product_id")).setName(rs.getString("name"))
					.setPrice(rs.getDouble("price")).setDescription(rs.getString("description"))
					.setDiscount(rs.getInt("discount")).setAnimal(rs.getString("animal"))
					.setCategory(rs.getString("category")).setImage(rs.getString("image")).setRating(rating)
					.setBrand(rs.getString("brand")), rs.getInt("quantity"));
		}
		return productsForOrder;
	}

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

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	public void insertProductsFromOrder(long orderId, HashMap<Product, Integer> cart)
			throws SQLException, NotEnoughQuantityException {
		Connection con = DBmanager.getAdminCon();

		for (Entry<Product, Integer> entry : cart.entrySet()) {
			Product product = entry.getKey();
			// long productId = product.getId();
			int quantity = (int) entry.getValue();
			productDao.removeQuantity(product.getId(), quantity);
			// if (quantity > product.getInStock()) {
			// throw new
			// NotEnoughQuantityException(NotEnoughQuantityException.NOT_ENOUGH_QUANTITY);
			// }

			String query = "INSERT INTO pisi.orders_has_products (product_id, order_id, product_quantity) "
					+ " VALUES (?,?,?)";

			try (PreparedStatement ps = con.prepareStatement(query);) {

				ps.setLong(1, product.getId());
				ps.setLong(2, orderId);
				ps.setInt(3, quantity);
				ps.executeUpdate();
			} catch (SQLException e) {
				throw e;
			}
		}
	}

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
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

}
