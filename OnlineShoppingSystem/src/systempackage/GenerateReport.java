package systempackage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.* ; 
import javax.servlet.http.HttpSession; 
import java.util.ArrayList ; 
import systempackage.ReportClass ; 

@WebServlet("/GenerateReport")
public class GenerateReport extends HttpServlet {

       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		System.out.println("in report servlet");
		Connection con = null ; 
		Statement stmt = null ; 
		ResultSet result = null ; 
		String query = "" ; 
		HttpSession session = request.getSession(); 
		
		try {
			try {
				con = systempackage.ConnectionEstablishment.getConnection();
			}
			catch ( Exception  e )
			{
				System.out.println("connection establishment failed "+e ) ; 
			}
			
			//System.out.println("exection reached here ") ; 
			query = "select * from totalcategories" ;
			stmt = con.createStatement(); 
			result = stmt.executeQuery(query) ; 
			
			ArrayList<String> categoryarray = new ArrayList<String>() ; 
			
			
			
			while( result.next() ){
				categoryarray.add(result.getString("catogery")) ; 
			}
			//System.out.println(categoryarray.size()) ;  
			
			String dealer = session.getAttribute("username").toString() ; 
			ArrayList<ReportClass> report = new ArrayList<ReportClass>() ; 
			
			for( String category : categoryarray )
			{
				query = "select * from "+category+" where dealerid = '"+dealer+"'" ; 
				result = stmt.executeQuery(query) ;
				
				while ( result.next() )
				{
					ReportClass record = new ReportClass() ; 
					record.setCategory(category) ; 
					record.setProductname(result.getString("productname"));
					record.setPrice(result.getInt("price"));
					record.setQuantity(result.getInt("quantity"));
					
					report.add(record) ;
				}
			}
			
			
			session.setAttribute("report" , report ) ;
			
			
		}
		
		catch ( Exception e )
		{
			System.out.println("sql exception occured "+e ) ; 
		}
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	

}
