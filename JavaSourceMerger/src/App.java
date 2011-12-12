import java.io.IOException;

import maliklund87.javasourcemerger.SourceMergerTwo;
import gui.Chooser;


public class App {

	public static void main(String[] args){
		Chooser ch = new Chooser();
		try {
			SourceMergerTwo.merge();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
