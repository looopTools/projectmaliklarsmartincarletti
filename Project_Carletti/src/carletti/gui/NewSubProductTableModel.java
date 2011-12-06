/**
 * @group Lars Nielsen, Malik Lasse Lund, Martin R¿nn Bungaard
 * @class NewSubProductTableModel
 * @author Lars Nielsen
 */
package carletti.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.dao.JpaDao;
import carletti.model.LongToStringParser;
import carletti.model.Product;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

public class NewSubProductTableModel extends AbstractTableModel{
	private Service service;
	// Column headers.
	private String[] coloumnNames = {"Time left", "Started on", "ID", "Position", "Name", "State", "Subtreatment", "Subtreatment #"};
	// The actual data.
	private ArrayList<Object[]> data = new ArrayList<Object[]>();
	private List<SubProduct> newData;
	private State state = State.DRYING;
		
	public NewSubProductTableModel(){
		service = Service.getInstance();
		newData = service.getSubProducts(state);
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
				LongToStringParser.parseLongToString(sp.timeLeft()), sp.getTime(sp.getTimeAdded()), 
				new Integer(sp.getId()), sp.getPosition().getPosID(), sp.getName(), 
				sp.getState(), sp.getCurrentSubTreatment().getName(), 
				(sp.getCurrentSubTreatmentIndex() + 1) + "/" + sp.getSubtreatments().size()	
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
	
	/**
	 * @author Malik Lund
	 * @return
	 */
	public SubProduct getSubProduct(int row){
		return newData.get(row);
	}

	public void updateData() {
		newData = service.getSubProducts(state);
	}
	

}
