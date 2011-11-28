/**
 * @author Lars Nielsen, Malik L. Lund, Martin R. Bundgaard
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class RecreateDatabase
{
    public static void main(String[] args) {
        try {
            //connection to MySQL
        	Class.forName("net.sourceforge.jtds.jdbc.Driver");
			Connection myConnection = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://10.37.129.3/master", "sa",
					"lnc20020"); // "jdbc:jtds:sqlserver://10.37.129.3:1433/Cars"
			
            
            Statement stmt = myConnection.createStatement();
            stmt.executeUpdate("DROP DATABASE Carletti");
            stmt.executeUpdate("CREATE DATABASE Carletti");
            
            System.out.println("Database recreated!");
        }
        catch (Exception e) {
            System.out.println("Error connecting to database:  "
                    + e.getMessage());
        }
    }
}
