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
import javax.servlet.http.HttpSession;

@WebServlet("/ChangePrice")
public class ChangePrice extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 Connection con = null ; 
		    Statement stmt = null ; 
		    ResultSet result = null ; 
		    String query = "" ; 
		    
		    HttpSession session = request.getSession() ; 
			try {
				try {
					con = systempackage.ConnectionEstablishment.getConnection() ;
					}
				catch (Exception e )
					{
						System.out.println("Connection failed "+e ) ; 
					}
					stmt = con.createStatement() ;
					
					response.setContentType("text/plain");
					String category = request.getParameter("category") ; 
					String productname = request.getParameter("productname") ; 
					int price = Integer.parseInt(request.getParameter("price")) ; 
					String dealer = session.getAttribute("username").toString() ;
					
					query = "select * from totalcategories where catogery = '"+category+"'" ; 
					result = stmt.executeQuery(query) ; 
				
					if( !result.next() )
					{
						
						response.getWriter().write("no such category ,, first add your product");
					}
					else {
							query = "select * from "+category+" where productname = '"+productname+"' and dealerid = '"+dealer+"'" ; 
							
							result = stmt.executeQuery(query) ;
				
							if( !result.next() )
							{
								response.getWriter().write("no such product ,, add your product ");
							}
							else {
								query = "update	"+category+" set price = "+price+" where productname = '"+productname+"' and dealerid = '"+dealer+"'" ; 
								stmt.executeUpdate(query) ;
								response.getWriter().write("successfully updated ");
							}
					}
					
			}
			
			catch(Exception e ) {
				 System.out.println("sql exception occured"+e) ; 
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
