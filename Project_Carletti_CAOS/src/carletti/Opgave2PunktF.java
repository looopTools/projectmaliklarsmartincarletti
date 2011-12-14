package carletti;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Opgave2PunktF {

	public Opgave2PunktF() {

		String query = "EXEC trashOldData";
		Connection myConnection;
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			myConnection = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://10.37.129.3:1433/Carletti", "sa",
					"lnc20020");
			Statement stmt = myConnection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE + 1);
			boolean res = stmt.execute(query);

			if (res == true) {
				System.out.println("Idiot");
			} else if (res == false) {
				System.out.println("Sweet sucess");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
