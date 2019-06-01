<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "java.util.*"  %>

 
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>customer page</title>
	
	<link rel = "stylesheet" href = "stylingfile.css" type = "text/css" />
	<style>
		
		input[type = submit] {
			margin-left : 5% ; 
			width : 15% ; 
			height : 20% ; 
		}
	</style>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"> </script>
	<script src ="javascript/customerpage.js"></script>
	
	<script>
		function back(elem)
		{
			elem.setAttribute("action" , "customerpage.jsp") ; 
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
		<div id = "header">
				<h2 style = "display : inline "> choose your category ..</h2>
				
				<form action = "LogoutServlet" name = "logout" style = "display : inline ">
					<input type = "submit" value = "logout" style = "width : 10%  ; float : right ; margin-right : 5%">
					
				</form>
				
		</div>
		<hr>
		
		<form action = "DisplayProducts" method = "post" >
		<%
		 Iterator itr;
		 ArrayList<String> sreport = new ArrayList<String>() ;
		 sreport = (ArrayList) session.getAttribute("searchedcategorylist") ; 
		 itr = sreport.iterator() ;
		 
		 while ( itr.hasNext())
		 {
			 %>
			
			<input type ="submit" name = "category" value = "<%= itr.next() %>">  </input></br></br>
			
		<% } %> 
		</form>
		
		</br>
		<center><pre> <a href = "billgeneration.jsp"> view order details ..</a>    <a href = "viewcart.jsp">viewcart..</a></pre> </center></br>
		<br>
		
		<form action = "" onclick = "return back(this)">
			<input type = "submit" value = "back" style = "width : 5% ; margin-left : 2%"/>
		</form>
		
	</body>
</html>