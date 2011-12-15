package carletti;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author Malik Lund
 *
 */
public class Opgave2PunktG implements Runnable{
	private Connection connection;
	
	public Opgave2PunktG(Connection connection){
		this.connection = connection;
	}
	
	public void run(){
		int selectedProductId = 10;
		try {
//			Class.forName("net.sourceforge.jtds.jdbc.Driver");
//			myConnection = DriverManager.getConnection(
//					"jdbc:jtds:sqlserver://Malik-PB/Carletti",
//					"sa",
//					"sa");
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			// fetch the id of the subProduct to test the trigger with.
			ResultSet res = stmt.executeQuery("SELECT ID FROM SUBPRODUCT WHERE NAME = 'PunktGSubProduct'");
			res.first();
			int subProductId = res.getInt("ID");
			
			// change state of the fetched id.
			stmt.execute("UPDATE SUBPRODUCT SET STATE = 'TREATMENT' WHERE ID = " + subProductId);
			
			// fetch content of exceededsubproducts-table to see changes.
			res = stmt.executeQuery("SELECT * FROM EXCEEDEDSUBPRODUCTS");
			ResultSetMetaData resMeta = res.getMetaData();
			while (res.next()){
				StringBuffer buffer = new StringBuffer();
				for (int i = 1; i < resMeta.getColumnCount()+1; i++){
					buffer.append(resMeta.getColumnName(i) + ": " +  res.getString(i) + " ");
				}
				System.out.println(buffer);
			}
			
			if (res != null){
				res.close();
			}
			if (stmt != null){
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
