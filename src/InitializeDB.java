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
	private boolean initializeStatus = false;
	
	private final String URL = "jdbc:mysql://localhost/";
	private final String USER = "john";
	private final String PASS = "pass1234";
	
	public InitializeDB() {
		connect();
	}
	
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASS);
			
			String sql = "SHOW DATABASES LIKE 'sunsetdb'";
			st = conn.createStatement();
			initializeStatus = st.execute(sql);
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
				"CREATE DATABASE IF NOT EXISTS sunsetdb;",
				"USE sunsetdb;"
				};
		
		for (String sql: sqlArr) {
			st = conn.createStatement();
			st.execute(sql);
		}
		
		createTables();
		populateTables();
		
		initializeStatus = true;
		
		System.out.println("*****sunsetdb HAS BEEN SUCCESSFULLY INITIALIZED****");
	}
	
	public void createTables() throws SQLException {
		String[] sqlArr = {
				"create table user ("
				+ "	   username varchar(40),"
				+ "    password varchar(40),"
				+ "    first_name varchar(20),"
				+ "    last_name varchar(20),"
				+ "    gender char(1),"
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
	
	public void populateTables() throws SQLException {
		User[] testUsers = {
				new User("arif123", "hasan456", "Arif", "Hasan", 'M', "2000-06-12"),
				new User("ahtesamul123", "haque456", "Ahtesamul", "Haque", 'M', "2000-01-01"),
				new User("psherman", "42wallabyway", "P.", "Sherman", 'M', "2003-05-30"),
		};
		
		UserDAO userDAO = new UserDAO();
		for (User user : testUsers) {
			userDAO.insert(user);
		}
		
		System.out.println("*****"+ testUsers.length + " TUPLES HAVE BEEN INSERTED INTO sunsetdb****");
	} 

	public boolean isInitialized() {
		return initializeStatus;
	}
}
