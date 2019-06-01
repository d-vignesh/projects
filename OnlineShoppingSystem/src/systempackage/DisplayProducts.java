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
import systempackage.ProductClass ;
import java.util.ArrayList ; 
import javax.servlet.http.HttpSession;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections; 

@WebServlet("/DisplayProducts")
public class DisplayProducts extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con = null ; 
		Statement stmt = null ; 
		ResultSet result = null ; 
		String query = "" ;
		HttpSession session = request.getSession(); 
		String category = request.getParameter("category") ; 
		
		try {
			try {
					con = systempackage.ConnectionEstablishment.getConnection(); 
				}
			catch( Exception e )
			{
				System.out.println("connection failed "+e ) ;
			}
			
			query = "select * from "+category ;
			
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
		//	Collections.sort(product) ; 
			session.setAttribute("category" , category) ; 
			
			session.setAttribute("productlist" , product ) ; 
			request.getRequestDispatcher("displayproducts.jsp").forward(request, response);
			}
		
		catch(Exception e ) 
		{
			System.out.println("sql exception occured "+e ) ; 
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
