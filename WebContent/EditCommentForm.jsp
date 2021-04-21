<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Sunset Images - Edit Comment</title>
	<style><%@include file="MainStyle.css"%></style>
</head>
<body>
	<nav>
		<img class="navLink" id="navLogo" src="https://www.sunsetwatchfamilycampground.com/wp-content/uploads/2019/10/Sunset-Logo-2.png" width="150"/>
		<a class="navLink" href="feed">Feed</a>
		<a class="navLink" href="community">Community</a>
		<a class="navLink" href="profile?target-user=${username}">Profile</a>
		<a class="navLink" id="newPost" href="NewPostForm.jsp">New Post</a>
		<a class="navLink" href="LoginForm.jsp">Log Out</a>
	</nav>
	<form action="update-comment" method="post">
	<div class="feedDiv">
	<input type="hidden" name="image-id" value="${imageId}" />
	<h2>Edit Comment</h2>
	<table align="center" width="80%">
		<tr>
			<td>Comment: </td>
			<td><input type="text" name=message value="${message}" required/> </td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" value="Save" /><span style="color: red">${errorComment}</span>
	</table>
	</div>
	</form>
</body>
</html>