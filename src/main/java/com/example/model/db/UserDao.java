package com.example.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.pojo.User;
import com.example.utils.exceptions.NotSuchUserException;

@Component
public class UserDao {

	@Autowired
	DBManager db;

	@Autowired
	private ProductDao pd;

	/**
	 * Insert <code>Product</code> into <code>User</code> favorites 
	 * @param user POJO of logged <code>User</code>
	 * @param product_id id number of the <code>Product</code>
	 * @return <code>boolean</code>: true - if insert is complete, false - if there is no changes in the DB;
	 * @throws SQLException
	 */
	public boolean insertFavorite(User user, long product_id) throws SQLException {
		Connection con = db.getConnection();
		String query = "INSERT INTO pisi.users_has_favorites (user_id, product_id) VALUES (?,?)";
		int result = 0;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, user.getId());
			stmt.setLong(2, product_id);
			result = stmt.executeUpdate();
		} finally {}
		return result == 1 ? true : false;

	}

	/**
	 * Removes <code>Product</code> from <code>User</code> favorites products;
	 * @param user POJO of logged <code>User</code>;
	 * @param product_id id number of the <code>Product</code>
	 * @return <code>boolean</code>: true - if insert is complete, false - if there is no changes in the DB;
	 * @throws SQLException
	 */
	public boolean removeFavorite(User user, long product_id) throws SQLException {
		Connection con = db.getConnection();
		String query = "DELETE FROM pisi.users_has_favorites WHERE user_id = ? and product_id = ? ";
		int result = 0;

		try (PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setLong(1, user.getId());
			stmt.setLong(2, product_id);
			result = stmt.executeUpdate();
		} finally{}
		return result == 1 ? true : false;
	}

	/**
	 * Insert <code>User</code> into DB after registration;
	 * @param user POJO of logged <code>User</code> from session;
	 * @throws SQLException
	 */
	public void insertUser(User user) throws SQLException {
		Connection con = db.getConnection();
		String query = "INSERT INTO pisi.users (first_name,last_name,email,password,gender, isAdmin) VALUES (?,?,?,?,?,?)";
		ResultSet rs = null;
		try (PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			if (user.getIsMale()) {
				ps.setInt(5, 1);
			} else {
				ps.setInt(5, 0);
			}
			if (user.isAdmin()) {
				ps.setInt(6, 1);
			} else {
				ps.setInt(6, 0);
			}
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			rs.next();
			user.setId(rs.getLong(1));
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	/**
	 * Checks if the given email address is valid. Using <code>javax.mail</code>
	 * @param email address to be check;
	 * @return <code>boolean</code> true - if the email if valid;
	 * 								false - if the email is not valid;
	 */
	public static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	/**
	 * Check if <code>User</code> exists in the DB;
	 * @param user POJO of the <code>User</code> 
	 * @return <code>boolean</code> true - if <code>User</code> exists, false - if not;
	 * @throws SQLException
	 */
	public boolean userExist(User user) throws SQLException {
		Connection con = db.getConnection();
		String query = "SELECT first_name AS name FROM pisi.users WHERE email = ? AND password = ?";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setString(1, user.getEmail());
			stmt.setString(2, user.getPassword());
			rs = stmt.executeQuery();
			return rs.next();
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	/**
	 * Returns <code>User</code> if exists by given email;
	 * @param email <code>String</code> of the given email;
	 * @return <code>User</code>
	 * @throws SQLException
	 */
	public User getUser(String email) throws SQLException {
		Connection con = db.getConnection();
		String query = "SELECT user_id AS id, first_name , last_name, password, gender, isAdmin AS admin"
					+ " FROM pisi.users WHERE email = ?";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			if (!rs.next()) {
				return new User();
			}
			User u = new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), email,
					rs.getString("password"), rs.getBoolean("gender"), rs.getBoolean("admin"),
					pd.getFavorites(rs.getLong("id")));
			return u;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	// update user data, no need to return User because we will already have it
	// in the servlet
	/**
	 * Update <code>User</code> data in DB by given <code>User</code> ID;
	 * @param user POJO of logged <code>User</code>;
	 * @return <code>boolean</code>: true - if update is complete, false - if there is no changes in the DB;
	 * @throws SQLException
	 */
	public boolean updateUser(User user) throws SQLException {
		Connection con = db.getConnection();
		String query = "UPDATE pisi.users SET first_name = ?, last_name = ?, email = ?, password = ?, isAdmin = ?, gender= ? WHERE user_id= ?;";
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getPassword());
			stmt.setBoolean(5, user.isAdmin());
			stmt.setBoolean(6, user.getIsMale());
			stmt.setLong(7, user.getId());
			return stmt.executeUpdate() == 1 ? true : false;
		} finally{}
	}

	/**
	 * Returns <code>User</code> by given <code>User</code> ID;
	 * @param id ID number of the requested <code>User</code>
	 * @return <code>User</code>
	 * @throws SQLException
	 */
	public User getUserByID(long id) throws SQLException {
		Connection con = db.getConnection();
		String query = "SELECT email AS email, first_name , last_name, password, gender, isAdmin AS admin"
					+ " FROM pisi.users WHERE user_id = ?";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			rs.next();
			User user = new User(id, rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"),
					rs.getString("password"), rs.getBoolean("gender"), rs.getBoolean("admin"), pd.getFavorites(id));
			return user;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	/**
	 * Returns users email who liked product with the givven email.
	 * @param productId ID number of the <code>Product</code>
	 * @return <code>List</code> of users email who liked <code>Product</code> with the given ID;
	 * @throws SQLException
	 */
	public List<String> emailsOfUsersLiked(long productId) throws SQLException {
		String query = "SELECT u.email AS email FROM pisi.users AS u "
					+ "JOIN pisi.users_has_favorites AS f ON(u.user_id = f.user_id) "
					+ "JOIN pisi.products AS p ON(f.product_id = p.product_id) " + "WHERE f.product_id = ?";
		List<String> emails = new ArrayList<String>();
		Connection con = db.getAdminCon();
		ResultSet rs = null;
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setLong(1, productId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				emails.add(rs.getString("email"));
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return emails;
	}

	/**
	 * Change status of the <code>User</code> by given email. Can be invoked only by God Admins.
	 * @param email <code>String</code> email of the <code>User</code>;
	 * @throws SQLException
	 * @throws NotSuchUserException - if there isn't an user with given email address;
	 */
	public void changeStatus(String email) throws SQLException, NotSuchUserException {
		String query = "UPDATE pisi.users SET isAdmin = !isAdmin WHERE email = ?;";
		Connection con = db.getAdminCon();
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setString(1, email);
			int result = stmt.executeUpdate();
			if (result != 1) {
				throw new NotSuchUserException("User not found");
			}
		} finally{}
	}

}