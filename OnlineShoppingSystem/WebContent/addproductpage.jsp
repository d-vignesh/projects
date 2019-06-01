<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>add new products</title>
	<link rel = "stylesheet" href = "stylingfile.css" type = "text/css" />
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"> </script>
	<script src ="javascript/addproduct.js"></script>
	
	</head>
	<body>
	
		<br>
		<div id = "header">
				<form action = "" onsubmit = "return back(this);" style = "margin-left : 5%">
			    	<input type = "submit" value = "back" >
		        </form>
		        
		        
				<center><h2 style = "display : inline "> Add details of your new products ..</h2></center>
				<br>
				<a href = "dealerpage.jsp" style = "display : inline ; float : left" > Homepage..</a></center>
				<form action = "LogoutServlet" name = "logout" style = "display : inline ">
					<input type = "submit" value = "logout" style = "width : 10%  ; float : right ; margin-right : 5%">
				</form>
				<br>
		</div>
		
		<hr><br>
		
		<center> <div id="responsemsg" style = "color : red"></div> </center>
	
		<center>
			<form id ="addproductform" > 
					<table>
						<tr>
							<td style = "border : none"> Category  </td>
							<td style = "border : none"> : <input type = "text" name = "category" id = "category"/> </td>
						</tr>
						
						<tr>
							<td style = "border : none"> ProductName  </td>
							<td style = "border : none"> : <input type = "text" name = "productname" id = "productname"/> </td>
						</tr>
						
						
						<tr>
							<td style = "border : none"> Price  </td>
							<td style = "border : none"> : <input type = "number" name = "price" id = "price" /> </td>
						</tr>
						
						<tr>
							<td style = "border : none"> Quantity  </td>
							<td style = "border : none"> : <input type = "number" name = "quantity" id = "quantity" /> </td>
						</tr>
						
						
						<tr>
							<td style = "border-top : 15px solid #333333 "> <input type = "submit" id ="submitbtn"  value ="addproduct"></td>
							
						</tr>
					</table>
					</br>
					
			</form>
			</br>
			
					
					
		</center>	
  	
		<div id="reportpage" style = "width : 80%"></div>		
		        			
	</body>
</html>