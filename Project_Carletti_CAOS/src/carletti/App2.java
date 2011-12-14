package carletti;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

public class App2 {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("net.sourceforge.jtds.jdbc.Driver");
		String url = args[1];
		String username = args[2];
		String password = args[3];
		Connection connection = DriverManager.getConnection(url, username, password);
		
		String[] commands = {
			" a: nextTreatment",
			" b: printSubProducts",
			" c: getTimeInfoSubProduct",
			" d: printSubProductOfProductType",
			" e: newSubProduct",
			" f: trashOldData",
			" g: trashOldData",
			"-1: exit"
		};

		for (String s: commands){
			System.out.println(s);
		}
		System.out.print("Type a command: ");
		
		HashMap<String, Runnable> runnables = new HashMap<String, Runnable>();
		runnables.put("a", new Opgave2PunktA()); //Lars 
		runnables.put("b", new Opgave2PunktB(connection));
		runnables.put("d", new Opgave2PunktD(connection));
		runnables.put("g", new Opgave2PunktG(connection));
		runnables.put("c", new Opgave2PunktC());
		runnables.put("e", new Opgave2PunktE());
		
		Scanner commandScanner = new Scanner(System.in);
		String command = commandScanner.nextLine();

		while (!command.equals("exit")){
			Runnable r = runnables.get(command);
			if (r != null){
				r.run();
			}
			System.out.print("Type a command: ");
			command = commandScanner.nextLine();
		}
	}
}
