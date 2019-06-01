package systempackage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.* ;
import java.util.ArrayList;
import java.util.Collections ; 
import javax.servlet.http.HttpSession ; 
import systempackage.ProductClass ; 

@WebServlet("/CustomerPageGenerator")
public class CustomerPageGenerator extends HttpServlet {
	
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
			
			query = "select * from totalcategories" ;
			stmt = con.createStatement(); 
			result = stmt.executeQuery(query) ; 
			
			ArrayList<String> categoryarray = new ArrayList<String>() ; 
			
			
			
			while( result.next() ){
				categoryarray.add(result.getString("catogery")) ; 
			}
				HttpSession session = request.getSession(); 
				Collections.sort(categoryarray) ; 
				session.setAttribute("categorylist", categoryarray);
				session.setAttribute("cartlist", new ArrayList<ProductClass>());
				
			}
		catch(Exception e )
		{
			System.out.println("sql exception occured in customergenerator page"+ e ) ; 
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
