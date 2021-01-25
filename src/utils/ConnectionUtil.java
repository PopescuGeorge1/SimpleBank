package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	Connection conn = null;
	public static Connection conDB()
	{
		try {
		 Class.forName("com.mysql.jdbc.Driver");
		 Connection con=DriverManager.getConnection(
				 credentialsDB.credentials().get(0)[0],
				 credentialsDB.credentials().get(1)[0],
				 credentialsDB.credentials().get(2)[0]
						 );
		 return con;
		}catch(ClassNotFoundException e) {
			return null;
		}catch(SQLException e) {
			return null;
		}
	}
}
