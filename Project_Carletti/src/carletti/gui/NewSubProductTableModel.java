/**
 * @author Lars Nielsen, Malik Lasse Lund, Martin R¿nn Bungaard
 * @class NewSubProductTableModel
 * @programmer Lars Nielsen
 */
package carletti.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.model.Product;

public class NewSubProductTableModel extends AbstractTableModel{
	// Column headers.
	private String[] coloumnNames = {"Time left", "Complete time", "ID", "Name", "State", "Subtreatment", "Number of Subtreatments"};
	// The actual data.
	private ArrayList<Object[]> data = new ArrayList<Object[]>();
		
	public NewSubProductTableModel(){
		
	}

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
	
	@Override
	public Class getColumnClass(int c){
		return getValueAt(0,c).getClass();
	}
//	int id, String name, Product product, long timeAdded
	public void newSubProduct(int id, String name, Product product, long timeAdded) {
		Object[] newData = {
			new Integer(id), name, product, new Long(timeAdded)	
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
