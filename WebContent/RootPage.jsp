<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<style><%@include file="MainStyle.css"%></style>
<title>Root User Page</title>
</head>
<body>
<nav>
		<img class="navLink" id="navLogo" src="https://www.sunsetwatchfamilycampground.com/wp-content/uploads/2019/10/Sunset-Logo-2.png" width="150"/>
		<a class="navLink" href="LoginForm.jsp">Log Out</a>
	</nav>
	
	<h3>Click the button below to initialize the database</h3>
	<form action="initialize" method="post">
		<input type="submit" value="Initialize Database"/>
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
		<table align="center" width="80%" border="1">
  			<tr>
    			<th>Username</th>
    			<th>Posts</th>
  			</tr>
  			<c:forEach items="${topUsers}" var="user">
  				<tr align="center">
    				<td>${user.key}</td>
    				<td>${user.value}</td>
  				</tr>
  			</c:forEach>
		</table>
		
	<h2>5. Popular Users</h2>
		<table align="center" width="80%" border="1">
  			<tr>
    			<th>Username</th>
    			<th>Followers</th>
  			</tr>
  			<c:forEach items="${popularUsers}" var="user">
  				<tr align="center">
    				<td>${user.getUsername()}</td>
    				<td>${user.getFollowerCount()}</td>
  				</tr>
  			</c:forEach>
		</table>
		
	<h2>6. Common Users</h2>
		<form action="common-users" method="post">
		<table align="center" width="80%">
  			<tr align="center">
    			<th>User X</th>
    			<th></th>
    			<th>User Y</th>
  			</tr>
  			<tr align="center">
  				<td>
  					<select name="userX" >
            			<option value=""></option>
            			<c:forEach items="${userList}" var="user">
  							<option value="${user.getUsername()}">${user.getUsername()}</option>
  						</c:forEach>
					</select>
  				</td>
  				<td></td>
  				<td>
  					<select name="userY" >
            			<option value=""></option>
            			<c:forEach items="${userList}" var="user">
  							<option value="${user.getUsername()}">${user.getUsername()}</option>
  						</c:forEach>
					</select>
  				</td>
  			</tr>
  			<tr align="center">
    			<td>${showUserX}</td>
    			<td></td>
    			<td>${showUserY}</td>
  			</tr>
  			<tr align="center">
  				<td></td>
  				<td><input type="submit" value="Search"/></td>
  				<td></td>
  			</tr>
		</table>
		</form>
		<br>
		<c:if test="${not empty commonUsers}">
		<table align="center" width="25%" border="1">
  			<tr>
    			<th>Username</th>
  			</tr>
  			<c:forEach items="${commonUsers}" var="user">
  				<tr align="center">
    				<td>${user}</td>
  				</tr>
  			</c:forEach>
		</table>
		</c:if>
		
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
		<table align="center" width="80%" border="1">
  			<tr>
    			<th>Username</th>
    			<th>Likes</th>
  			</tr>
  			<c:forEach items="${positiveUsers}" var="user">
  				<tr align="center">
    				<td>${user.key}</td>
    				<td>${user.value}</td>
  				</tr>
  			</c:forEach>
		</table>
		
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
		<table align="center" width="80%" border="1">
  			<tr>
    			<th>Username</th>
    			<th>First Name</th>
    			<th>Last Name</th>
  			</tr>
  			<c:forEach items="${inactiveUsers}" var="user">
  				<tr align="center">
    				<td>${user.getUsername()}</td>
    				<td>${user.getFirstName()}</td>
    				<td>${user.getLastName()}</td>
  				</tr>
  			</c:forEach>
		</table>
</body>
</html>