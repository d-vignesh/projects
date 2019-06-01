package systempackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;  
import javax.servlet.http.HttpSession ; 

@WebServlet("/RemoveProduct")
public class RemoveProduct extends HttpServlet {


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		HttpSession session = request.getSession();
		 
		int id = Integer.parseInt(request.getParameter("id")) ;
		
		ArrayList<ProductClass> billlist = (ArrayList) session.getAttribute("billinglist") ;
		ProductClass product = billlist.get(id) ; 
		int price = product.getPrice();
		billlist.remove(id) ; 
		session.setAttribute("billinglist", billlist);
		
		
		Connection con = null ; 
		Statement stmt = null ; 
		ResultSet result = null ; 
		String query = "" ;
		
		
		try {
			try {
					con = systempackage.ConnectionEstablishment.getConnection(); 
				}
			catch( Exception e )
			{
				System.out.println("connection failed "+e ) ;
			}
			
			query = "select quantity from "+session.getAttribute("category")+" where dealerid = '"+product.getDealer()+"' AND productname = '"+product.getProductname()+"'" ; 
			stmt = con.createStatement() ; 
			result = stmt.executeQuery(query) ;
			
			result.next(); 
			int available_qty = result.getInt("quantity") ;
			
			query = "update "+session.getAttribute("category")+" set quantity = "+(available_qty+product.getQuantity())+" where dealerid = '"+product.getDealer()+"' AND productname = '"+product.getProductname()+"'" ;
			
			stmt.executeUpdate(query) ; 
			
			query = "select * from "+session.getAttribute("category") ;
			
			stmt = con.createStatement();
			result = stmt.executeQuery(query) ; 
			
			ArrayList<ProductClass> productlist = new ArrayList<ProductClass>() ;
			
			while ( result.next() )
			{
				ProductClass pro = new ProductClass() ; 
				pro.setProductname(result.getString("productname")) ; 
				pro.setDealer(result.getString("dealerid"));
				pro.setPrice(result.getInt("price"));
				pro.setQuantity(result.getInt("quantity")) ; 
				
				productlist.add(pro) ;
			}
			session.setAttribute("productlist" , productlist ) ; 
		}
		
		catch(Exception e )
		{
			System.out.println("sql exception occured in remove product page"+ e ) ; 
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
		
		response.getWriter().write(""+price);
	}

}
