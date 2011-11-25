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
import carletti.model.SubProduct;
import carletti.service.Service;

public class NewSubProductTableModel extends AbstractTableModel{
	// Column headers.
	private String[] coloumnNames = {"Time left", "Complete time", "ID", "Name", "State", "Subtreatment", "Number of Subtreatments"};
	// The actual data.
	private ArrayList<Object[]> data = new ArrayList<Object[]>();
	private List<SubProduct> newData;
		
	public NewSubProductTableModel(){
		newData = Service.getAllNotWastedSubProducts();
	}

	@Override
	public int getColumnCount() {
		return coloumnNames.length;
	}

	@Override
	public int getRowCount() {
		return newData.size();
	}

	@Override
	public String getColumnName(int col){
		return coloumnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {
		SubProduct sp = newData.get(row);
		Object[] value = {
				sp.getTimeLeft(sp.timeLeft()), sp.getTime(sp.getTimeAdded()), 
				new Integer(sp.getId()), sp.getName(), 
				sp.getState(), sp.getCurrentSubTreatment().getName(), 
				sp.getSubtreatments().size()	
		};
		return value[col];
	}
	
	@Override
	public Class getColumnClass(int c){
		return getValueAt(0,c).getClass();
	}
	
	public SubProduct selctedSubProduct(int selectedRow){
		if(selectedRow >= 0){
			return newData.get(selectedRow);
		}
		else{
			return null;
		}
		
	}

	public void updateData() {
		newData = Service.getAllNotWastedSubProducts();
	}
	

}
