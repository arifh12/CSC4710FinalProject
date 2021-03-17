<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Sunset Images</title>
	<style><%@include file="MainStyle.css"%></style>
</head>
<body>
	<div class="loginDiv">
		<img class="logo" src="https://www.sunsetwatchfamilycampground.com/wp-content/uploads/2019/10/Sunset-Logo-2.png" width="200"/>
		<h1 class="loginH1">Welcome</h1>
		<form action="login" method="post">
			<table style="width: 100%">
				<tr>				
					<td><input type="text" name="username" required placeholder="Email Address" /></td>
				</tr>
				<tr>
					<td><input type="password" name="password" required placeholder="Password" /></td>
				</tr>

			</table>
			<br> <input type="submit" class="loginBtn" value="Login" /> <span
				style="color: red">${errorLogin} </span>
			<p>
				New User? Register for an account. <a href="RegistrationForm.jsp">Register Here.</a>
		</form>
	</div>
</body>
</html>
