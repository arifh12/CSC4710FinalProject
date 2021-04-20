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
	
	public List<Image> getCoolImages() throws SQLException {
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
	
	public List<Image> getNewImages() throws SQLException {
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
	
	public List<Image> getViralImages() throws SQLException {
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
	
	public Map<String, Integer> getTopTags() throws SQLException {
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
	
	public List<Image> getPoorImages() throws SQLException {
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

}
