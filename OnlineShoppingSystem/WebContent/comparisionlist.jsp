 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "java.util.*" import = "systempackage.ProductClass "%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<title>comparision list</title>
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
						productlist = (ArrayList) session.getAttribute("comparelist") ; 
						if( productlist.size() <= 1 )
						{
							session.setAttribute("errormessage" , "add more than one products to compare") ; 
							request.getRequestDispatcher("displayproducttocompare.jsp").forward( request , response) ; 
						}
						itr = productlist.iterator() ;
						int id = 1 ; 
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
									<form action = "BuyProduct" method = "post" >
										 <input type = "hidden" name = "category" value = "<%= session.getAttribute("category") %>" >
										 <input type = "hidden" name = "dealer" value = "<%= row.getDealer() %>" >
										 <input type = "hidden" name = "productname" value = "<%= row.getProductname() %>" >
										 quantity:<input type = "text" name = "quantity" size="1" value = "1">
										 </center><input type = "submit" value = "buy" /> 
									</form> 
								</td>
								
							</tr>
							
						
						<% } %>
						</table>
						
						</br>
					<center> <a href = "customerpage.jsp"> homepage .. </a> </center>
					
					<form action = "displayproducttocompare.jsp" style = "margin-left : 4%" >
						<input type = "submit" value = "back" />
					</form>
				
	</body>
</html>