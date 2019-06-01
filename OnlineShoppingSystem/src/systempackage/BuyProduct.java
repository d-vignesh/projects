package systempackage;

import java.io.IOException;
import javax.servlet.http.HttpSession ; 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.* ; 

@WebServlet("/BuyProduct")
public class BuyProduct extends HttpServlet {
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("in servlet") ; 
		Connection con = null ; 
		Statement stmt = null ; 
		ResultSet result = null ; 
		String query = "" ;
		HttpSession session = request.getSession(); 
		
		try {
			try {
					con = systempackage.ConnectionEstablishment.getConnection(); 
				}
			catch( Exception e )
			{
				System.out.println("connection failed "+e ) ;
			}
			
			query = "select quantity,price from "+session.getAttribute("category")+" where dealerid = '"+request.getParameter("dealer") + "' and productname = '"+request.getParameter("productname")+"'" ;
			
			stmt = con.createStatement() ; 
			result = stmt.executeQuery(query) ;
			result.next(); 
			int available_quantity = result.getInt("quantity") ; 
			int required_quantity = Integer.parseInt(request.getParameter("quantity")) ; 
			int price = (result.getInt("price")) ; 
			
				if( available_quantity < required_quantity )
				{
					response.getWriter().write( "failed");
				}
				else {	
			int remaining_quantity = (available_quantity - required_quantity) ; 
			query = "update "+request.getParameter("category")+" set quantity = "+remaining_quantity+" where dealerid = '"+request.getParameter("dealer") +"' and productname = '"+request.getParameter("productname")+"'" ; 
			stmt.executeUpdate(query) ; 
			
			
			query = "select * from "+session.getAttribute("category") ;
			
			stmt = con.createStatement();
			result = stmt.executeQuery(query) ; 
			
			ArrayList<ProductClass> product = new ArrayList<ProductClass>() ;
			
			while ( result.next() )
			{
				ProductClass pro = new ProductClass() ; 
				pro.setProductname(result.getString("productname")) ; 
				pro.setDealer(result.getString("dealerid"));
				pro.setPrice(result.getInt("price"));
				pro.setQuantity(result.getInt("quantity")) ; 
				
				product.add(pro) ;
			}
			session.setAttribute("productlist" , product ) ; 
			
			ProductClass row = new ProductClass() ; 
			row.setDealer(request.getParameter("dealer")) ; 
			row.setProductname(request.getParameter("productname")) ; 
			row.setPrice(price);
			row.setQuantity(required_quantity);
			
			ArrayList<ProductClass> billlist  = (ArrayList) session.getAttribute("billinglist") ; 
			billlist.add(row) ; 
			session.setAttribute("billinglist", billlist);
			
			
			response.getWriter().write(""+remaining_quantity);
			
			}}
		 
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
