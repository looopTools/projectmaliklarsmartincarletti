package carletti.gui.dialogs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import carletti.model.SubProduct;
import carletti.service.Service;

public class NextSubTreatmentDialogTableModel extends AbstractTableModel
{
	// Column headers.
		private String[] coloumnNames = {"ID", "Name", "Subtreatment", "Number of Subtreatments"};
		// The actual data.
		private ArrayList<Object[]> data = new ArrayList<Object[]>();
		private List<SubProduct> newData;
		
		public NextSubTreatmentDialogTableModel(){
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
					
					new Integer(sp.getId()), sp.getName(), 
					 sp.getCurrentSubTreatment().getName(), sp.getSubtreatments().size()	
			};
			return value[col];
		}
		
		@Override
		public Class getColumnClass(int c){
			return getValueAt(0,c).getClass();
		}
		
		public SubProduct selctedSubProduct(int selectedRow){
			return newData.get(selectedRow);
		}

		public void updateData() {
			newData = Service.getAllNotWastedSubProducts();
		}
}
