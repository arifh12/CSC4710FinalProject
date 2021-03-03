<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<script>
var check = function() {
  if (document.getElementById('password').value ==
    document.getElementById('confirm_password').value) {
    document.getElementById('message').style.color = 'green';
    document.getElementById('message').innerHTML = 'matching';
  } else {
    document.getElementById('message').style.color = 'red';
    document.getElementById('message').innerHTML = 'not matching';
  }
}
</script>
<meta charset="ISO-8859-1">
<title>Registration Page</title>
</head>
<body>
	<h1>Registration Sign-up Form</h1>
        <form action="register" method="post">
            <table style="with: 100%">
				<tr>
                    <td>First Name</td>
                    <td><input type="text" name="fname" placeholder="John"/></td>
                </tr>
				<tr>
                    <td>Last Name</td>
                    <td><input type="text" name="lname" placeholder="Doe"/></td>
                </tr>
				<tr>
                    <td>Birthday</td>
                    <td><input type="date" name="birthday" maxlength="10" placeholder="YYYY-MM-DD"/></td>
                </tr>
				<tr>
                    <td>Gender</td>
                    <td><input type="radio" id="male" name="gender" value="male">
						<label for="male">Male</label><br>
						<input type="radio" id="female" name="gender" value="female">
						<label for="female">Female</label><br>
						<input type="radio" id="other" name="gender" value="other">
						<label for="other">Other</label>
					</td>
                </tr>
                <tr>
                    <td>Email Address</td>
                    <td><input type="email" name="username" required placeholder="example@mail.com" /></td>
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
            <input type="submit" value="Register" />
        </form>
        <p>Already have an account? <a href="LoginForm.jsp">Login Here.</a>
</body>
</html>