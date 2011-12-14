package carletti;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @group Martin R. Bundgaard, Malik L. Lund, Lars Nielsen
 * @author Lars Nielsen
 * @class
 *
 */
public class Opgave2PunktA {
	
	private int ID;
	
	public Opgave2PunktA(int ID){
		this.ID = ID;
		
		String query = "UPDATE SUBPRODUCT SET CURRENTSUBTREATMENTINDEX = CURRENTSUBTREATMENTINDEX+1, STATE = 'DRYING' WHERE ID =" + getID() +
				" AND STATE = 'TREATMENT'";
		Connection myConnection;
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			myConnection = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://10.37.129.3:1433/Carletti", "sa",
					"lnc20020");
			Statement stmt = myConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE+1);
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
//			if (stmt != null){
//				stmt.close();
//			}
//			if (myConnection != null){
//				myConnection.close();
//			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	
	
	
	
	

}
