package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaDeConexao {
	private static final String DATABASE_URL = "jdbc:mysql://localhost/Agenda";
	private static final String DATABASE_USER = "agenda";
	private static final String DATABASE_PASSWORD = "root";
	
	public static Connection getConnection() throws SQLException {
		try {
			return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
			
		} catch(SQLException e) {
			throw e;
		}
	}
}
