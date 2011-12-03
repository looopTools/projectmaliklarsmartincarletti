package carletti.gui;

import carletti.gui.dialogs.NextSubTreatmentDialog;

/**
 * Continuously updates the list of SubProducts in MainFrame
 * Obs. keeps running until window is closed.
 * @author Malik Lund
 *
 */
public class UpdaterThreadForTabbedMain implements Runnable {
	private MainFrameTabbed mainFrame;
	private NextSubTreatmentDialog nextSubTreatmentDialog;
	
	public UpdaterThreadForTabbedMain(MainFrameTabbed mainFrame){
		this.mainFrame = mainFrame;
	}
	
	public UpdaterThreadForTabbedMain(NextSubTreatmentDialog nextSubTreatmentDialog)
	{
		this.nextSubTreatmentDialog =  nextSubTreatmentDialog;
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
