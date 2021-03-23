<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Sunset Images - Feed</title>
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
			<span style="color:red">${likeError}</span>
			<c:forEach items="${imageList}" var="image">
				<div class="postHeader">
					<p id="postUser">${image.getPostUser()}: ${image.getDescription()} Tags: ${image.getTags().toString()}</p>
					<p id="postedAt">${image.getPostedAt()}</p>
				</div>
				<br>
				<p>
					<img id="postImg" src="${image.getUrl()}" alt="Uh-oh. Something went wrong!" />
				</p>
				<p>
					<label>Likes: ${image.getLikes()} </label>
					<c:if test="${username eq image.getPostUser()}">
						<a href="delete?image-id=<c:out value='${image.getImageId()}'/>" >Delete</a>
						<a href="edit?image-id=<c:out value='${image.getImageId()}'/>" >Edit</a>
					</c:if>
				</p>
				<p>
					<a href="<c:out value="${image.getLikeStatus() ? 'unlike' : 'like'}" />?image-id=<c:out value='${image.getImageId()}'/>" ><c:out value="${image.getLikeStatus() ? 'Unlike' : 'Like'}" /></a>
				</p>
				<hr>
			</c:forEach>
			<p id="feedEnd">End of feed.</p>
		</div>
</body>
</html>