package systempackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession ; 

@WebServlet("/CustomerValidateServlet")
public class CustomerValidateServlet extends HttpServlet {
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username") ;
		String password = request.getParameter("password") ; 
		
		
		response.setContentType("text/plain");
		boolean identity = this.validationMethod(username , password) ;
		if( identity )
		{
			HttpSession session = request.getSession(); 
			session.setAttribute("username" , username ) ; 
			session.setAttribute("billinglist", new ArrayList<ProductClass>());
			response.getWriter().write("valid");
		}
		else
		{
			
			response.getWriter().write("invalid");
		} 
		
		
	}
	
	boolean validationMethod ( String username , String password )
	{
		Connection con = null ; 
		Statement stmt = null ;
		ResultSet result = null ; 
		try {
			try {
				con = systempackage.ConnectionEstablishment.getConnection() ;
				}
			catch(Exception e )
				{
				System.out.println("Database Connection failed "+ e.toString()) ; 
				}
			stmt = con.createStatement() ;
			String query = "" ; 
			query = "select * from customertable where username = '"+username+"' AND password = '"+password+"'" ; 
			result = stmt.executeQuery(query) ; 
			if( result.next()) return true ; 
			}
		catch(Exception e)
			{
				System.out.println("no class"+e) ; 
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
		
		return false ; 
	}


}
