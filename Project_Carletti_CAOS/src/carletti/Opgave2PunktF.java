package carletti;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Opgave2PunktF implements Runnable{
	private Connection connection;

	public Opgave2PunktF(Connection connection) {
		this.connection = connection;
	}
	
	public void run(){
		String query = "EXEC trashOldData";
		Connection myConnection;
		try {
			Statement stmt = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE + 1);
			boolean res = stmt.execute(query);

			if (res == true) {
				System.out.println("Idiot");
			} else if (res == false) {
				System.out.println("Sweet sucess");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
