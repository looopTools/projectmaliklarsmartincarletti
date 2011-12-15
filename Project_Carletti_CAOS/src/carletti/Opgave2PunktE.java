package carletti;

import java.sql.Connection;
import java.util.Scanner;

import carletti.model.Position;
import carletti.model.Product;
import carletti.model.SubProduct;
import carletti.model.Treatment;
import carletti.service.Service;

public class Opgave2PunktE implements Runnable
{

	private static Service s;
	
	public void run()
	{
		String sName = "";
		int pId = 0;
		int tId = 0;
		int po = 0;

		s = Service.getInstance();
		
		Scanner commandScanner = new Scanner(System.in);
		
		System.out.println("Positions 1 - 2 - 3 - 4 - 5 - 6 - 7 - 8 - 9");
		System.out.println("Type Position: ");
		po = Integer.parseInt(commandScanner.nextLine());
		
		System.out.println("Product ID's: 10 Red Chocolate MMs | 15 Liquorice | 19 Coffee Bean");
		System.out.println("Chouse Product");
		pId = Integer.parseInt(commandScanner.nextLine());
		

		
		
		System.out.println("Type subproduct name: ");
		sName = commandScanner.nextLine();
						
		
		Position po1 = new Position("", po);
		
		if (pId == 10)
		{
			tId = 11;
		}
		else if (pId == 15)
		{
			tId = 16;
		}
		else if (pId == 19)
			tId = 20;
			
		
		Treatment t1 = new Treatment(tId, "");
		Product p1 =  s.createProduct(pId, "", "", t1);
		SubProduct sp = s.createSubProduct("'" + sName + "'", p1, po1);
		
	} 
		
}
