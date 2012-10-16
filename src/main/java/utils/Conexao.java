package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	private static Connection connection;
	
	public static Connection criarConexao() throws SQLException {
		connection = DriverManager.getConnection
			(
				//driver + database
				"jdbc:postgresql://127.0.0.1:5432/monitor_twitter_bkp",
				//username
				"aers",
				//password
				""
			);
		
		return connection;
	}
	
	public static void fecharConexao() throws SQLException {
		connection.close();
	}
	
}
