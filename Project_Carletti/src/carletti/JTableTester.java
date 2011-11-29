package carletti;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class JTableTester extends JFrame {
	private JTable table;
	private TableModel tableModel;
	
	public static void main(String[] args){
		JFrame frame = new JTableTester();
		frame.setVisible(true);
	}
	
	public JTableTester() {
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tableModel = new MyTableModel();
		table = new JTable(tableModel);
		table.setAutoCreateRowSorter(true);
		table.setFillsViewportHeight(true);
		table.setDefaultRenderer(table.getColumnClass(1), new MyCellRenderer());
		scrollPane.setViewportView(table);
		
		
		pack();
	}
	
	private class MyTableModel extends AbstractTableModel {
		private String[] coloumnNames = {"Name", "Minimum (ms)", "Optimal (ms)", "Maximum (ms)"};
		private Object[][] data = {
				{"Subtreatment 1", new Integer(10), new Integer(20), new Integer(30)},
				{"Subtreatment 2", new Integer(20), new Integer(30), new Integer(40)},
				{"Subtreatment 3", new Integer(30), new Integer(40), new Integer(50)}
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
	
	private class MyCellRenderer extends DefaultTableCellRenderer{
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
			Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if (row == 1 && column == 1){
				comp.setBackground(Color.green);
			}
			return comp;
		}
	}
}
