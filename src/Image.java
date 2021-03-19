import java.util.List;

public class Image {
	private int imageId, likes;
	private String url, description, postedAt, postUser;
	private List<String> tags = null;
	private boolean likeStatus = false;
	
	public boolean getLikeStatus() {
		return likeStatus;
	}

	public void setLikeStatus(boolean likeStatus) {
		this.likeStatus = likeStatus;
	}

	public Image() {}

	public Image(String url, String description, String postedAt, String postUser) {
		this.url = url;
		this.description = description;
		this.postedAt = postedAt;
		this.postUser = postUser;
	}
	
	public Image(int imageId, String url, String description, String postedAt, String postUser) {
		this.imageId = imageId;
		this.url = url;
		this.description = description;
		this.postedAt = postedAt;
		this.postUser = postUser;
	}

	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPostedAt() {
		return postedAt;
	}
	public void setPostedAt(String postedAt) {
		this.postedAt = postedAt;
	}
	public String getPostUser() {
		return postUser;
	}
	public void setPostUser(String postUser) {
		this.postUser = postUser;
	}
	
	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public int getLikes() {
		return likes;
	}

	public void setLikes(int likeCount) {
		this.likes = likeCount;
	}
}
