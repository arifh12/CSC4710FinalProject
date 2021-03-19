<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Sunset Images - New Post</title>
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
	<form action="insert-image" method="post">
	<div class="loginDiv">
	<h2>Create a New Post</h2>
	<table>
		<tr>
			<td>Image URL: </td>
			<td><input type="url" name=url required /> </td>
		</tr>
		<tr>
			<td>Description: </td>
			<td><input type="text" name=description /> </td>
		</tr>
		<tr>
			<td>Tags: </td>
			<td><input type="text" name=tags placeholder="separate, tags, like, this"/> </td>
		</tr>
		<tr>
			<td><input type="submit" value="Post" /><span style="color: red">${errorNewPost}</span>
	</table>
	</div>
	</form>
</body>
</html>