package carletti;

import java.util.Scanner;

import carletti.service.Service;

public class Opgave2PunktC implements Runnable
{

	private static Service s;
	
	public void run()
	{
		s = Service.getInstance();
		String command = "";
		String argument = "";

		Scanner argumentScanner = new Scanner(System.in);
		System.out.println("Type ID; ");
		argument = argumentScanner.nextLine();
		s.DryingTime(Integer.parseInt(argument));
	}
}
