package carletti.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import carletti.model.SubProduct;

public class Dao
{
	
	
	public static void drying(int ID){
		
		Connection myConnection;
		
		String s = "SELECT sp.ID, sp.NAME ,(sp.TIMEADDED + st.DRYMIN) AS timeRemainingMIN, " +
				" (sp.TIMEADDED + st.DRYPRIME) AS timeRemainingPRIME, (sp.TIMEADDED + st.DRYMAX) AS timeRemainingMAX" +
				" FROM SUBPRODUCT sp, PRODUCT p, TREATMENT t, TREATMENT_SUBTREATMENT ts, SUBTREATMENT st " +
				" WHERE " + ID + " " + "= sp.ID" +
				" AND sp.PRODUCT_ID = p.ID" +
				" AND p.TREATMENT_ID = T.ID" +
				" AND t.ID = ts.Treatment_ID" +
				" AND st.ID = ts.subTreatments_ID" +
				" AND sp.CURRENTSUBTREATMENTINDEX = st.number";
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			myConnection = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://MARTIN-PC/Carletti", "sa", "178255");
			Statement stmt = myConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = stmt.executeQuery(s);
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

	
	

	public static void storeSubproduct(SubProduct sp)
	{
//		int ID = sp.getId();
		int CURRENTSUBTREATMENTINDEX = sp.getCurrentSubTreatmentIndex();
		String NAME = sp.getName();
		String STATE = sp.getState().toString();
		long TIMEADDED = sp.getTimeAdded();
		int POSITION_ID = sp.getPosition().getId();
		int PRODUCT_ID = sp.getProduct().getId();
		
		String s = "DECLARE @id int" +
				" SET @id = (SELECT SEQ_COUNT FROM SEQUENCE)" +
				" UPDATE SEQUENCE SET SEQ_COUNT = (@id + 1)" +
				" INSERT INTO SUBPRODUCT(ID, CURRENTSUBTREATMENTINDEX, NAME, STATE, TIMEADDED, POSITION_ID, PRODUCT_ID)" +
				" VALUES (  @id" + ", " + CURRENTSUBTREATMENTINDEX + ", " +  NAME  + ", " + "'" + STATE + "'" + ", " + TIMEADDED + ", " + POSITION_ID + ", " + PRODUCT_ID + ")";
		

		Connection myConnection;
		try
		{
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			myConnection = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://MARTIN-PC/Carletti", "sa", "178255");
			Statement stmt = myConnection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			boolean res = stmt.execute(s);
			if (stmt != null)
			{
				stmt.close();
			}
			if (myConnection != null)
			{
				myConnection.close();
			}
			
        	System.out.println("Subprodukt er oprettet!");

		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();

		} catch (SQLException e)
		{
			int ee = e.getErrorCode();
			System.out.println(ee);
		        if (ee == 3609)
		        {
		        	System.out.println("Position optaget!");
		        }
		        else if (ee == 515)
		        {
		        	System.out.println("Navnet kan ikke være null!");
		        }
		        else if (ee == 102)
		        {
		        	System.out.println("Navnet skal være større end 0 karaktere");
		        }
		        else if (ee == 207)
		        {
		        	System.out.println("husk ' ' uden om navnet");
		        }
		        else if (ee == 0)
		        	System.out.println("Ingen forbindelse til database");
		        
		}
		
	}

}
