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
	private Connection connection;
	
	public Opgave2PunktB(Connection connection){
		this.connection = connection;
	}
	
	public void run(){
		try {
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}