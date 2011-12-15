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
		try {
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE+1);
			boolean res = stmt.execute(query);
			
			if(res == true){
				System.out.println("Did you type a valid ID");
			}
			else if(res == false){
				System.out.println("Transaction completed");
			}
			if (stmt != null){
				stmt.close();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}	
	}
}
