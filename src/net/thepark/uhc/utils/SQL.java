package net.thepark.uhc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL {


	public static Connection getConnection() throws SQLException {

		Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/minigames",
                "root", "");
		return connection;
	}
}
