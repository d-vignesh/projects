package systempackage;

import java.sql.DriverManager ; 
import java.sql.Connection ; 
import java.sql.SQLException ;

public class ConnectionEstablishment {
	public static Connection getConnection() throws SQLException , ClassNotFoundException
	{
		Connection con = null ;
		Class.forName("com.mysql.jdbc.Driver") ; 
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stockdatabase","root","") ;
		
		return con ;
	}

}
