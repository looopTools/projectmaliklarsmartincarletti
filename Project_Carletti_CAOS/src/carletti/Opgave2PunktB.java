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
public class Opgave2PunktB implements Runnable{
	
	public Opgave2PunktB(){
		
	}
	
	public void run(){
		Connection myConnection;
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			myConnection = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://Malik-PB/Carletti",
					"sa",
					"sa");
			Statement stmt = myConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = stmt.executeQuery(
					" SELECT sp.ID, sp.NAME, (sp.TIMEADDED + st.DRYMAX) AS timeRemaining" +
					" FROM SUBPRODUCT sp, SUBTREATMENT st, SUBPRODUCT_SUBTREATMENT sp_st" +
					" WHERE sp.state = 'DRYING'" +
					"   AND sp.ID = sp_st.SubProduct_ID" +
					"   AND st.ID = sp_st.subtreatments_ID" +
					"   AND sp.CURRENTSUBTREATMENTINDEX = st.number" +
					" ORDER BY timeRemaining");
			
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
}