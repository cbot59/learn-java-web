<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Update Student</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
<link type="text/css" rel="stylesheet" href="css/add-student-style.css" />
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>Foobar University</h2>
		</div>
	</div>

	<div id="container">
		<h3>Update Student</h3>

		<form action="StudentControllerServlet" method="GET">
			<input type="hidden" name="command" value="UPDATE" /> 
			<input type="hidden" name="studentId" value="${student.id}" />
			<table>
				<tbody>
					<tr>
						<td>First Name:</td>
						<td><input type="text" name="firstName"
							value="${student.firstName}" /></td>
					</tr>
					<tr>
						<td>Last Name:</td>
						<td><input type="text" name="lastName"
							value="${student.lastName}" /></td>
					</tr>
					<tr>
						<td>Email:</td>
						<td><input type="text" name="email" value="${student.email}" /></td>
					</tr>
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save" /></td>
					</tr>
				</tbody>
			</table>
		</form>

		<div style="clear: both;"></div>
		<p>
			<a href="StudentControllerServlet">Back to list</a>
		</p>
	</div>
</body>
</html>