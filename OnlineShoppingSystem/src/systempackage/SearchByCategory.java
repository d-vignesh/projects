package systempackage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.* ;
import java.util.ArrayList;

@WebServlet("/SearchByCategory")
public class SearchByCategory extends HttpServlet {


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
			
			query = "select * from totalcategories where catogery LIKE '%"+request.getParameter("searchcategory")+"%'" ;
			
			stmt = con.createStatement(); 
			result = stmt.executeQuery(query) ; 
			
			ArrayList<String> searchedcategoryarray = new ArrayList<String>() ; 
			
			
			
			while( result.next() ){
				searchedcategoryarray.add(result.getString("catogery")) ; 
			}
				HttpSession session = request.getSession(); 
				session.setAttribute("searchedcategorylist", searchedcategoryarray); 
				if( searchedcategoryarray.size() == 0 ) response.getWriter().write("not found") ; 
				else response.getWriter().write("found");
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
