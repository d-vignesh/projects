<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "java.util.*" import = "systempackage.ProductClass"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<title>view cart</title>
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"> </script>
			<script src = "javascript/viewcart.js"> </script>
			
			<link rel = "stylesheet" href = "stylingfile.css" type = "text/css" />
			
			<style>
				table {
					width : 100% ; 
				}
				th {
					border-bottom : 5px solid black ; 
					background-color : green ; 
					color : white ; 
					height : 30px ; 
					text-align : center ; 
				}
				td {
					border-bottom : 1px solid white ;
					height : 50px ; 
					text-align : center ; 
				}
				
			</style>	
	</head>
	
	<body>
			
				<br>
				<div id = "header">
					<h2 style = "display : inline "> products in cart ..</h2>
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
						<th>Quantity Avilable </th>
					</tr>
					</br>
				
				<%  
					Iterator itr ; 
					ArrayList<ProductClass> productlist = new ArrayList<ProductClass>() ; 
					productlist = (ArrayList) session.getAttribute("cartlist") ; 
					itr = productlist.iterator() ;
					
					while ( itr.hasNext() )
					{
						ProductClass row = new ProductClass() ; 
						row = (ProductClass) itr.next() ; 
						int id = 0 ; 
						%>
						<tr>
							<td> <%= row.getProductname() %> </td>
							<td> <%= row.getDealer() %> </td>
							<td> <%= row.getPrice() %> </td>
							<td style = "width : 10%"> <%= row.getQuantity() %> </td>
							<td style = "text-align : center" width = "15%"> 
								<form>
									 <input type = "hidden" name = "category" id = "category" value = "<%= session.getAttribute("category") %>" >
									 <input type = "hidden" name = "dealer" id = "dealer" value = "<%= row.getDealer() %>" >
									 <input type = "hidden" name = "productname" id = "productname" value = "<%= row.getProductname() %>" >
									 <input type = "hidden" name = "id" id = "id" value = "<%= id %>" >
									 quantity:<input type = "text" name = "quantity" size="1" id = "quantity" value = "1">
									 </center><input type = "submit" id = "bsubmitbtn"value = "buy"/>
									 <td style = "text-align : center" width = "15%" > 
								</form> 
								<form>
								<input type = "submit" id = "rsubmitbtn" name = "remove" value = "remove" >
								<input type = "hidden" name = "rid" value = "<%= id++ %>" >
							</form>
							</td> 
								
							</td>
							</tr>
						
					
					<% } %>
					</table>
					
					<center><pre> <a href = "billgeneration.jsp"> view order details ..</a>  <a href = "customerpage.jsp"> homepage..</a>  <a href = "displayproducts.jsp"> productpage..</a>
	</body>
</html>