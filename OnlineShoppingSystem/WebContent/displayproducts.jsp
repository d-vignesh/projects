<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "java.util.*" import = "systempackage.ProductClass"%>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
		<head>
				<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
				<title>display product</title>
				<link rel = "stylesheet" href = "stylingfile.css" type = "text/css" />
				<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"> </script>
				<script src = "javascript/displayproduct.js"> </script>
				
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
					
						<br>
					<div id = "header">
							<h2 style = "display : inline "> here are your products ..</h2>
							<form action = "LogoutServlet" name = "logout" style = "display : inline ">
								<input type = "submit" value = "logout" style = "width : 10%  ; float : right ; margin-right : 5%">
							</form>
					</div>
					
						<hr>
						
						</br>
						
						<div style = "float : right" >
						<form action = "SearchServlet" method = "post" style = "display : inline ; " name = "searchdealer" onsubmit = "return searchValidation('searchdealer')"  >
							<p style = "display : inline" > searchByDealer </p>
							<input type = "text" name = "searchkey"  />
							<input type = "submit" value = "search" />
							<input type = "hidden" name = "category" value = "<%= session.getAttribute("category") %>" />
							<input type = "hidden" name = "searchby" value = "dealerid" />
						</form>
						
						<form action = "SearchServlet" method = "post" style = "display : inline ;" name = "searchname" onsubmit = "return searchValidation('searchname')" >
							<p style = "display : inline" > searchByName </p>
							<input type = "text" name = "searchkey" />
							<input type = "submit" value = "search"  />
							<input type = "hidden" name = "category" value = "<%= session.getAttribute("category") %>" />
							<input type = "hidden" name = "searchby" value = "productname" />
						</form>
						</div>
						</br>
						
						<center> <div id = "responsemsg" style = "color : red "> </div></center>
						
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
							productlist = (ArrayList) session.getAttribute("productlist") ; 
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
									<td style = "width : 10%" name ="available_quantity"> <%= row.getQuantity() %> </td>
									<td style = "text-align : center" width = "15%"> 
										 <form>
											 <input type = "hidden" name = "category" id = "bcategory" value = "<%= session.getAttribute("category") %>" >
											 <input type = "hidden" name = "dealer" id = "bdealer" value = "<%= row.getDealer() %>" >
											 <input type = "hidden" name = "productname" id = "bproductname " value = "<%= row.getProductname() %>" >
											 <input type = "hidden" name = "price" id = "bprice" value = "<%= row.getPrice() %>" />
											 <input type = "hidden" name = "id" id = "bid" value = "<%= id %>" >
											 quantity:<input type = "text" name = "quantity" id = "bquantiy" size="1" value = "1">
											 </center><input type = "submit" value = "buy" id = "bsubmitbtn" /> 
										 </form>
									</td>
									<td style = "text-aling : center" width = "10%">
										<form>
											 <input type = "hidden" name = "category" id = "ccategory" value = "<%= session.getAttribute("category") %>" >
											 <input type = "hidden" name = "dealer" id = "cdealer" value = "<%= row.getDealer() %>" >
											 <input type = "hidden" name = "productname" id = "cproductname" value = "<%= row.getProductname() %>" >
											 <input type = "hidden" name = "id" id = "cid" value = "<%= id++ %>" >
											 <input type = "hidden" name = "price" id = "cprice" value = "<%= row.getPrice() %>" />
											 <input type = "hidden" name = "Quantity" id = "cquantity" value = "<%= row.getQuantity() %>" />
											 <input type = "hidden" name = "jspname" id = "jspname" value = "displayproduct.jsp" />
											 </center><input type = "submit" id = "csubmitbtn" value = "addtocart"/> 
										</form> 
									</td>
								</tr>
								
							
							<% } %>
							</table>
							
						</br>
						<center> <pre><a href = "customerpage.jsp"> homepage .. </a>   <a href = "viewcart.jsp"> view cart..</a>   <a href = "billgeneration.jsp"> view order details ..</a></pre></center>
						
						<form action = "CompareProduct" method = "get" name = "compare">
							<input type = "submit" value = "compareProducts" style = "float : right ; margin-right : 7%"/>
						</form>
						
						<form action = "" onsubmit = "return back(this);" style = "margin-left : 10%">
						    	<input type = "submit" value = "back" >
					    </form>
						
				</body>
</html>