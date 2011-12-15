package carletti;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * 
 * @group Martin R. Bundgaard, Malik L. Lund, Lars Nielsen
 * @author Lars Nielsen
 * @class
 *
 */
public class Opgave2PunktA implements Runnable {
	
	private Connection connection;
	private int ID;
	
	public Opgave2PunktA(Connection connection){
		this.connection = connection;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter ID: ");
		int id = Integer.parseInt(scanner.nextLine());
		setID(id);
		String query = "UPDATE SUBPRODUCT SET CURRENTSUBTREATMENTINDEX = CURRENTSUBTREATMENTINDEX+1, STATE = 'DRYING' WHERE ID =" + getID() +
				" AND STATE = 'TREATMENT'";
//		Connection myConnection;
		try {
//			Class.forName("net.sourceforge.jtds.jdbc.Driver");
//			myConnection = DriverManager.getConnection(
//					"jdbc:jtds:sqlserver://10.37.129.3:1433/Carletti", "sa",
//					"lnc20020");
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE+1);
//			ResultSet res = stmt.executeQuery(query); //Query to be executed
//			
//			ResultSetMetaData resMeta = res.getMetaData();
//			while (res.next()){
//				StringBuffer buffer = new StringBuffer();
//				for (int i = 1; i < resMeta.getColumnCount()+1; i++){
//					buffer.append(resMeta.getColumnName(i) + ": " +  res.getString(i) + " ");
//				}
//				System.out.println(buffer);
//			}
			
			boolean res = stmt.execute(query);
			
			if(res == true){
				System.out.println("Did you type a valid ID");
			}
			else if(res == false){
				System.out.println("Transaction completed");
			}
//			if (res != false){
//				res.close();
//			}
			if (stmt != null){
				stmt.close();
			}
//			if (myConnection != null){
//				myConnection.close();
//			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	

}
