package systempackage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.* ; 
import javax.servlet.http.HttpSession ; 

@WebServlet("/AddProduct")
public class AddProduct extends HttpServlet { 
    Connection con = null ; 
    Statement stmt = null ; 
    ResultSet result = null ; 
    String query = "" ; 
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
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
				
				String category = request.getParameter("category") ; 
				String productname = request.getParameter("productname") ; 
				int price = Integer.parseInt(request.getParameter("price")) ; 
				int quantity = Integer.parseInt(request.getParameter("quantity")) ; 
		
				String dealer = session.getAttribute("username").toString() ; 
				System.out.println(category +" "+productname+" "+price+" "+quantity );
				
				
				response.setContentType("text/plain");
				boolean exist = true ; 
				
				query = "select * from "+category ; 
				try {
					result = stmt.executeQuery(query) ;
				}
				catch ( Exception e )
				{
					
					query = "create table "+category+" ( productname varchar(30) , dealerid varchar(30) , price int , quantity int )" ; 
					stmt.executeUpdate(query) ; 
					query = "insert into "+category+" values ('"+productname+"','"+dealer+"',"+price+","+quantity+")" ;
					stmt.executeUpdate(query) ;
					query = "insert into totalcategories values ( '"+category+"')" ; 
					stmt.executeUpdate(query) ; 
					exist = false ; 
					response.getWriter().write("added successfully");
				}
				if( exist )
				{
					query = "select * from "+category+" where dealerid = '"+dealer+"' and  productname = '"+productname+"'" ; 
					
					result = stmt.executeQuery(query) ; 
					if( result.next() )
					{
						
						response.getWriter().write( "you already have such a product ,, if you need you can update its price and availability");
				
					}
					else
					{
						
						query = "insert into "+category+" values ('"+productname+"','"+dealer+"',"+price+","+quantity+")" ;
						stmt.executeUpdate(query) ; 
						response.getWriter().write("added successfully");
						
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
