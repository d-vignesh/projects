<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<title>change product price </title>
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"> </script>
			<script src ="javascript/changeproductprice.js"></script>
			<link rel = "stylesheet" href = "stylingfile.css" type = "text/css" />
			
			
	</head>
	<body>
	
				<br>
				<div id = "header">
						<h2 style = "display : inline ">Make changes to you product ..</h2>
						<form action = "LogoutServlet" name = "logout" style = "display : inline ">
							<input type = "submit" value = "logout" style = "width : 10%  ; float : right ; margin-right : 5%">
						</form>
				</div>
				
				<hr>
				<br>
				
				
						<center> <div id = "responsemsg" style = "color : red"></div></center>
				
				<center>
				<form>
				
				<table>
							<tr>
								<td> Category  </td>
								<td> : <input type = "text" name = "category" id = "category"/> </td>
							</tr>
							
							<tr>
								<td> ProductName  </td>
								<td> : <input type = "text" name = "productname" id = "productname" /> </td>
							</tr>
							
							
							<tr>
								<td> NewPrice  </td>
								<td> : <input type = "number" name = "price" id = "price"/> </td>
							</tr>
							
							<tr> <td> <br> </td> </tr>
							
							<tr>
								<td> <input type = "submit" value = "Updateprice" id = "submitbtn"></td>
								
							</tr>
						</table>
					
				</form>
				</center>
				<br>
				<center><a href = "dealerpage.jsp"> Homepage..</a></center>
				</br>
						
						<form action = "" onsubmit = "return back(this);" style = "margin-left : 10%">
					    	<input type = "submit" value = "back" >
				        </form>
	</body>
</html>