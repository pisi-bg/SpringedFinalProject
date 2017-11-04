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

	public Collection<Rating> getProductRatingAndComment(long productId) throws SQLException {
		Collection<Rating> collection = new ArrayList<>();

		Connection con = db.getConnection();
		String query = "SELECT r.rating, r.comment AS comment, u.first_name as user, date_time AS time  "
				+ "FROM pisi.ratings AS r JOIN pisi.users AS u USING (user_id) WHERE r.product_id = ? ORDER BY date_time ";
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
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	public double getProductRating(long productId) throws SQLException {

		Connection con = db.getConnection();
		String query = "SELECT AVG(r.rating) AS rating FROM pisi.ratings AS r WHERE r.product_id = ?";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, productId);
			rs = stmt.executeQuery();
			rs.next();
			return rs.getDouble("rating");
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

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

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	public boolean addProductRating(Rating rating, User u) throws SQLException {

		Connection con = db.getConnection();
		String query = "INSERT INTO pisi.ratings (product_id, user_id, rating, comment, date_time ) VALUES (?,?,?,?,?)";
		ResultSet rs = null;

		try (PreparedStatement ps = con.prepareStatement(query);) {
			ps.setLong(1, rating.getProductId());
			ps.setLong(2, u.getId());
			ps.setDouble(3, rating.getRating());
			ps.setString(4, rating.getComment());
			ps.setString(5, DateTimeConvertor.localDateTimeToSql(rating.getDateTime()));
			return (ps.executeUpdate() == 1);

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	// public boolean addProductRating(long productId, long userId, double
	// rating) throws SQLException {
	//
	// Connection con = db.getConnection();
	// String query = "INSERT INTO pisi.ratings (product_id, user_id, rating)
	// VALUES (?,?,?)";
	// ResultSet rs = null;
	//
	// try (PreparedStatement ps = con.prepareStatement(query);) {
	// ps.setInt(1, (int) productId);
	// ps.setInt(2, (int) userId);
	// ps.setDouble(3, rating);
	// int result = ps.executeUpdate();
	// return result == 1 ? true : false;
	// } catch (SQLException e) {
	// throw e;
	// } finally {
	// if (rs != null) {
	// rs.close();
	// }
	// }
	// }

	// public ArrayList<Rating>

}
