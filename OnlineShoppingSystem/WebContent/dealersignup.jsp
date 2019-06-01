<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<title>dealersignup</title>
			<link rel = "stylesheet" href = "stylingfile.css" type = "text/css" />
			
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"> </script>
			<script src ="javascript/dealersignup.js"></script>
	
	</head>
	<body>
				<h2> a new seller ..</h2>
				<hr>
				
				<center> <div id = "responsemsg" style = "color : red"></div></center>
				
				<form id="dealersignform">
				<table>
					<tr>
						<td> username : </td>
						<td> <input type = "text" name = "username" id ="username" > </td>
					</tr>
					
					<tr>
						<td> password : </td>
						<td> <input type = "password" name = "password" id ="password"> </td>
					</tr>
					<tr> <td></td> </tr>
					<tr> 
					<td> <input type = "submit" value = "adduser" id = "submitbtn"> </td>
					</tr>
				 </table>	
				</form>
	</body>
</html>