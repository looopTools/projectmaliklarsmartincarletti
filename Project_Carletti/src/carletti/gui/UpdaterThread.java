package carletti.gui;

/**
 * Continuously updates the list of SubProducts in MainFrame
 * Obs. keeps running until window is closed.
 * @author Malik Lund
 *
 */
public class UpdaterThread implements Runnable {
	private MainFrame mainFrame;
	
	public UpdaterThread(MainFrame mainFrame){
		this.mainFrame = mainFrame;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true){
			mainFrame.updateList();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
