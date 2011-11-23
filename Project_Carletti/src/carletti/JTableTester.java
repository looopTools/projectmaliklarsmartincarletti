package carletti;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class JTableTester extends JFrame {
	private JTable table;
	
	public JTableTester() {
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		String[] coloumnNames = {"ID", "Subproduct", "Product Type"};
		Object[][] data = {
				{new Integer(1), "Chocolate", "Chocolate Product"},
				{new Integer(2), "FooBar", "FooBar Product"},
				{new Integer(3), "BarBaz", "BarBaz Product"},
				{new Integer(4), "BazFoo", "BazFoo Product"}
		};
		
		table = new JTable(data, coloumnNames);
		table.setAutoCreateRowSorter(true);
		TableColumn column = table.getColumn("Subproduct");
		scrollPane.setViewportView(table);
		
		pack();
	}
	
	private class MyTableModel extends AbstractTableModel {

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JTableTester();
		frame.setVisible(true);
	}

}
