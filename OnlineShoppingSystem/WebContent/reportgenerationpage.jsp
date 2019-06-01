<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "java.util.*" import = "systempackage.ReportClass"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<title>Stock Report</title>
			<link rel = "stylesheet" href = "stylingfile.css" type = "text/css" />
			<style>
				table {
					width : 80% ; 
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
			
			<script>
			function back(elem ) 
			{
				elem.setAttribute("action" , "dealerpage.jsp") ; 
				elem.submit() ; 
			}
			</script>
	</head>
	
	<body>
	
				<%
				if( session.getAttribute("username") == null )
				{
					response.sendRedirect("login.jsp") ; 
				}
				%>
				<br>
				<h2 style = "display : inline "> Your stock details .. </h2>
				<!--  <form action = "LogoutServlet" name = "logout" style = "display : inline ">
						<input type = "submit" value = "logout" style = "width : 10%  ; float : right ; margin-right : 5%">
					</form>
					-->	
				<hr>
				<br>
				<center>
				<table>
					<tr>
						<th> Category </th>
						<th> Productname </th>
						<th> price </th>	
						<th> Quantity </th>
						
					</tr>
					
					<% Iterator itr;
					 ArrayList<ReportClass> sreport = new ArrayList<ReportClass>() ;
					 sreport = (ArrayList) session.getAttribute("report") ; 
					 itr = sreport.iterator() ; 
					 while( itr.hasNext() )
					 {
						 ReportClass row = new ReportClass() ; 
						 row = (ReportClass) itr.next() ; %>
						 
						<tr>
							<td> <%= row.getCategory() %> </td>
							<td> <%= row.getProductname() %> </td>
							<td> <%= row.getPrice() %> </td>
							<td> <%= row.getQuantity() %> </td>
						</tr>
						<% } %>
						</tr>	 
						 
			</table>
			</center>
					</br>
					<center><a href = "dealerpage.jsp"> Homepage..</a></center>
			</br>
			
				<!--  	<form action = "" onsubmit = "return back(this);" style = "margin-left : 4%">
				    	<input type = "submit" value = "back" >
			        </form> -->
		
	</body>
</html>