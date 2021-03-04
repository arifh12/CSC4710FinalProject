import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

////testing
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
				new User("ahtesamul123", "haque456", "Ahtesamul", "Haque", "Male", "2000-01-01"),
				new User("psherman", "42wallabyway", "P.", "Sherman", "Male", "2003-05-30"),
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
