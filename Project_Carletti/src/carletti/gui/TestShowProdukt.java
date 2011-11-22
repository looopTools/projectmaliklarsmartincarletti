package carletti.gui;

import carletti.service.Service;

public class TestShowProdukt
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Service.createSomeObjects();
		ShowProductFrame sp = new ShowProductFrame();
		sp.setVisible(true);

	}

}


