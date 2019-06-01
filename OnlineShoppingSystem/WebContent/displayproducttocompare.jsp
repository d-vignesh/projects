<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "java.util.*" import = "systempackage.ProductClass "%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<title>compare products</title>
			<link rel = "stylesheet" href = "stylingfile.css" type = "text/css" />
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"> </script>
			<script src = "javascript/displayproducttocompare.js"> </script>
			
			
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
				<%
				if( session.getAttribute("username") == null )
				{
					response.sendRedirect("login.jsp") ; 
				}
				%>
				
					<div id = "header">
						<h2 style = "display : inline "> here are your products ..</h2>
						<form action = "LogoutServlet" name = "logout" style = "display : inline ">
							<input type = "submit" value = "logout" style = "width : 10%  ; float : right ; margin-right : 5%">
						</form>
					</div>
				
					<hr>
					
					<center> <div id = "responsemsg" style = "color : red"></div></center>
					</br>
					
					<div style = "float : right" >
					<form action = "CompareSearchServlet" method = "post" style = "display : inline ; "  >
						<p style = "display : inline" > searchByDealer </p>
						<input type = "text" name = "searchkey"  />
						<input type = "submit" value = "search" />
						<input type = "hidden" name = "category" value = "<%= session.getAttribute("category") %>" />
						<input type = "hidden" name = "searchby" value = "dealerid" />
					</form>
					
					<form action = "CompareSearchServlet" method = "post" style = "display : inline ;" OnSubmit = "document.getElementById('add').disabled = true ;" >
						<p style = "display : inline" > searchByName </p>
						<input type = "text" name = "searchkey" />
						<input type = "submit" value = "search"  />
						<input type = "hidden" name = "category" value = "<%= session.getAttribute("category") %>" />
						<input type = "hidden" name = "searchby" value = "productname" />
					</form>
					</div>
					</br>
					
					<table>
						<tr>
							<th>Productname </th>
							<th>Dealer </th>
							<th>Price </th>
							<th>Quantity Available</th>
						</tr>
						</br>
					
					<%  
						Iterator itr ; 
						ArrayList<ProductClass> productlist = new ArrayList<ProductClass>() ; 
						productlist = (ArrayList) session.getAttribute("listtocompare") ; 
						itr = productlist.iterator() ;
						int id = 0 ; 
						
						while ( itr.hasNext() )
						{
							ProductClass row = new ProductClass() ; 
							row = (ProductClass) itr.next() ; 
							%>
							<tr>
								<td> <%= row.getProductname() %> </td>
								<td> <%= row.getDealer() %> </td>
								<td> <%= row.getPrice() %> </td>
								<td style = "width : 10%"> <%= row.getQuantity() %> </td>
								<td style = "text-align : center" width = "15%"> 
									<form>
										 <input type = "hidden" name = "dealer" id = "dealer" value = "<%= row.getDealer() %>" >
										 <input type = "hidden" name = "productname" id = "productname" value = "<%= row.getProductname() %>" >
										 <input type = "hidden" name = "price" id = "price" value = "<%= row.getPrice() %>" >
										 <input type = "hidden" name = "quantity" id = "quantity" value = "<%= row.getQuantity() %>" >
										 <input type = "hidden" name = "id" id = "id" value ="<%= id++ %>" >
										 <input type = "submit" name = "submitbtn" value = "AddtoCompare" id = "submitbtn" /> 
									</form> 
								</td>
							</tr>
							
						
						<% } %>
						</table>
						
					</br>
					<center> <pre><a href = "customerpage.jsp"> homepage .. </a>   <a href = "viewcart.jsp"> view cart..</a>   <a href = "billgeneration.jsp"> view order details ..</a></pre></center>
					
					<form>
						<input type = "submit" value = "compare" id = "compare" style = "float : right ; margin-right : 7%"/>
					</form>
					
					<form action = "displayproducts.jsp" onsubmit = "return back(this);" style = "margin-left : 10%">
					    	<input type = "submit" value = "back" >
				    </form>
			
	</body>
</html>