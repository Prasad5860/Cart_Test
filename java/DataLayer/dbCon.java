package DataLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbCon {

	public static Connection initialize() {

		String URL = "jdbc:postgresql://localhost:5432/postgres";
		String USER = "postgres";
		String PASSWORD = "Prasad@DBP47";
		String DRIVER = "org.postgresql.Driver";

		try {
			Class.forName(DRIVER);
			Connection conn = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("connection: "+conn);
			return conn;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}