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
				ps = conn.prepareStatement(sql2);
				
				for(String tag : image.getTags()) {
					ps.setInt(1, image.getImageId());
					ps.setString(2, tag);
					ps.addBatch();
				}
				
				ps.executeBatch();
			}
		}
		
		return insertedImage;
	}
	
	public ArrayList<Image> getImageList(String username) throws SQLException {
		ArrayList<Image> images = new ArrayList<Image>();
		String sql = "select * from image where post_user='" + username + "' OR post_user in ("
					+ "		select following_username"
					+ "   	from follows"
					+ "   	where follower_username = '" + username
					+ "') order by posted_at desc;";
		
		conn = DBConnector.getConnection();
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		
		while (rs.next()) {
			int id = rs.getInt("image_id");
			String url = rs.getString("url");
			String description = rs.getString("description");
			String postedAt = rs.getString("posted_at");
			String postUser = rs.getString("post_user");
			
			Image img = new Image(id, url, description, postedAt, postUser);
			images.add(img);
		}
		
		return images;
	}
}







