import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	private InitializeDB db;
	private ImageDAO imageDAO;
	private Interaction interaction;
	private final String USER_ROOT = "root";
	private final String PASS_ROOT = "pass1234";
	
	public void init() {
		userDAO = new UserDAO();
		db = new InitializeDB();
		imageDAO = new ImageDAO();
		interaction = new Interaction();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(action);
		
		try {
			switch (action) {
			case "/login":
				processLogin(request, response);
				break;
			case "/initialize":
				db.initialize();
				RequestDispatcher rd = request.getRequestDispatcher("LoginForm.jsp");
				rd.forward(request, response);
				break;
			case "/register":
				processRegister(request, response);
				break;
			case "/feed":
				loadFeedPage(request, response);
				break;
			case "/insert-image":
				insertImage(request, response);
				break;
			case "/community":
				loadCommunityPage(request, response);
				break;
			case "/search":
				getSearchedUser(request, response);
				break;
			case "/delete":
				deleteImage(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update-image":
				updateImage(request, response);
				break;
			case "/like":
				likeImage(request, response);
				break;
			case "/unlike":
				unlikeImage(request, response);
				break;
			case "/profile":
				viewProfile(request, response);
				break;
			case "/follow":
				followUser(request, response);
				break;
			case "/unfollow":
				unfollowUser(request, response);
				break;
			default:
				System.out.println("error");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void processLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		RequestDispatcher rd;
		
		if(username.equalsIgnoreCase(USER_ROOT) && password.equals(PASS_ROOT)) {
			rd = request.getRequestDispatcher("Initialization.jsp");
		} 
		else if (userDAO.login(username, password)) {
			request.getSession().setAttribute("username", username);
			rd = request.getRequestDispatcher("/feed");
		} else {
			request.setAttribute("errorLogin", "Invalid user credentials!");
			rd = request.getRequestDispatcher("LoginForm.jsp");
		}
		System.out.println(username + ", " + password);
			
		rd.forward(request, response);
	}
	
	private void processRegister(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String firstName = request.getParameter("fname");
		String lastName = request.getParameter("lname");
		String birthday = request.getParameter("birthday");
		String gender = request.getParameter("gender");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirm_password");
		
		User user = new User(username, password, firstName, lastName, gender, birthday);
		RequestDispatcher rd;
		
		if (password.equals(confirmPassword)) {
			if(userDAO.insert(user)) {
				System.out.println("***USER INSERTED SUCCESSFULLY***");
				user.toString();
				rd = request.getRequestDispatcher("/login");
			} else {
				System.out.println("***DUPLICATE USERNAME***");
				request.setAttribute("user", user);
				request.setAttribute("errorRegistration", "Email already exists!");
				rd = request.getRequestDispatcher("RegistrationForm.jsp");
			}
		} else {
			request.setAttribute("user", user);
			System.out.println("***UNMATCHING PASSWORDS***");
			request.setAttribute("errorRegistration", "Passwords do not match!");
			rd = request.getRequestDispatcher("RegistrationForm.jsp");
		}
		
		rd.forward(request, response);
	}
	
	private void loadFeedPage(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String username = (String) request.getSession().getAttribute("username");
		ArrayList<Image> images = imageDAO.getImageList(username);
		ArrayList<Integer> likes = interaction.getUserLikesList(username);
		
		for (Image img : images) {
			if(likes.contains(img.getImageId()))
				img.setLikeStatus(true);
		}
		
		request.setAttribute("imageList", images);
		request.setAttribute("likes", likes);
		
		RequestDispatcher rd = request.getRequestDispatcher("FeedPage.jsp");
		rd.forward(request, response);		
	}

	private void insertImage(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String username = (String) request.getSession().getAttribute("username");
		String url = request.getParameter("url");
		String tags = request.getParameter("tags");
		String description = request.getParameter("description");
		String postedAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		RequestDispatcher rd;
		
		if (url.isBlank()) {
			request.setAttribute("errorNewPost", "Invalid URL");
			rd = request.getRequestDispatcher("NewPostForm.jsp");
		} else {
			List<String> tagList = Arrays.asList(tags.split(", "));
		
			Image img = new Image();
			img.setUrl(url);
			img.setDescription(description);
			img.setTags(tagList);
			img.setPostUser(username);
			img.setPostedAt(postedAt);
		
			imageDAO.insert(img);
			rd = request.getRequestDispatcher("/feed");
		}
		
		rd.forward(request, response);	
	}
	
	public void loadCommunityPage(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		List<User> userList = userDAO.getUsers("", (String) request.getSession().getAttribute("username"));
		
		request.setAttribute("userList", userList);
		RequestDispatcher rd = request.getRequestDispatcher("CommunityPage.jsp");
		
		rd.forward(request, response);
	}
	
	public void getSearchedUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String name = request.getParameter("search-field");
		List<User> userList = userDAO.getUsers(name, (String) request.getSession().getAttribute("username"));
		
		request.setAttribute("userList", userList);
		RequestDispatcher rd = request.getRequestDispatcher("CommunityPage.jsp");
		
		rd.forward(request, response);
	}
	
	public void deleteImage(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int imageId = Integer.parseInt(request.getParameter("image-id"));
		
		if (imageDAO.delete(imageId))
			System.out.println(imageId + " has been deleted successfully!");
		
		RequestDispatcher rd = request.getRequestDispatcher("feed");
		rd.forward(request, response);
	}
	
	public void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("image-id"));
		
		Image img = imageDAO.getImage(id);
		String tags = String.join(", ", img.getTags());
		
		request.setAttribute("tags", tags);
		request.setAttribute("image", img);
		RequestDispatcher rd = request.getRequestDispatcher("EditImageForm.jsp");
		rd.forward(request, response);
	}
	
	public void updateImage(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("image-id"));
		String url = request.getParameter("url");
		String description = request.getParameter("description");
		String tags = request.getParameter("tags");
		List<String> tagList = Arrays.asList(tags.split(", "));
		
		Image img = new Image();
		img.setImageId(id);
		img.setUrl(url);
		img.setDescription(description);
		img.setTags(tagList);
		
		imageDAO.update(img);
		
		response.sendRedirect("feed");
	}
	
	private void likeImage(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String username = (String) request.getSession().getAttribute("username");
		int id = Integer.parseInt(request.getParameter("image-id"));
		
		if (interaction.insertLike(username, id)) {
			response.sendRedirect("feed");
		} else {
			request.setAttribute("likeError", "Daily like limit reached!");
			RequestDispatcher rd = request.getRequestDispatcher("feed");
			rd.forward(request, response);
		}
	}
	
	private void unlikeImage(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String username = (String) request.getSession().getAttribute("username");
		int id = Integer.parseInt(request.getParameter("image-id"));
		
		if (interaction.deleteLike(username, id)) {
			response.sendRedirect("feed");
		}
	}
	
	private void viewProfile(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String username = (String) request.getSession().getAttribute("username");
		String targetUser = request.getParameter("target-user");
		User userProfile = userDAO.getUser(targetUser, username);
		
		boolean isFollowing = interaction.isFollowing(targetUser, username);
		userProfile.setFollowStatus(isFollowing);
		
		request.setAttribute("userProfile", userProfile);
		RequestDispatcher rd = request.getRequestDispatcher("ProfilePage.jsp");
		rd.forward(request, response);
	}
	
	private void followUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String username = (String) request.getSession().getAttribute("username");
		String targetUser = request.getParameter("target-user");
		
		interaction.insertFollow(targetUser, username);
		
		RequestDispatcher rd;
		String referer = request.getHeader("Referer");
		
		if (referer.contains("community"))
			response.sendRedirect("community");
		else {
			rd = request.getRequestDispatcher("profile?target-user=" + targetUser);
			rd.forward(request, response);
		}
	}
	
	private void unfollowUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String username = (String) request.getSession().getAttribute("username");
		String targetUser = request.getParameter("target-user");
		
		interaction.deleteFollow(targetUser, username);
		
		RequestDispatcher rd;
		String referer = request.getHeader("Referer");
		
		if (referer.contains("community"))
			response.sendRedirect("community");
		else {
			rd = request.getRequestDispatcher("profile?target-user=" + targetUser);
			rd.forward(request, response);
		}
	}
}









