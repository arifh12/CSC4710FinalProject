<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Root User Page</title>
</head>
<body>
	<h3>Click the button below to initialize the database</h3>
	<form action="initialize" method="post">
		<input type="submit" value="Initialize Databse"/>
	</form>
	<hr>
	<h2>1. Cool Images</h2>
		<table align="center" width="80%" border="1">
  			<tr>
    			<th>Image ID</th>
    			<th>URL</th>
    			<th>Likes</th>
  			</tr>
  			<c:forEach items="${coolImages}" var="img">
  				<tr align="center">
    				<td>${img.getImageId()}</td>
    				<td><img style="max-width:400px; max-height:400px" src="${img.getUrl()}" alt="Uh-oh. Something went wrong!"/></td>
    				<td>${img.getLikes()}</td>
  				</tr>
  			</c:forEach>
		</table>
	<h2>2. New Images</h2>
		<table align="center" width="80%" border="1">
  			<tr>
    			<th>Image ID</th>
    			<th>URL</th>
    			<th>Posted At</th>
  			</tr>
  			<c:forEach items="${newImages}" var="img">
  				<tr align="center">
    				<td>${img.getImageId()}</td>
    				<td><img style="max-width:400px; max-height:400px" src="${img.getUrl()}" alt="Uh-oh. Something went wrong!"/></td>
    				<td>${img.getPostedAt()}</td>
  				</tr>
  			</c:forEach>
		</table>
	<h2>3. Viral Images</h2>
		<table align="center" width="80%" border="1">
  			<tr>
    			<th>Image ID</th>
    			<th>URL</th>
    			<th>Likes</th>
  			</tr>
  			<c:forEach items="${viralImages}" var="img">
  				<tr align="center">
    				<td>${img.getImageId()}</td>
    				<td><img style="max-width:400px; max-height:400px" src="${img.getUrl()}" alt="Uh-oh. Something went wrong!"/></td>
    				<td>${img.getLikes()}</td>
  				</tr>
  			</c:forEach>
		</table>
	<h2>4. Top Users</h2>
		<c:forEach items="${topUsers}" var="user">
			${user.getUsername()}
		</c:forEach>
	<h2>5. Popular Users</h2>
		<c:forEach items="${popularUsers}" var="user">
			${user.getUsername()}
		</c:forEach>
	<h2>6. Common Users</h2>
		${testing}
		
	<h2>7. Top Tags</h2>
		<table align="center" width="80%" border="1">
  			<tr>
    			<th>Tag</th>
    			<th>User Count</th>
  			</tr>
  			<c:forEach items="${topTags}" var="tag">
  				<tr align="center">
    				<td>${tag.key}</td>
    				<td>${tag.value}</td>
  				</tr>
  			</c:forEach>
		</table>
	<h2>8. Positive Users</h2>
		<c:forEach items="${positiveUsers}" var="user">
			${user.getUsername()}
		</c:forEach>
	<h2>9. Poor Images</h2>
		<table align="center" width="80%" border="1">
  			<tr>
    			<th>Image ID</th>
    			<th>URL</th>
    			<th>Post User</th>
  			</tr>
  			<c:forEach items="${poorImages}" var="img">
  				<tr align="center">
    				<td>${img.getImageId()}</td>
    				<td><img style="max-width:400px; max-height:400px" src="${img.getUrl()}" alt="Uh-oh. Something went wrong!"/></td>
    				<td>${img.getPostUser()}</td>
  				</tr>
  			</c:forEach>
		</table>
	<h2>10. Inactive Users</h2>
		<c:forEach items="${inactiveUsers}" var="user">
			${user.getUsername()}
		</c:forEach>
</body>
</html>