package carletti;

import java.util.HashMap;
import java.util.Scanner;

public class App2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
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
		runnables.put("b", new Opgave2PunktB());
		runnables.put("d", new Opgave2PunktD());
		runnables.put("g", new Opgave2PunktG());
		
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
