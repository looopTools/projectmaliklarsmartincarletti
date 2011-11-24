package carletti;

import carletti.gui.MainFrame;
import carletti.service.Service;

public class App {
	
	public static void main(String[] args){
		Service.createSomeObjects();
		MainFrame mf = new MainFrame();
		mf.setVisible(true);
	}
}
