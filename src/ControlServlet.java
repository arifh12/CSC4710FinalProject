import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	private InitializeDB db;
	private final String USER_ROOT = "root";
	private final String PASS_ROOT = "pass1234";
	
	public void init() {
		userDAO = new UserDAO();
		db = new InitializeDB();
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
				response.sendRedirect("LoginForm.jsp");
				break;
			case "/register":
				processRegister(request, response);
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
		
		if(username.equalsIgnoreCase(USER_ROOT) && password.equalsIgnoreCase(PASS_ROOT)) {
			response.sendRedirect("Initialization.jsp");
		} else if (db.isInitialized()) {
			userDAO.login(username, password);
			response.sendRedirect("LoginForm.jsp");
		}
		else 
			System.out.println("*****DATABASE NOT INITIALIZED*****");
		
		System.out.println(username + " , " + password);
	}
	
	private void processRegister(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String firstName = request.getParameter("fname");
		String lastName = request.getParameter("lname");
		String birthday = request.getParameter("birthday");
		String gender = request.getParameter("gender");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User user = new User(username, password, firstName, lastName, gender, birthday);
		
		if(userDAO.insert(user)) {
			System.out.println("***USER INSERTED SUCCESSFULLY***");
			user.toString();
		} else {
			System.out.println("***USERNAME ALREADY EXISTS!***");
		}
		
		response.sendRedirect("LoginForm.jsp");
	}

}











