<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Sunset Images - Community</title>
	<style><%@include file="MainStyle.css"%></style>
</head>
<body>
	<nav>
		<img class="navLink" id="navLogo" src="https://www.sunsetwatchfamilycampground.com/wp-content/uploads/2019/10/Sunset-Logo-2.png" width="150"/>
		<a class="navLink" href="feed">Feed</a>
		<a class="navLink" href="community">Community</a>
		<a class="navLink" id="newPost" href="NewPostForm.jsp">New Post</a>
		<a class="navLink" href="LoginForm.jsp">Log Out</a>
	</nav>
	<div class="feedDiv">
	<form action="search" method="get">
		<label>Search: </label>
		<input type="text" name="search-field" required>
		<input type="submit" value="Search">
	</form>
	<table>
		<c:forEach items="${userList}" var="userC">
		<tr>
			<td>
				${userC.getFirstName()} ${userC.getLastName()}
			</td>
			<td>
				<c:if test="${username != userC.getUsername()}">
					<a href="follow?following-user=<c:out value='${userC.getUsername()}'/>">Follow</a>	
				</c:if>
			</td>
		</tr>
		</c:forEach>
	</table>
	</div>
</body>
</html>