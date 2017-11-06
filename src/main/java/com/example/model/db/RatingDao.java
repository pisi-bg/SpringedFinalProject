package com.example.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.pojo.Rating;
import com.example.model.pojo.User;
import com.example.utils.DateTimeConvertor;

@Component
public class RatingDao {

	@Autowired
	private DBManager db;
	@Autowired
	UserDao ud;

	/**
	 * Creates a <code>Collection</code> of <code>Rating</code> objects by given <code>Product</code> id;
	 * @param productId id number of the product
	 * @return <code>Collection</code>
	 * @throws SQLException
	 */
	public Collection<Rating> getProductRatingAndComment(long productId) throws SQLException {
		Collection<Rating> collection = new ArrayList<>();

		Connection con = db.getConnection();
		String query = "SELECT r.rating, r.comment AS comment, u.first_name AS user, date_time AS time  "
					+ "FROM pisi.ratings AS r JOIN pisi.users AS u USING (user_id) WHERE r.product_id = ? ORDER BY date_time DESC";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, productId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				LocalDateTime time = DateTimeConvertor.sqlToLocalDateTime(rs.getString("time"));
				collection.add(new Rating().setRating(rs.getDouble("rating")).setComment(rs.getString("comment"))
						.setUserName(rs.getString("user")).setDateTime(time));
			}
			return collection;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	/**
	 * Returns average rating for <code>Product</code>;
	 * @param productId id number of the <code>Product</code>
	 * @return <code>double</code>
	 * @throws SQLException
	 */
	public double getProductRating(long productId) throws SQLException {

		Connection con = db.getConnection();
		String query = "SELECT AVG(r.rating) AS rating FROM pisi.ratings AS r WHERE r.product_id = ?";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, productId);
			rs = stmt.executeQuery();
			rs.next();
			return rs.getDouble("rating");
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	/**
	 * Returns counts of ratings for <code>Product</code>;
	 * @param productId id number of the <code>Product</code>
	 * @return Integer 
	 * @throws SQLException
	 */
	public int getCountOfRatings(long productId) throws SQLException {

		Connection con = db.getConnection();
		String query = "SELECT COUNT(r.rating) AS rating FROM pisi.ratings AS r WHERE r.product_id = ?";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, productId);
			rs = stmt.executeQuery();
			rs.next();
			return rs.getInt("rating");
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	/**
	 * Returns specific rating value for <code>Product</code> given by <code>User</code>
	 * @param productId id number of <code>Product</code>
	 * @param userId id number of <code>User</code>
	 * @return <code>double</code>
	 * @throws SQLException
	 */
	public double productHasRatingFromUser(long productId, long userId) throws SQLException {

		Connection con = db.getConnection();
		String query = "SELECT rating FROM pisi.ratings WHERE product_id = ? AND user_id = ? ;";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, productId);
			stmt.setLong(2, userId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getDouble("rating");
			} else {
				return -1;
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	/**
	 * Add <code>Rating</code> to <code>Product</code> by <code>User</code>
	 * @param rating POJO with info for <code>Product</code>, <code>User</code>, rating and comment;
	 * @param user POJO of logged <code>User</code>;
	 * @return <code>boolean</code>: true - if insert is complete, false - if there is no changes in the DB;
	 * @throws SQLException
	 */
	public boolean addProductRating(Rating rating, User user) throws SQLException {

		Connection con = db.getConnection();
		String query = "INSERT INTO pisi.ratings (product_id, user_id, rating, comment, date_time ) VALUES (?,?,?,?,?)";
		ResultSet rs = null;

		try (PreparedStatement ps = con.prepareStatement(query);) {
			ps.setLong(1, rating.getProductId());
			ps.setLong(2, user.getId());
			ps.setDouble(3, rating.getRating());
			ps.setString(4, rating.getComment());
			ps.setString(5, DateTimeConvertor.localDateTimeToSql(rating.getDateTime()));
			return (ps.executeUpdate() == 1);
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}
}
