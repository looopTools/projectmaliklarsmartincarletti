package carletti;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectTester {
	
	public DatabaseConnectTester(){
		
	}
	
	public void connect(){
		Connection myConnection;
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			myConnection = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://Martin-PC/Carletti", "sa", "178255");
			Statement stmt = myConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = stmt.executeQuery("SELECT * FROM SUBPRODUCT;");
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
			if (myConnection != null){
				myConnection.close();
			}
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
