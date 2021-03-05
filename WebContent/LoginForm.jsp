<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sunset Images</title>
</head>
	<body>
    <h1>Login Form</h1>
        <form action="login" method="post">
            <table style="with: 100%">
                <tr>
                    <td>Username</td>
                    <td><input type="text" name="username" required placeholder="example@mail.com"/></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="password" required 
                    		placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;"/>
                    </td>
                </tr>

            </table>
            <br>
            <input type="submit" value="Login" /> <span style ="color:red">${errorLogin} </span>
            <p>New User? Register for an account. <a href="RegistrationForm.jsp">Register Here.</a>
        </form>
</body>
</html>
