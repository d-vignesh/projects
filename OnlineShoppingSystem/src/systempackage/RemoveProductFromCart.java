package systempackage;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/RemoveProductFromCart")
public class RemoveProductFromCart extends HttpServlet {
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		 
		int id = Integer.parseInt(request.getParameter("id")) ;
		
		ArrayList<ProductClass> cartlist = (ArrayList) session.getAttribute("cartlist") ;
		ProductClass product = cartlist.get(id) ; 
		cartlist.remove(id) ; 
		session.setAttribute("cartlist" , cartlist);
		
		response.getWriter().write("removed successfully") ;
	}

}
