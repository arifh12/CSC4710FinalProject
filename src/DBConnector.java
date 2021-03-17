import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	private static final String rootUrl = "jdbc:mysql://127.0.0.1:3306/";
	private static final String url = "jdbc:mysql://127.0.0.1:3306/sunsetdb?useSSL=false";
	private static final String USER = "john";
	private static final String PASS = "pass1234";
	
	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(url, USER, PASS);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println(conn);
		}
		
		return conn;
	}
	
	public static Connection createConnection() {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(rootUrl, USER, PASS);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		if (conn == null)
			System.out.println("*****CONNECTION FAILED****");
		else
			System.out.println("*****ESTABLISHED CONNECTION****");
		
		return conn;
	}
}
