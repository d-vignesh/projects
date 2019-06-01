<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login page</title>
<link rel = "stylesheet" href = "stylingfile.css" type = "text/css" />

	<style>
		input[type = submit] {
			width : 20% ; 
		}
	</style>

	<script>
		function dealer(elem) {
			elem.setAttribute("action","dealerlogin.jsp") ;
			elem.submit() ;
		}
	
		function customer(elem) {
			elem.setAttribute("action","customerlogin.jsp") ; 
			elem.submit() ; 
		}
	</script>

</head>

<body>
	<br><br>
	<hr>
	
		<center>
		<h1> Welcome to e-cart ..</h1>
		<br>
		<form action = "" onsubmit = "return dealer(this) ; ">
			<input type = "submit"  value = "login as dealer" >
		</form>
		<br>
		<form action = "" onsubmit = "return customer(this) ; ">
			<input type = "submit"  value = "login as customer" >
		</form>
		</center>
</body>
</html>