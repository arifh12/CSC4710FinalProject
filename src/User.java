import java.util.ArrayList;

public class User {
	private String username, password, firstName, lastName, birthday, gender;
	private int followerCount, followingCount;
	private boolean followStatus;

	public User() {}

	public User(String username, String password, String firstName, String lastName, String gender, String birthday) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.gender = gender;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public int getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(int followerCount) {
		this.followerCount = followerCount;
	}

	public int getFollowingCount() {
		return followingCount;
	}

	public void setFollowingCount(int followingCount) {
		this.followingCount = followingCount;
	}
	
	public boolean getFollowStatus() {
		return followStatus;
	}

	public void setFollowStatus(boolean followStatus) {
		this.followStatus = followStatus;
	}
}
