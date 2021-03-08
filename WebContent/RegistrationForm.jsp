<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<style><%@include file="MainStyle.css"%></style>
	<script>
		/* var check = function() {
  			if (document.getElementById('password').value ==
    			document.getElementById('confirm_password').value) {
    			document.getElementById('message').style.color = 'green';
    			document.getElementById('message').innerHTML = 'matching';
  			} else {
    			document.getElementById('message').style.color = 'red';
    			document.getElementById('message').innerHTML = 'not matching';
 			}
		} */
	</script>
<meta charset="ISO-8859-1">
<title>Registration Page</title>
</head>
<body>
<div>
	<h1>Registration</h1>
        <form action="register" method="post">
            <table style="with: 100%">
				<tr>
                    <td>First Name</td>
                    <td><input type="text" name="fname" placeholder="John" value="${user.getFirstName()}"/></td>
                </tr>
				<tr>
                    <td>Last Name</td>
                    <td><input type="text" name="lname" placeholder="Doe" value="${user.getLastName()}"/></td>
                </tr>
				<tr>
                    <td>Birthday</td>
                    <td><input type="date" name="birthday" maxlength="10" placeholder="YYYY-MM-DD" min="1899-01-01" max="2020-12-31"
                    		value="${user.getBirthday()}"/>
                    </td>
                </tr>
				<tr>
                    <td>Gender</td>
                    <td><select id="gender">
                    	<option value=""></option>
  						<option value="Male">Male</option>
  						<option value="Female">Female</option>
 						<option value="Other">Other</option>
						</select>
					</td>
                </tr>
                <tr>
                    <td>Email Address</td>
                    <td><input type="email" name="username" required placeholder="example@mail.com" value="${user.getUsername()}"/></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" id="password" name="password" required
                    		placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;" onkeyup='check();' />
                    </td>
                </tr>
				<tr>
                    <td>Confirm Password</td>
                    <td><input type="password" id="confirm_password" name="confirm_password" required
                    		placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;" onkeyup='check();' />
                    <span id='message'></span></td>
                </tr>
            </table>
            <input type="submit" value="Register" /> <span style ="color:red">${errorRegistration} </span>
        </form>
        <p>Already have an account? <a href="LoginForm.jsp">Login Here.</a>
</div>
</body>
</html>