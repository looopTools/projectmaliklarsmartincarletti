package carletti.gui.dialogs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.model.LongToStringParser;
import carletti.model.SubTreatment;
import carletti.model.Treatment;

/**
 * This class is used by the JTable in CreateNewSubTreatmentDialog.
 * It contains the current list of SubTreatment-objects to be added to a
 * new Treatment-object in a new Product-object
 * @author Malik Lund
 *
 */
public class NewProductSubTreatmentsTableModel extends AbstractTableModel {
	// Column headers.
	private String[] coloumnNames = {"Name", "Minimum", "Optimal", "Maximum"};
	// The actual data.
	private Treatment treatment = new Treatment("temp");

	@Override
	public int getColumnCount() {
		return coloumnNames.length;
	}

	@Override
	public int getRowCount() {
		return treatment.getSubTreatments().size();
	}
	
	@Override
	public String getColumnName(int col){
		return coloumnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {
		List<SubTreatment> subTreatments = treatment.getSubTreatments();
		if (col < 1){
			return subTreatments.get(row).getName();
		} else {
			long time;
			if (col == 1){
				time = subTreatments.get(row).getDryMin();
			}
			else if (col == 2){
				time = subTreatments.get(row).getDryPrime();
			} else {
				time = subTreatments.get(row).getDryMax();
			}
			return LongToStringParser.parseLongToString(time);
		}
	}
	
	@Override
	public Class getColumnClass(int c){
		return getValueAt(0,c).getClass();
	}

	/**
	 * Add a new SubTreatment to the list of subTreatments.
	 * @param name Name of the subtreatment.
	 * @param min Minimum drying time.
	 * @param optimal Optimal or prime drying time.
	 * @param max Maximum drying time.
	 */
	public void newSubTreatment(String name, long min, long optimal, long max) {
		treatment.createSubTreatment(name, min, optimal, max);
		fireTableDataChanged();
	}
	
	/**
	 * Returns a temporary treatment containing the subtreatments
	 * @return A Treatment object containing the SubTreatments. OBS! Recreate using
	 * 		   the Service class!! This is only temporary and it will not be saved
	 * 		   in the database!
	 */
	public Treatment getData(){
		return treatment;
	}
	
	/**
	 * 
	 * @param subTreatments
	 */
	public void setData(Treatment t){
		treatment = t;
		fireTableDataChanged();
	}

}
