package carletti;

import java.io.IOException;
import java.util.Scanner;

/**
 * 
 * @group Martin R. Bundgaard, Malik L. Lund, Lars Nielsen
 * @author Lars Nielsen
 * @class
 * 
 */
public class App {

	public static void main(String[] args) {
		String[] commands = { "nextTreatment", "printSubProducts",
				"getTimeInfoSubProduct", "printSubProductOfProductType",
				"newSubProduct", "trashOldData", "EXIT" };
		String command = "";
		String argument = "";

		printCommands(commands);

		System.out.print("Type a Command: ");
		Scanner commandScanner = new Scanner(System.in);
		command = commandScanner.nextLine();

		Scanner argumentScanner = new Scanner(System.in);

		while (!command.equals(commands[6])) {

			if (command.equals(commands[0])) {

				System.out.print("Type ID: ");
				argument = argumentScanner.nextLine();
				Opgave2PunktA nt = new Opgave2PunktA(Integer.parseInt(argument));

			} 
			else if (command.equals(commands[1])) {

			}

			else if (command.equals(commands[2])) {

			}

			else if (command.equals(commands[3])) {

			}

			else if (command.equals(commands[4])) {

			}

			else if (command.equals(commands[5])) {

				Opgave2PunktF tod = new Opgave2PunktF();
			}
			System.out.print("Type a Command: ");
			command = commandScanner.nextLine();
		}

		if (command.equals(commands[6])) {
			System.out.println("Thanks for using this Applikation");
		}

	}

	private static void printCommands(String[] commands) {
		System.out.println("----Commands----");
		for (int i = 0; i < commands.length; i++) {
			System.out.println(commands[i] + " ");
		}
		System.out.println("----Enter Command----");

	}
}