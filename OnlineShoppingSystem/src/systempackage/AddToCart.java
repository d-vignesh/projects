package systempackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList ; 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AddToCart")
public class AddToCart extends HttpServlet {
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		Connection con = null ; 
		Statement stmt = null ; 
		ResultSet result = null ; 
		String query = "" ;
		HttpSession session = request.getSession(); 
		
		
		String dealer = request.getParameter("dealer") ; 
		String productname = request.getParameter("productname") ; 
		int quantity = Integer.parseInt(request.getParameter("quantity")) ; 
		
		try {
			try {
					con = systempackage.ConnectionEstablishment.getConnection(); 
				}
			catch( Exception e )
			{
				System.out.println("connection failed "+e ) ;
			}
			
			query = "select quantity,price from "+session.getAttribute("category")+" where dealerid = '"+request.getParameter("dealer") + "' and productname = '"+request.getParameter("productname")+"'" ;
			stmt = con.createStatement();
			
			result = stmt.executeQuery(query) ;
			result.next();
			int available_quantity = result.getInt("quantity");
			int price = result.getInt("price");
			
			if( quantity > available_quantity)
			{
				response.getWriter().write("insufficient Quantity");
			}
			
			else {
		
				ProductClass cart = new ProductClass() ; 
				cart.setDealer(dealer) ; 
				cart.setProductname(productname);
				cart.setPrice(price);
				cart.setQuantity(quantity);
				
				session = request.getSession(); 
				ArrayList<ProductClass> cartlist = (ArrayList<ProductClass>) session.getAttribute("cartlist") ; 
				cartlist.add(cart) ; 
				session.setAttribute("cartlist" , cartlist) ;
				
				response.getWriter().write("add successfully") ; 
			
			}
			}
			
		 catch(Exception e ) 
			{
				System.out.println("sql exception occured omgggg "+e ) ; 
			}
			
			finally {
				if( result != null )
				{
					try {
						result.close(); 
					}
					catch ( Exception e )
					{}
					try {
						stmt.close() ; 
					}
					catch ( Exception e )
					{}
					try {
						con.close(); 
					}
					catch(Exception e )
					{}
				}
			}
			
		
		
		
	}

}
