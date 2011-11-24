package carletti.gui.dialogs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class NewProductSubTreatmentsTableModel extends AbstractTableModel {
	private String[] coloumnNames = {"Name", "Minimum (ms)", "Optimal (ms)", "Maximum (ms)"};
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
		return data.get(row)[col];
	}
	
	public Class getColumnClass(int c){
		return getValueAt(0,c).getClass();
	}

	public void newSubTreatment(String name, long min, long optimal, long max) {
		Object[] newData = {
			name, new Long(min), new Long(optimal), new Long(max)	
		};
		data.add(newData);
		fireTableDataChanged();
	}
	
	public List<Object[]> getData(){
		return data;
	}

}
