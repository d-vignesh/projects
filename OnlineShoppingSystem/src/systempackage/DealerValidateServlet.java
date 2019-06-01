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


@WebServlet("/DealerValidateServlet")
public class DealerValidateServlet extends HttpServlet {

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username") ;
		String password = request.getParameter("password") ; 
		response.setContentType("text/plain");
		boolean identity = this.validationMethod(username , password) ;
		HttpSession session = request.getSession(); 
		
		if( identity )
		{
			
			session.setAttribute("username", username);
			response.getWriter().write("valid") ; 
		}
		else response.getWriter().write("invalid"); 
		
		
	
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
			query = "select * from dealertable where username = '"+username+"' AND password = '"+password+"'" ; 
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
