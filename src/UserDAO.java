import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

public class UserDAO {
	private Connection conn = null;
	private Statement st = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	public UserDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection)DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sunsetdb?"
					+ "useSSL=false&user=john&password=pass1234");
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println(conn);
		}
	}
	
	public boolean insert(User user) throws SQLException {
		String sql = "INSERT into user VALUES(?, ?, ?, ?, ?, ?)";
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, user.getUsername());
		ps.setString(2, user.getPassword());
		ps.setString(3, user.getFirstName());
		ps.setString(4, user.getLastName());
		ps.setString(5, user.getGender());
		ps.setString(6, user.getBirthday());
		
		boolean tupleInserted = ps.executeUpdate() > 0;
		ps.close();
		
		return tupleInserted;
	}
	
	//TODO: make this class return a User object!!!!!
	public boolean login(String username, String password) throws SQLException {
		String sql = "SELECT * FROM user WHERE username=? AND password=?";
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, username);
		ps.setString(2, password);
		
		boolean loginSuccess;
		
		rs = ps.executeQuery();
		
		//TODO: still need to do something with this part later in the project!!!
		if (rs.next()) {
			System.out.println("login successful from UserDAO");
			loginSuccess = true; 
		} else {
			System.out.println("NOT FOUND from UserDAO!!!!!!!");
			loginSuccess = false;
		}
		
		return loginSuccess;
		
	}
}
