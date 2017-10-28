package com.example.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.pojo.Rating;
import com.example.utils.DateTimeJavaSqlConvertor;

@Component
public class RatingDao {

	@Autowired
	private DBManager db;

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

	public boolean addProductRating(Rating rating) throws SQLException {

		Connection con = db.getConnection();
		String query = "INSERT INTO pisi.ratings (product_id, user_id, rating, comment, date_time ) VALUES (?,?,?,?,?)";
		ResultSet rs = null;

		try (PreparedStatement ps = con.prepareStatement(query);) {
			ps.setLong(1, rating.getProductId());
			ps.setLong(2, rating.getUserId());
			ps.setDouble(3, rating.getRating());
			ps.setString(4, rating.getComment());
			ps.setString(5, DateTimeJavaSqlConvertor.localDateTimeToSql(rating.getDateTime()));
			System.out.println(ps.toString());
			System.out.println("sddeasssssssssssssssssss");
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
