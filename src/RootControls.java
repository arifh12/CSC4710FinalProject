import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RootControls {
	private Connection conn = null;
	private Statement st = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	private ImageDAO imageDAO;
	private UserDAO userDAO;
	
	public RootControls() {
		imageDAO = new ImageDAO();
		userDAO = new UserDAO();
	}
	
	public List<Image> getCoolImages() throws SQLException { //1
		conn = DBConnector.getConnection();
		List<Image> coolImages = new ArrayList<>();
		String sql = "select image_id, url, count(*) "
				+ "from image "
				+ "natural join likes "
				+ "group by image_id "
				+ "having count(*) >=5;";
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		
		while (rs.next()) {
			Image img;
			int imgId = rs.getInt("image_id");
			String url = rs.getString("url");
			int likes = rs.getInt("count(*)");
			
			img = new Image(imgId, likes, url);
			coolImages.add(img);
		}
		
		return coolImages;
	}
	
	public List<Image> getNewImages() throws SQLException { //2
		conn = DBConnector.getConnection();
		List<Image> newImages = new ArrayList<>();
		String sql = "SELECT image_id, url, posted_at FROM image WHERE DATE(posted_at) = curdate();";
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		
		while (rs.next()) {
			Image img;
			int imgId = rs.getInt("image_id");
			String url = rs.getString("url");
			String postedAt = rs.getString("posted_at");
			
			img = new Image(imgId, url);
			img.setPostedAt(postedAt);
			newImages.add(img);
		}
		
		return newImages;
	}
	
	public List<Image> getViralImages() throws SQLException { //3
		conn = DBConnector.getConnection();
		List<Image> viralImages = new ArrayList<>();
		String sql = "select image_id, url, count(*) as total "
				+ "from image "
				+ "natural join likes "
				+ "group by image_id "
				+ "order by  total desc "
				+ "limit 3;";
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		
		while (rs.next()) {
			Image img;
			int imgId = rs.getInt("image_id");
			String url = rs.getString("url");
			int likes = rs.getInt("total");
			
			img = new Image(imgId, likes, url);
			viralImages.add(img);
		}
		
		return viralImages;
	}
	
	public Map<String, Integer> getTopUsers() throws SQLException { //5
		conn = DBConnector.getConnection();
		Map<String, Integer> topUsers = new HashMap<>();
		String sql = "select post_user,  count(image_id) "
				+ "from image "
				+ "group by post_user "
				+ "having count(*) = ( "
				+ "	select count(image_id) "
				+ "	from image "
				+ "	group by post_user "
				+ "    order by count(image_id) desc "
				+ "    limit 1 "
				+ ");";
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		
		while (rs.next()) {
			String username = rs.getString("post_user");
			int posts  = rs.getInt("count(image_id)");
			
			topUsers.put(username, posts);
		}
		
		return topUsers; 
	}
	
	public List<User> getPopularUsers() throws SQLException { //5
		conn = DBConnector.getConnection();
		List<User> popularUsers = new ArrayList<>();
		String sql = "select following_username, count(*) "
				+ "from follows "
				+ "group by following_username "
				+ "having count(*) >= 5";
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		
		while (rs.next()) {
			User user;
			String username = rs.getString("following_username");
			int followers = rs.getInt("count(*)");
			
			user = new User(username);
			user.setFollowerCount(followers);
			popularUsers.add(user);
		}
		
		return popularUsers; 
	}
	
	public List<String> getCommonUsers(String userX, String userY) throws SQLException { //6
		conn = DBConnector.getConnection();
		List<String> commonUsers = new ArrayList<>();
		String sql = "select following_username "
				+ "from follows "
				+ "where follower_username = '" + userX + "' and following_username in ( "
				+ "	select following_username "
				+ "	from follows "
				+ "	where follower_username = '" + userY + "'"
				+ ");";
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		
		while (rs.next()) {
			String username = rs.getString("following_username");
			
			commonUsers.add(username);
		}
		
		return commonUsers;
	}
	
	public Map<String, Integer> getTopTags() throws SQLException { //7
		conn = DBConnector.getConnection();
		Map<String, Integer> topTags = new HashMap<>();
		String sql = "select tag, count(distinct(post_user)) as count "
				+ "from image i "
				+ "natural join image_tag t "
				+ "group by tag "
				+ "having count >= 3;";
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		
		while (rs.next()) {
			String tag = rs.getString("tag");
			int count = rs.getInt("count");
			
			topTags.put(tag, count);
		}
		
		return topTags;
	}
	
	public Map<String, Integer> getPositiveUsers() throws SQLException { //8
		conn = DBConnector.getConnection();
		Map<String, Integer> positiveUsers = new HashMap<>();
		String sql = "select username, count(*) "
				+ "from likes l\r\n"
				+ "left join image i on l.image_id = i.image_id "
				+ "where username <> post_user "
				+ "group by username "
				+ "having count(*) = ( "
				+ "	select count(*) "
				+ "    from follows f "
				+ "    join image i2 on f.following_username = i2.post_user "
				+ "    where f.follower_username = l.username "
				+ ");";
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		
		while (rs.next()) {
			String username = rs.getString("username");
			int likes  = rs.getInt("count(*)");
			
			positiveUsers.put(username, likes);
		}
		
		return positiveUsers; 
	}
	
	public List<Image> getPoorImages() throws SQLException { //9
		conn = DBConnector.getConnection();
		List<Image> poorImages = new ArrayList<>();
		String sql = "select image_id, url, post_user "
				+ "from image "
				+ "where image_id not in ( "
				+ "    select l.image_id "
				+ "    from likes l "
				+ "    join comments c "
				+ ");";
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		
		while (rs.next()) {
			Image img;
			int imgId = rs.getInt("image_id");
			String url = rs.getString("url");
			String postUser = rs.getString("post_user");
			
			img = new Image(imgId, url);
			img.setPostUser(postUser);
			poorImages.add(img);
		}
		
		return poorImages;
	}
	
	public List<User> getInactiveUsers() throws SQLException { //10
		conn = DBConnector.getConnection();
		List<User> inactiveUsers = new ArrayList<>();
		String sql = "select distinct username, first_name, last_name "
				+ "from user "
				+ "where username not in ( "
				+ "(select post_user "
				+ "    from image "
				+ ") union ( "
				+ "	select follower_username "
				+ "    from follows "
				+ ") union ( "
				+ "	select username "
				+ "    from likes "
				+ ") union ( "
				+ "	select username "
				+ "    from comments) "
				+ ");";
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		
		while (rs.next()) {
			User user;
			String username = rs.getString("username");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			
			user = new User(username, firstName, lastName);
			inactiveUsers.add(user);
		}
		
		return inactiveUsers;
	}

}
