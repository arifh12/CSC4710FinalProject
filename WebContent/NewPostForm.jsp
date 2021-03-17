<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sunset Images - New Post</title>
</head>
<body>
	<form action="insert-image" method="post">
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
	</form>
</body>
</html>