<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Sunset Images - Profile</title>
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
	<div class="feedDiv">
	<h2>Profile Page</h2>
	<table>
		<tr>
			<td>Email: </td>
			<td>${userProfile.getUsername()}</td>
		</tr>
		<tr>
			<td>Name: </td>
			<td>${userProfile.getFirstName()} ${userProfile.getLastName()}</td>
		</tr>
		<tr>
			<td>Gender: </td>
			<td>${userProfile.getGender()}</td>
		</tr>
		<tr>
			<td>Birthday: </td>
			<td>${userProfile.getBirthday()}</td>
		</tr>
		<tr></tr>
		<tr>
			<td>Followers: </td>
			<td>${userProfile.getFollowerCount()}</td>
		</tr>
		<tr>
			<td>Following: </td>
			<td>${userProfile.getFollowingCount()}</td>
		</tr>
	</table>
	<c:if test="${ username ne userProfile.getUsername() }">
		<a href="${userProfile.getFollowStatus() ? 'unfollow' : 'follow'}?target-user=${userProfile.getUsername()}" >
			${userProfile.getFollowStatus() ? 'Unfollow' : 'Follow'}</a>
	</c:if>
	</div>
</body>
</html>