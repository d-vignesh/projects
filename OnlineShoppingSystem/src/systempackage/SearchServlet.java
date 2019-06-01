 package systempackage;

import java.io.IOException;
import javax.servlet.http.HttpSession ; 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con = null ; 
		Statement stmt = null ; 
		ResultSet result = null ; 
		String query = "" ;
		HttpSession session = request.getSession() ;
		
		try {
			try {
					con = systempackage.ConnectionEstablishment.getConnection(); 
				}
			catch( Exception e )
			{
				System.out.println("connection failed "+e ) ;
			}
			
			query = "select * from "+session.getAttribute("category")+" where "+request.getParameter("searchby")+" LIKE '%"+request.getParameter("searchkey")+"%'"  ; 
		//	System.out.println(query) ; 
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
			
			if( product.size() == 0 ) 
			{
				request.setAttribute("errormessage" , "no result found") ; 
			}
		//	request.setAttribute("category" , request.getParameter("category")) ; 
			session.setAttribute("searchlist" , product ) ; 
			request.getRequestDispatcher("displaysearchedcategory.jsp").forward(request, response);
			}
		
		catch(Exception e ) 
		{
			System.out.println("sql exception occured omg "+e ) ; 
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
