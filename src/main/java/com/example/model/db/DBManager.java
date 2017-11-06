package com.example.model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

@Component
public class DBManager {

	private static DBManager instance;

	private Connection con;
	private Connection adminCon;

	// constructor
	private DBManager() throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.jdbc.Driver");

		final String DB_IP = "localhost";
		final String DB_PORT = "3306";
		final String DB_DBNAME = "pisi";
		final String DB_USER = "root";
		final String DB_PASS = "root";

		con = DriverManager.getConnection("jdbc:mysql://" + DB_IP + ":" + DB_PORT + "/" + DB_DBNAME, DB_USER, DB_PASS);
		adminCon = DriverManager.getConnection("jdbc:mysql://" + DB_IP + ":" + DB_PORT + "/" + DB_DBNAME, DB_USER,
				DB_PASS);

	}

	// return connection to the database
	public Connection getConnection() {
		return con;
	}

	// return admin connection to the database
	public Connection getAdminCon() {
		return adminCon;
	}

	// close connection to the database when server is shutdown
	public void closeConnections() throws SQLException {
		if (con != null) {			
			con.close();			
		}
		if (adminCon != null) {			
			adminCon.close();
			
		}
	}

}
