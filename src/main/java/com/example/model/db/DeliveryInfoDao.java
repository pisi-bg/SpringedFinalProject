package com.example.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.pojo.DeliveryInfo;
import com.example.utils.exceptions.NoSuchCityException;
import com.example.utils.exceptions.NotEnoughQuantityException;

@Component
public class DeliveryInfoDao {

	@Autowired
	DBManager DBmanager;

	/**
	 * This method insert Delivery Info info DB;
	 * @param delivInfo POJO of <code>DeliveryInfo</code> to be inserted;
	 * @throws SQLException
	 * @throws NoSuchCityException - when there is no such city in the DB;
	 */
	public void insertDelivInfoOrder(DeliveryInfo delivInfo) throws SQLException, NoSuchCityException {
		Connection con = DBmanager.getAdminCon();
		String query = "INSERT INTO pisi.deliveries ( address, zip_code, city_id, reciever_first_name, reciever_last_name, reciever_phone, notes) VALUES (?,?,?,?,?,?,?)";
		String cityName = delivInfo.getCity();
		int cityId = retrieveCityId(cityName);
		ResultSet rs = null;
		
		try (PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			ps.setString(1, delivInfo.getAddress());
			ps.setInt(2, delivInfo.getZipCode());
			ps.setInt(3, cityId);
			ps.setString(4, delivInfo.getRecieverFirstName());
			ps.setString(5, delivInfo.getRecieverLastName());
			ps.setLong(6, delivInfo.getRecieverPhone());
			ps.setString(7, delivInfo.getNotes());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			rs.next();
			delivInfo.setDeliveryInfoId(rs.getLong(1));
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	/**
	 * Returns <code>DeliveryInfo</code> by given ID;
	 * @param deliveryInfoId ID number of <code>DeliveryInfo</code> to be returned
	 * @return <code>DeliveryInfo</code>
	 * @throws SQLException
	 */
	public DeliveryInfo getDeliveryInfo(long deliveryInfoId) throws SQLException {
		Connection con = DBmanager.getConnection();
		String query = "SELECT c.city_id , d.delivery_info_id, d.address, d.zip_code, d.reciever_first_name, "
						+ "d.reciever_last_name, d.reciever_phone, d.notes, c.city_name  "
						+ "FROM pisi.deliveries AS d JOIN pisi.cities AS c USING (city_id) WHERE delivery_info_id=?;";
		ResultSet rs = null;

		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setLong(1, deliveryInfoId);
			rs = stmt.executeQuery();
			rs.next();
			DeliveryInfo delInfo = new DeliveryInfo(deliveryInfoId, rs.getString("address"), rs.getInt("zip_code"),
					rs.getString("city_name"), rs.getString("reciever_first_name"), rs.getString("reciever_last_name"),
					rs.getLong("reciever_phone"), rs.getString("notes"));
			return delInfo;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	/**
	 * Returns previous <code>DeliveryInfo</code> if there is any, by given <code>User</code> ID;
	 * @param userId ID number if the logged <code>User</code>
	 * @return <code>ArrayList<DeliveryInfo></code> - may be empty;
	 * @throws SQLException
	 */
	public ArrayList<DeliveryInfo> getDeliveriesInfosForUser(long userId) throws SQLException {
		Connection con = DBmanager.getConnection();
		ArrayList<DeliveryInfo> arr = new ArrayList<>();
		String temp = "SELECT c.city_id , d.delivery_info_id, d.address, d.zip_code, d.reciever_first_name, d.reciever_last_name, d.reciever_phone, d.notes, c.city_name "
						+ "FROM pisi.deliveries AS d " + "JOIN pisi.orders AS o ON(d.delivery_info_id = o.delivery_info_id) "
						+ "JOIN pisi.cities AS c USING (city_id) " + "WHERE o.user_id = ? GROUP BY d.address";
		ResultSet rs = null;
		try (PreparedStatement stmt = con.prepareStatement(temp);) {
			stmt.setLong(1, userId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				arr.add(new DeliveryInfo(rs.getLong("delivery_info_id"), rs.getString("address"), rs.getInt("zip_code"),
										rs.getString("city_name"), rs.getString("reciever_first_name"),
										rs.getString("reciever_last_name"), rs.getLong("reciever_phone"), rs.getString("notes")));
			}
			return arr;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	/**
	 * Returns city ID, if it exists in the DB, by given Name;
	 * @param cityName <code>String</code> name of the city;
	 * @return <code>Integer</code>
	 * @throws SQLException
	 * @throws NoSuchCityException - when there is no such city name in the DB;
	 */
	public int retrieveCityId(String cityName) throws SQLException, NoSuchCityException {
		Connection con = DBmanager.getConnection();
		String query = "SELECT city_id AS id FROM pisi.cities WHERE city_name = ?";
		ResultSet rs = null;
		try (PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setString(1, cityName);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("id");
			} else
				throw new NoSuchCityException("No such city in our data base, please don't modify our frontend.");
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}
}
