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

	public UserDAO() {}

	public boolean insert(User user) throws SQLException {
		conn = DBConnector.getConnection();

		if (!isDuplicate(user.getUsername())) {
			String sql = "INSERT into user(username, password, first_name, last_name, gender, birthday) VALUES(?, ?, ?, ?, ?, ?);";

			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getLastName());
			ps.setString(5, user.getGender());
			ps.setString(6, user.getBirthday());

			return ps.executeUpdate() > 0;
		}

		return false;
	}

	// TODO: make this class return a User object!!!!!
	public boolean login(String username, String password) throws SQLException {
		conn = DBConnector.getConnection();

		String sql = "SELECT * FROM user WHERE username=? AND password=?;";

		ps = conn.prepareStatement(sql);
		ps.setString(1, username);
		ps.setString(2, password);

		boolean loginSuccess;

		rs = ps.executeQuery();

		// TODO: still need to do something with this part later in the project!!!
		if (rs.next()) {
			System.out.println("*****LOGIN SUCCESFUL FOR USER " + username + "*****");
			loginSuccess = true;
		} else {
			System.out.println("*****NOT FOUND USER " + username + "*****");
			loginSuccess = false;
		}

		disconnect();

		return loginSuccess;
	}

	public boolean isDuplicate(String username) throws SQLException {
		//TODO change this to statement
		String sql = "SELECT * FROM user WHERE username=?;";

		ps = conn.prepareStatement(sql);
		ps.setString(1, username);
		rs = ps.executeQuery();

		return rs.next();
	}
	
	public User getUser(String username) throws SQLException {
		conn = DBConnector.getConnection();
		
		String sql = "SELECT * FROM user WHERE username='" + username + "';";
		User user = null;
		
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		
		while (rs.next()) {
			String uname = rs.getString("username");
			String pass = rs.getString("password");
			String fname = rs.getString("first_name");
			String lname = rs.getString("last_name");
			String gender = rs.getString("gender");
			String bday = rs.getString("birthday");
			
			user = new User(uname, pass, fname, lname, gender, bday);
		}
		
		return user;		
	}

	private void disconnect() throws SQLException {
		if (st != null && !st.isClosed())
			st.close();
		if (ps != null && !ps.isClosed())
			ps.close();
		if (rs != null && !rs.isClosed())
			rs.close();
		if (conn != null && !conn.isClosed())
			conn.close();
	}
}
