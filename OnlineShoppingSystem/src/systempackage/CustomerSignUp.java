package systempackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CustomerSignUp")
public class CustomerSignUp extends HttpServlet {


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con = null ; 
		Statement stmt = null ;
		ResultSet result = null ; 
		String query = "" ; 
		try {
			try {
				con = systempackage.ConnectionEstablishment.getConnection() ;
				}
			catch(Exception e )
				{
				System.out.println("Database Connection failed "+ e.toString()) ; 
				}
		stmt = con.createStatement();
		String username = request.getParameter("username") ; 
		String password = request.getParameter("password") ; 
		
		query = "insert into customertable ( username , password ) values ('"+username+"','"+password+"')" ;
		
		
		try {
			stmt.executeUpdate(query) ; 
			response.getWriter().write("signedup");
		}
		catch(SQLIntegrityConstraintViolationException e )
		{ 
			response.getWriter().write("failed") ; 
		}
		
	 }
		catch(Exception e )
		{
			System.out.println("failed to connect "+ e ) ; 
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
