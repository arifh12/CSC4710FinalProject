import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
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
				RequestDispatcher rd = request.getRequestDispatcher("HomePage.jsp");
				rd.forward(request, response);
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
		RequestDispatcher rd;
		
		if(username.equalsIgnoreCase(USER_ROOT) && password.equals(PASS_ROOT)) {
			rd = request.getRequestDispatcher("Initialization.jsp");
		} 
		else if (userDAO.login(username, password)) {
			rd = request.getRequestDispatcher("HomePage.jsp");
		} else {
			request.setAttribute("errorLogin", "Invalid user credentials!");
			rd = request.getRequestDispatcher("LoginForm.jsp");
		}
			
		rd.forward(request, response);
		System.out.println(username + ", " + password);
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
				rd = request.getRequestDispatcher("HomePage.jsp");
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

}











