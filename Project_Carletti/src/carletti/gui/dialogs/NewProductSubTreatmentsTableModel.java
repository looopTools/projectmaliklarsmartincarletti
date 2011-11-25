package carletti.gui.dialogs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * This class is used by the JTable in CreateNewSubTreatmentDialog.
 * It contains the current list of SubTreatment-objects to be added to a
 * new Treatment-object in a new Product-object
 * @author Malik Lund
 *
 */
public class NewProductSubTreatmentsTableModel extends AbstractTableModel {
	// Column headers.
	private String[] coloumnNames = {"Name", "Minimum (ms)", "Optimal (ms)", "Maximum (ms)"};
	// The actual data.
	private ArrayList<Object[]> data = new ArrayList<Object[]>();

	@Override
	public int getColumnCount() {
		return coloumnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}
	
	@Override
	public String getColumnName(int col){
		return coloumnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {
		if (col < 1){
			return data.get(row)[col];
		} else {
			return convertLongToString((Long)data.get(row)[col]);
		}
	}
	
	@Override
	public Class getColumnClass(int c){
		return getValueAt(0,c).getClass();
	}
	
	private String convertLongToString(long time){
		int days = (int)(time / (1000*60*60*24));
		time = time - days*1000*60*60*24;
		int hours = (int)(time / (1000*60*60));		
		time = time - hours * 1000*60*60;
		int minutes = (int)(time / (1000*60));
		time = time - minutes * 1000*60;
		int seconds = (int)(time / (1000));
		return String.format("%dd%dh%dm%ds", days, hours, minutes, seconds);
	}

	/**
	 * Add a new SubTreatment to the list of subTreatments.
	 * @param name Name of the subtreatment.
	 * @param min Minimum drying time.
	 * @param optimal Optimal or prime drying time.
	 * @param max Maximum drying time.
	 */
	public void newSubTreatment(String name, long min, long optimal, long max) {
		Object[] newData = {
			name, new Long(min), new Long(optimal), new Long(max)	
		};
		data.add(newData);
		fireTableDataChanged();
	}
	
	/**
	 * Returns a list of all table data.
	 * @return A new List containing the data.
	 */
	public List<Object[]> getData(){
		return new ArrayList<Object[]>(data);
	}

}
