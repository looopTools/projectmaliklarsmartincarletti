package carletti;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Opgave2PunktD {
	
	public Opgave2PunktD(){
		
	}
	
	public void connect(){
		int selectedProductId = 10;
		Connection myConnection;
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			myConnection = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://Malik-PB/Carletti",
					"sa",
					"sa");
			Statement stmt = myConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = stmt.executeQuery(
					" SELECT sp.ID, sp.NAME, sp.TIMEADDED, st.NAME, sp.TIMEADDED + st.DRYMIN AS minimum, sp.TIMEADDED + st.DRYPRIME AS prime, sp.TIMEADDED + st.DRYMAX AS maximum" +
					" FROM SUBPRODUCT sp, SUBTREATMENT st, SUBPRODUCT_SUBTREATMENT sp_st" +
					" WHERE sp.PRODUCT_ID = " + selectedProductId +
					"   AND sp_st.SubProduct_ID = sp.ID" +
					"   AND sp.CURRENTSUBTREATMENTINDEX = st.number" +
					"   AND st.ID = sp_st.subtreatments_ID");
			
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
	
	public static void main(String[] args){
		Opgave2PunktD tester = new Opgave2PunktD();
		tester.connect();
	}
}