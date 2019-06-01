package systempackage;

import java.io.IOException;
import java.util.ArrayList; 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession ; 

@WebServlet("/GenerateCompareList")
public class GenerateCompareList extends HttpServlet {
	
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		HttpSession session = request.getSession() ;
		
		ProductClass product = new ProductClass() ; 
		product.setProductname(request.getParameter("productname"));
		product.setDealer(request.getParameter("dealer"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setQuantity(Integer.parseInt(request.getParameter("quantity")));
	
		
		ArrayList<ProductClass> comparelist = (ArrayList)session.getAttribute("comparelist") ; 
		
		comparelist.add(product) ; 
		session.setAttribute("comparelist" , comparelist) ; 
		response.getWriter().write("compared") ; 
		
	}

	
	

}
