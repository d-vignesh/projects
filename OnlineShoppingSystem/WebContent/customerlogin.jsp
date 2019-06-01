<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<title>customer login page</title>
			<link rel = "stylesheet" href = "stylingfile.css" type = "text/css" />
		
			
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"> </script>
			<script src ="javascript/customerlogin.js"></script>
	</head>

	<body>
			<h2> Online Shopping System .. </h2>
			
			<hr><br>
			
			
			<form id="customerloginform" style = "margin-left : 15% ">
				
				<center> <div id="responsemsg" style = "color : red "></div> </center>
				<table style = "display : inline ">
					<tr> 
					<td> <h4 style = "color : green"> CustomerLogin.. </h4></td>
					</tr>
					<tr> <td>username </td> <td> <input type = "text" name = "username" id ="username"></td> </tr>
					<tr> <td>password </td> <td> <input type = "password" name = "password" id="password"> </td> </tr>
					<tr> <td> <br> </td> </tr>
					<tr> 
						<td> <input type = "submit" value = "login" id = "submitbtn"></td> 
					</tr>
					<tr>
					<td>new user??</td>
					<td><a href = "customersignup.jsp"> signup..</a></td>
					</tr>
				</table> 
				
			</form>
			
		<!--  
				<br><br>
				<form action = "" onsubmit = "return back(this);" style = "margin-left : 3%">
					<input type = "submit" value = "back" >
				</form>
				
		-->	
			
		</body>
</html>