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
	private TableModel tableModel;
	
	public JTableTester() {
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tableModel = new MyTableModel();
		table = new JTable(tableModel);
		table.setAutoCreateRowSorter(true);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		pack();
	}
	
	private class MyTableModel extends AbstractTableModel {
		private String[] coloumnNames = {"ID", "Subproduct", "Product Type"};
		private Object[][] data = {
				{new Integer(1), "Chocolate", "Chocolate Product"},
				{new Integer(2), "FooBar", "FooBar Product"},
				{new Integer(3), "BarBaz", "BarBaz Product"},
				{new Integer(4), "BazFoo", "BazFoo Product"}
		};

		@Override
		public int getColumnCount() {
			return coloumnNames.length;
		}

		@Override
		public int getRowCount() {
			return data.length;
		}
		
		@Override
		public String getColumnName(int col){
			return coloumnNames[col];
		}

		@Override
		public Object getValueAt(int row, int col) {
			return data[row][col];
		}
		
		public Class getColumnClass(int c){
			return getValueAt(0,c).getClass();
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
