<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>dealerpage</title>
	
	<link rel = "stylesheet" href = "stylingfile.css" type = "text/css" />
	<style>
		input[type = submit] {
			width : 30% ; 
			height : 20% ; 
		}
	</style>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"> </script>
	<script src ="javascript/dealerpage.js"></script>
	
	
	</head>
	<body>
	
		
		<br>
		<div id = "header">
				<h2 style = "display : inline "> make dealer actions ..</h2>
				<form action = "LogoutServlet" name = "logout" style = "display : inline ">
					<input type = "submit" value = "logout" style = "width : 10%  ; float : right ; margin-right : 5%">
				</form>
		</div>
		<hr>
		<br>
		
		<center>
				<form action = "addproductpage.jsp">
					<input type = "submit" value = "Add new Product">
				</form>
				<br>
				
				<form action = "changeproductprice.jsp">
					<input type = "submit" value = "changeprice">
				</form>
				<br>
				
				<form action = "updatestockpage.jsp">
					<input type = "submit" value = " Update product stocks "> 
				</form>
				</br>
				
				<form action = "GenerateReport">
					<input type = "submit" value = "generate Report">
				</form>
				</br>
				</br>
				
				
				
		</center>
		
	</body>
</html>	