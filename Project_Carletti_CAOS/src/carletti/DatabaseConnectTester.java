package carletti;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectTester {
	
	public DatabaseConnectTester(){
		
	}
	
	public void connect(){
		Connection myConnection;
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			myConnection = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://Malik-PB/Carletti",
					"sa",
					"sa");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DatabaseConnectTester tester = new DatabaseConnectTester();
		tester.connect();
	}

}
