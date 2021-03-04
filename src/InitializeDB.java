import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InitializeDB {
	private Connection conn = null;
	private Statement st = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	private String url = "jdbc:mysql://localhost/";
	private final String USER = "john";
	private final String PASS = "pass1234";
	
	public InitializeDB() {}
	
	private void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, USER, PASS);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		if (conn == null)
			System.out.println("*****CONNECTION FAILED****");
		else
			System.out.println("*****ESTABLISHED CONNECTION****");
	}
	
	public void initialize() throws SQLException {	
		connect();
		String[] sqlArr = {
				"DROP DATABASE IF EXISTS sunsetdb;",
				"CREATE DATABASE IF NOT EXISTS sunsetdb;"
				};
		
		for (String sql: sqlArr) {
			st = conn.createStatement();
			st.execute(sql);
		}
		
		url += "sunsetdb?useSSL=false";
		connect();
		
		createTables();
		populateTables();
		
		System.out.println("*****sunsetdb HAS BEEN SUCCESSFULLY INITIALIZED****");
		
		disconnect();
	}
	
	private void createTables() throws SQLException {
		String[] sqlArr = {
				"create table user ("
				+ "	   username varchar(40),"
				+ "    password varchar(40),"
				+ "    first_name varchar(20),"
				+ "    last_name varchar(20),"
				+ "    gender char(6),"
				+ "    birthday date,"
				+ "    primary key(username)"
				+ ");",
				"create table image ("
				+ "		image_id int auto_increment,"
				+ "     url varchar(100),"
				+ "     description varchar(100),"
				+ "     posted_at timestamp default current_timestamp,"
				+ "     post_user varchar(40) not null,"
				+ "     foreign key(post_user) references user(username),"
				+ "     primary key(image_id)"
				+ ");"
				};
		
		for (String sql: sqlArr) {
			st = conn.createStatement();
			st.execute(sql);
		}
		
		System.out.println("*****TABLES HAVE BEEN CREATED IN sunsetdb****");
	}
	
	private void populateTables() throws SQLException {
		User[] testUsers = {
				new User("arif123@hasan.com", "hasan456", "Arif", "Hasan", "Male", "2000-06-12"),
				new User("ahtesamul123@haque.com", "haque456", "Ahtesamul", "Haque", "Male", "2000-01-01"),
				new User("psherman@gmail.com", "42wallabyway", "P.", "Sherman", "Other", "2003-05-30"),
				
				new User("bruce@batman.com", "bruce", "Arif", "Hasan", "Male", "2000-06-12"),
				new User("robin@batman.com", "robin", "Ahtesamul", "Haque", "Male", "2000-01-01"),
				new User("joker@batman.com", "joker", "Joker", "Clown", "Other", "1998-09-09"),
				
				new User("familyguy@fox.com", "peterg", "Peter", "Griffin", "Male", "2002-03-15"),
				new User("brian@fox.com", "briang", "Brian", "Griffin", "Male", "2000-07-27"),
				new User("meg@fox.com", "megg", "Meg", "Griffin", "Female", "2004-05-30"),
				new User("taylor@fox.com", "taylor", "Taylor", "Swift", "Female", "2006-02-15"),
		};
		
		UserDAO userDAO = new UserDAO();
		for (User user : testUsers) {
			userDAO.insert(user);
		}
		
		System.out.println("*****"+ testUsers.length + " TUPLES HAVE BEEN INSERTED INTO sunsetdb****");
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
