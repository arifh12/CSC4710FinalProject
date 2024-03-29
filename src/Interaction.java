import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Interaction {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	Statement st = null;
	
	public ArrayList<Integer> getUserLikesList(String username) throws SQLException {
		conn = DBConnector.getConnection();
		
		String sql = "select image_id from likes where username=?";
		ArrayList<Integer> likeIds = new ArrayList<>();
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, username);
		rs = ps.executeQuery();
		
		while(rs.next()) {
			likeIds.add(rs.getInt(1));
		}
		
		return likeIds;
	}

	public boolean insertLike(String username, int id) throws SQLException {
		conn = DBConnector.getConnection();
		boolean insertedLike;
		
		if (getDailyLikes(username) < 3) {
			String sql = "insert into likes(username, image_id) values (?,?);";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setInt(2, id);
			insertedLike = ps.executeUpdate() > 0;
		} else {
			insertedLike = false;
		}
		
		return insertedLike;
	}
	
	public boolean deleteLike(String username, int id) throws SQLException{
		conn = DBConnector.getConnection();
		String sql = "delete from likes where username=? and image_id=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, username);
		ps.setInt(2, id);
		
		return ps.executeUpdate() > 0;
	}
	
	public int getDailyLikes(String username) throws SQLException {
		conn = DBConnector.getConnection();
		String sql = "select count(*) from likes where username=? and like_date=curdate();";
		int dailyLikes = 0;
		
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, username);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if(resultSet.next())
			dailyLikes = resultSet.getInt(1);
		
		return dailyLikes;
	}
	
	public boolean isFollowing(String following, String follower) throws SQLException {
		conn = DBConnector.getConnection();
		String sql = "select * from follows where following_username=? and follower_username=?;";
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, following);
		ps.setString(2, follower);
		rs = ps.executeQuery();
		
		if (rs.next())
			return true;
				
		return false;
	}
	
	public boolean insertFollow(String following, String follower) throws SQLException {
		conn = DBConnector.getConnection();
		String sql = "insert into follows(following_username, follower_username) values (?,?);";
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, following);
		ps.setString(2, follower);
		
		return ps.executeUpdate() > 0;
	}
	
	public boolean deleteFollow(String following, String follower) throws SQLException {
		conn = DBConnector.getConnection();
		String sql = "delete from follows where following_username=? and follower_username=?;"; 
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, following);
		ps.setString(2, follower);
		
		return ps.executeUpdate() > 0;
	}
	
	public boolean deleteComment(String username, int imageId) throws SQLException {
		conn = DBConnector.getConnection();
		String sql = "delete from comments where username=? and image_id=?";
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, username);
		ps.setInt(2, imageId);
		
		return ps.executeUpdate() > 0;
	}
	
	public boolean insertComment(String username, int imageId, String message) throws SQLException {
		conn = DBConnector.getConnection();
		String sql = "insert into comments(username, image_id, message) values(?,?,?);";
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, username);
		ps.setInt(2, imageId);
		ps.setString(3, message);
		
		return ps.executeUpdate() > 0;
	}
	
	public String getComment(int imageId, String username) throws SQLException {
		conn = DBConnector.getConnection();
		String sql = "select message from comments where image_id=" + imageId + " and username='" + username + "';";
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		
		if (rs.next())
			return rs.getString("message");
		
		return null;
	}

	public void updateComment(int imageId, String username, String message) throws SQLException {
		conn = DBConnector.getConnection();
		String sql = "update comments "
				+ "set message='" + message + "' "
				+ "where username='" + username + "' and image_id=" + imageId + ";";
		st = conn.createStatement();
		st.executeUpdate(sql);
	} 
}












