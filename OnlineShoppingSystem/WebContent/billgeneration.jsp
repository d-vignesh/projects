<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "java.util.*" import = "systempackage.ProductClass" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<title> bill generation </title>
			<link rel = "stylesheet" href = "stylingfile.css" type = "text/css" />
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"> </script>
			<script src = "javascript/billgeneration.js"> </script>
			
			<style>
				table {
					width : 100% ; 
				}
				th {
					border-bottom : 5px solid black ; 
					background-color : green ; 
					color : white ; 
					height : 30px ; 
				}
				td {
					border-bottom : 1px solid white ;
					height : 50px ; 
				}
				
			</style>
	</head>
	<body>
	
			<%
			if( session.getAttribute("username") == null )
			{
				response.sendRedirect("login.jsp") ; 
			}
			%>
		
			<br>
			<div id = "header">
					<h2 style = "display : inline "> here are your orders..</h2>
					<form action = "LogoutServlet" name = "logout" style = "display : inline ">
						<input type = "submit" value = "logout" style = "width : 10%  ; float : right ; margin-right : 5%">
					</form>
			</div>
			<hr>
			
			<center> <div id = "responsemsg" style = "color : red"></div></center>
			
			<table>
					<tr>
						<th>Productname </th>
						<th>Dealer </th>
						<th>Price </th>
						<th>Quantity </th>
					</tr>
					</br>
				
				<%  
					Iterator itr ; 
					ArrayList<ProductClass> productlist = new ArrayList<ProductClass>() ; 
					productlist = (ArrayList) session.getAttribute("billinglist") ; 
					itr = productlist.iterator() ;
					int id = 0 , total = 0 ; 
					
					while ( itr.hasNext() )
					{
						ProductClass row = new ProductClass() ; 
						row = (ProductClass) itr.next() ; 
						total += row.getPrice() ; 
						%>
						<tr>
							<td> <%= row.getProductname() %> </td>
							<td> <%= row.getDealer() %> </td>
							<td> <%= row.getPrice() %> </td>
							<td> <%= row.getQuantity() %> </td> 
							<td style = "text-align : center" width = "15%" > 
							<form>
								<input type = "submit" id = "removebtn" name = "remove" value = "remove" >
								<input type = "hidden" name = "id" value = "<%= id++ %>" >
							</form>
							</td>
							
						</tr>
						
					
					<% } %>
					</table>
					<br>
					<input id = "total" type = "button" value = "totalamount : <%= total %>" style = " font-style : italic ; font-weight : bold ;float : right ; margin-right : 5% ; background-color : green ; color : white ; border : 0 ; height : 30px ">
					
					<br>	
					
				<center> <pre> <a href = "customerpage.jsp">homepage..</a>  <a href = "displayproducts.jsp">productpage..</a>  <a href = "viewcart.jsp"> viwecart.. </a></pre></center>
	</body>
</html>