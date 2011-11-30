package carletti.gui.dialogs;

import javax.swing.JDialog;

/**
 * Handles the selection of an existing treatment plan.
 * This treatment plan is then associated with a
 * produt in creation.
 * @author Malik Lund
 *
 */
public class SelectTreatmentDialog extends JDialog {
	
	private NewProductSubTreatmentsTableModel tableModel;
	
	public SelectTreatmentDialog(NewProductSubTreatmentsTableModel tableModel){
		this.setModal(true);
		this.setTitle("Select treatment");
		
		this.tableModel = tableModel;
	}
}
