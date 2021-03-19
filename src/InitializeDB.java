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
	
	public InitializeDB() {}
	
	public void initialize() throws SQLException {	
		conn = DBConnector.createConnection();
		
		String[] sqlArr = {
				"DROP DATABASE IF EXISTS sunsetdb;",
				"CREATE DATABASE IF NOT EXISTS sunsetdb;"
				};
		
		for (String sql: sqlArr) {
			st = conn.createStatement();
			st.execute(sql);
		}

		conn = DBConnector.getConnection();
		
		createTables();
		populateTables();
		
		System.out.println("*****sunsetdb HAS BEEN SUCCESSFULLY INITIALIZED****");
		
		disconnect();
	}
	
	private void createTables() throws SQLException {
		conn = DBConnector.getConnection();
		
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
				+ "     url varchar(200),"
				+ "     description varchar(100),"
				+ "     posted_at timestamp,"
				+ "     post_user varchar(40) not null,"
				+ "     foreign key(post_user) references user(username),"
				+ "     primary key(image_id)"
				+ ");",
				"create table image_tag ("
				+ "		image_id int,"
				+ "    	tag varchar(20),"
				+ "   	primary key(image_id, tag),"
				+ "   	foreign key(image_id) references image(image_id) on delete cascade"
				+ ");",
				"create table likes("
				+ "		username varchar(40),"
				+ "   	image_id int,"
				+ " 	like_date date default (curdate()),"
				+ "     primary key(username, image_id),"
				+ "     foreign key(username) references user(username),"
				+ "     foreign key(image_id) references image(image_id) on delete cascade"
				+ ");",
				"create table comments("
				+ "		username varchar(40),"
				+ "   	image_id int,"
				+ " 	message varchar(140),"
				+ "     primary key(username, image_id),"
				+ "     foreign key(username) references user(username),"
				+ "     foreign key(image_id) references image(image_id) on delete cascade"
				+ ");",
				"create table follows ("
				+ "		following_username varchar(40),"
				+ "    follower_username varchar(40),"
				+ "    primary key(following_username, follower_username),"
				+ "    foreign key(following_username) references user(username),"
				+ "    foreign key(follower_username) references user(username)"
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
				
				new User("bruce@batman.com", "brucew", "Bruce", "Wayne", "Male", "2000-06-12"),
				new User("robin@batman.com", "robinh", "Robin", "Hood", "Male", "2000-01-01"),
				new User("joker@batman.com", "jokerc", "Joker", "Clown", "Other", "1998-09-09"),
			
				new User("familyguy@fox.com", "peterg", "Peter", "Griffin", "Male", "2002-03-15"),
				new User("brian@fox.com", "briang", "Brian", "Griffin", "Male", "2000-07-27"),
				new User("meg@fox.com", "megg", "Meg", "Griffin", "Female", "2004-05-30"),
				new User("taylor@fox.com", "taylors", "Taylor", "Swift", "Female", "2006-02-15"),
			};
		
		Image[] testImages = {
				new Image("https://d25tv1xepz39hi.cloudfront.net/2020-06-01/files/natural-light-sunset-beach_2044-01.jpg", "This is a cool image.", "2012-10-05 12:54:29", "arif123@hasan.com"),
				new Image("https://media.cntraveler.com/photos/545d0f4b0a0711b245b6d556/master/w_2048,h_1536,c_limit/new-york-city-sunsets-bushwick-inlet-park.jpg", "Picture of a sunny view.", "2016-12-18 09:14:38", "arif123@hasan.com"),
				new Image("https://earthsky.org/upl/2013/09/sunrise-red-sea-Graham-Telford-e1489764712368.jpg", "A view of the sun this evening.", "2010-01-02 16:45:09", "ahtesamul123@haque.com"),
				
				new Image("https://i.redd.it/m87ytedr2fzz.jpg", "Batman watching the sunset!", "2017-10-15 19:33:25", "bruce@batman.com"),
				new Image("https://img.freepik.com/free-photo/beautiful-colorful-sunset_1048-2416.jpg", "Admiring the sunny day.", "2011-08-12 21:58:08", "robin@batman.com"),
				new Image("https://wallup.net/wp-content/uploads/2018/10/07/219250-dog-sunset-748x468.jpg", "Brian walking in the park.", "2014-04-16 20:19:46", "brian@fox.com"),
				
				new Image("https://www.denverpost.com/wp-content/uploads/2020/08/TDP-L-SMOKY_-SUNSET_094.jpg", "Loving the sunset today!", "2016-06-20 17:13:58", "meg@fox.com"),
				new Image("https://free4kwallpapers.com/uploads/originals/2020/04/12/contrast-birds-at-sunset-wallpaper.jpg", "Watching the birds fly in the sunset.", "2019-05-04 21:22:48", "taylor@fox.com"),
				new Image("https://cdn.pixabay.com/photo/2016/09/07/11/37/tropical-1651426__340.jpg", "A view of the lake.", "2014-05-21 16:24:49", "psherman@gmail.com"),
				new Image("https://cdn.pixabay.com/photo/2019/03/29/18/31/sunset-4089845_960_720.jpg", "Hiking while the sun sets.", "2015-11-20 19:34:41", "familyguy@fox.com")		
			};
		
		String[][] testImageTags = {
				{"1", "cool"},
				{"1", "morning"},
				{"2", "sunny"},
				
				{"3", "evening"},
				{"3", "dawn"}, 
				{"3", "relaxing"},
				
				{"6", "dog"},
				{"7", "prom"}, 
				{"9", "lake"},
				{"10", "hiking"}
			};
		
		String[][] testLikes = {
				{"arif123@hasan.com", "1", "2015-08-22"},
				{"arif123@hasan.com", "3", "2018-04-17"},
				{"ahtesamul123@haque.com", "1", "2009-11-05"},
				
				{"bruce@batman.com", "5", "2012-05-03"},
				{"robin@batman.com", "1", "2019-03-15"},
				{"brian@fox.com", "2", "2003-10-10"},
				
				{"meg@fox.com", "6", "2017-06-29"},
				{"taylor@fox.com", "10", "2011-01-30"},
				{"psherman@gmail.com", "8", "2005-12-25"},
				{"familyguy@fox.com", "7", "2008-09-08"},
				
				//TODO: add more rows. second column must be between 1 and 10.
			};
		
		String[][] testComments = {
				{"ahtesamul123@haque.com", "1", "This image is very cool."},
				{"arif123@hasan.com", "2", "The sunset looks nice."},
				{"ahtesamul123@haque.com", "3", "Good picture. I like it very much."},
				
				{"meg@fox.com", "7", "I'll make this my wallpaper!"},
				{"bruce@batman.com", "3", "I wish I could be there."},
				{"psherman@gmail.com", "5", "Wow, looks like you're having fun!"},
				
				{"robin@batman.com", "2", "That camera takes amazing pictures"},
				{"taylor@fox.com", "6", "Wow, where'd you take this sunset picture?"},
				{"joker@batman.com", "4", "The sun looks beautiful."},
				{"familyguy@fox.com", "8", "I wish we could get more sunlight here in Alaska, LOL."},
				
				//TODO: add more rows. second column must be between 1 and 10.
			};
		
		String[][] testFollows = {
				{"arif123@hasan.com", "ahtesamul123@haque.com"},
				{"ahtesamul123@haque.com", "arif123@hasan.com"},
				{"taylor@fox.com", "arif123@hasan.com"},
				{"bruce@batman.com", "arif123@hasan.com"},
				{"robin@batman.com", "arif123@hasan.com"},
				{"ahtesamul123@haque.com", "bruce@batman.com"},
				
				{"robin@batman.com", "bruce@batman.com"},
				{"taylor@fox.com", "bruce@batman.com"},
				{"joker@batman.com", "robin@batman.com"},
				
				{"familyguy@fox.com", "ahtesamul123@haque.com"},
				{"ahtesamul123@haque.com", "psherman@gmail.com"},
				{"arif123@hasan.com", "robin@batman.com"},
				{"taylor@fox.com", "psherman@gmail.com"},
				
				//TODO add more rows. 
			};
		
		// Inserting 10 realistic tuples into user table
		UserDAO userDAO = new UserDAO();
		for (User user : testUsers) {
			userDAO.insert(user);
		}
		
		// Inserting 10 realistic tuples into image table
		ImageDAO imageDAO = new ImageDAO();
		for (Image image : testImages) {
			imageDAO.insert(image);
		}
		
		// Inserting 10 realistic tuples into image_tag table
		for (String[] imageTag : testImageTags) {
			insertImageTag(Integer.parseInt(imageTag[0]), imageTag[1]);
		}
		
		// Inserting 10 realistic tuples into likes table
		for (String[] like : testLikes) {
			insertLikes(like[0], Integer.parseInt(like[1]), like[2]);
		}
		
		// Inserting 10 realistic tuples into comments table
		for (String[] comment : testComments) {
			insertComments(comment[0], Integer.parseInt(comment[1]), comment[2]);
		}
		
		// Inserting 10 realistic tuples into comments table
		for (String[] follow : testFollows) {
			insertFollows(follow[0], follow[1]);
		}
		
		System.out.println("*****TUPLES HAVE BEEN INSERTED INTO sunsetdb****");
	}
	
	private void insertImageTag(int imageId, String tag) throws SQLException  {
		String sql = "INSERT INTO image_tag(image_id, tag) VALUES(?,?)";
		
		ps = conn.prepareStatement(sql);
		ps.setInt(1, imageId);
		ps.setString(2, tag);
		
		ps.executeUpdate();
	}
	
	private void insertLikes(String username, int imageId, String likeDate) throws SQLException {
		String sql = "INSERT INTO likes(username, image_id, like_date) VALUES(?,?,?)";
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, username);
		ps.setInt(2, imageId);
		ps.setString(3, likeDate);
		
		ps.executeUpdate();
	}
	
	private void insertComments(String username, int imageId, String message) throws SQLException {
		String sql = "INSERT INTO comments(username, image_id, message) VALUES(?,?,?)";
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, username);
		ps.setInt(2, imageId);
		ps.setString(3, message);
		
		ps.executeUpdate();
	}
	
	private void insertFollows(String following, String follower) throws SQLException {
		String sql = "INSERT INTO follows(following_username, follower_username) VALUES(?,?)";
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, following);
		ps.setString(2, follower);
		
		ps.executeUpdate();
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
