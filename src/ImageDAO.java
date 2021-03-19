import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ImageDAO {
	private Connection conn = null;
	private Statement st = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	public boolean insert(Image image) throws SQLException {
		if (conn == null)
			conn = DBConnector.getConnection();
		
		String sql = "INSERT INTO image(url, description, posted_at, post_user) VALUES (?, ?, ?, ?);";
		String sql2 = "INSERT INTO image_tag(image_id, tag) VALUES (?, ?);";
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, image.getUrl());
		ps.setString(2, image.getDescription());
		ps.setString(3, image.getPostedAt());
		ps.setString(4, image.getPostUser());
		
		boolean insertedImage = ps.executeUpdate() > 0;
		
		if (insertedImage && image.getTags() != null) {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT @@IDENTITY;");
			
			if(rs.next()) {
				image.setImageId(rs.getInt(1));
				
				insertTags(image);
			}
		}
		
		return insertedImage;
	}
	
	public Image getImage(int id) throws SQLException {
		Image img = null;
		String sql = "select * from image where image_id = ?;";
		
		conn = DBConnector.getConnection();
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		rs = ps.executeQuery();
		
		while (rs.next()) {
			String url = rs.getString("url");
			String description = rs.getString("description");
			String postedAt = rs.getString("posted_at");
			String postUser = rs.getString("post_user");
			ArrayList<String> tags = getTags(id);		
			int likes = getLikeCount(id);
			
			img = new Image(id, url, description, postedAt, postUser);
			img.setTags(tags);
			img.setLikes(likes);
		}
		
		return img;
	}
	
	
	public ArrayList<Image> getImageList(String username) throws SQLException {
		ArrayList<Image> images = new ArrayList<Image>();
		String sql = "select * from image where post_user=? OR post_user in ("
					+ "		select following_username"
					+ "   	from follows"
					+ "   	where follower_username=?"
					+ ") order by posted_at desc;";
		
		conn = DBConnector.getConnection();
		ps = conn.prepareStatement(sql);
		ps.setString(1, username);
		ps.setString(2, username);
		rs = ps.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("image_id");
			String url = rs.getString("url");
			String description = rs.getString("description");
			String postedAt = rs.getString("posted_at");
			String postUser = rs.getString("post_user");
			ArrayList<String> tags = getTags(id);
			int likes = getLikeCount(id);
			
			Image img = new Image(id, url, description, postedAt, postUser);
			img.setTags(tags);
			img.setLikes(likes);
			images.add(img);
		}
		
		return images;
	}
	
	public ArrayList<String> getTags(int id) throws SQLException {
		ArrayList<String> tags = new ArrayList<>();
		String sql = "select * from image_tag where image_id=" + id + ";";
		
		st = conn.createStatement();
		ResultSet resultSet = st.executeQuery(sql);
		
		while(resultSet.next()) {
			tags.add(resultSet.getString("tag"));
		}
		
		return tags;
	}
	
	public boolean delete(int id) throws SQLException {
		conn = DBConnector.getConnection();
		String sql = "DELETE FROM image WHERE image_id = ?";
		
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		
		return ps.executeUpdate() > 0;
	}
	
	public boolean update(Image img) throws SQLException {
		conn = DBConnector.getConnection();
		String sql = "delete from image_tag where image_id=?";
		String sql2 = "update image set description=?, url=? where image_id=?;";
		
		ps = conn.prepareStatement(sql);
		ps.setInt(1, img.getImageId());
		ps.executeUpdate();
		
		ps = conn.prepareStatement(sql2);
		ps.setString(1, img.getDescription());
		ps.setString(2, img.getUrl());
		ps.setInt(3, img.getImageId());
		ps.executeUpdate();
		
		insertTags(img);
		
		return false;
	}
	
	public void insertTags(Image img) throws SQLException {
		String sql = "INSERT INTO image_tag(image_id, tag) VALUES (?, ?);";
		ps = conn.prepareStatement(sql);
	
		for(String tag : img.getTags()) {
			ps.setInt(1, img.getImageId());
			ps.setString(2, tag);
			ps.addBatch();
		}
	
		ps.executeBatch();
	}
	
	public int getLikeCount(int id) throws SQLException {
		String sql = "select count(*) from likes where image_id=?;";
		
		ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet resultSet = ps.executeQuery();
		
		if(resultSet.next())
			return resultSet.getInt(1);
		else
			return -1;
	}
}







