package carletti.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import carletti.gui.dialogs.CreateNewProductDialog;
import carletti.gui.dialogs.NewSubProductDialog;
import carletti.gui.dialogs.NextSubTreatmentDialog;
import carletti.gui.dialogs.ShowDoneDialog;
import carletti.gui.dialogs.ShowWastedDialog;
import carletti.gui.dialogs.ShowWastedDialogTableModel;
import carletti.gui.dialogs.SubProductDialog;
import carletti.gui.dialogs.WasteSubProduct;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import javax.swing.table.TableColumn;


public class MainFrameTabbed extends JFrame {
	private Service service;

	private JPanel buttonsPanel;
	private JScrollPane subProductsScrollPane, subProductsTreatmentScrollPane;
	private JButton btnNewSubProduct;
	private JButton btnInfo;
	private JButton btnWaste;
	private JTabbedPane tabbedPane;
	private JTable subProductTable, subProductsTreatmentTable;
	private NewSubProductTableModelTabbed subProductTableModel, subProductsTreatmentTableModel;

	private Controller btnCtrl;

	private Dimension minimumSize = new Dimension(1000, 400);
	private Dimension btnMinSize = new Dimension(20, 180);
	private JButton btnNewProduct;
	private JButton btnProductInfo;
	private JButton btnTreatment;
	private JButton btnShowSubTreatment;
	private JButton btnShowDone;
	private JButton btnShowWasted;

	public MainFrameTabbed() {
		service = Service.getInstance();

		btnCtrl = new Controller();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		this.setTitle("Carletti");
		this.setSize(minimumSize);
		this.setLocation(new Point(100, 50));
		buttonsPanel = new JPanel();
		getContentPane().add(buttonsPanel, BorderLayout.EAST);

		btnInfo = new JButton("Info");
		btnInfo.setMinimumSize(btnMinSize);
		btnInfo.addActionListener(btnCtrl);

		btnWaste = new JButton("Waste");
		btnWaste.setMinimumSize(btnMinSize);
		btnWaste.addActionListener(btnCtrl);

		btnProductInfo = new JButton("Product Info");
		btnProductInfo.addActionListener(btnCtrl);

		btnNewSubProduct = new JButton("New subproduct");
		btnNewSubProduct.setMinimumSize(btnMinSize);
		btnNewSubProduct.addActionListener(btnCtrl);

		btnNewProduct = new JButton("New Product");
		
		btnTreatment = new JButton("Treatment");
		btnTreatment.addActionListener(btnCtrl);
		
		btnShowSubTreatment = new JButton("Show Subtreatment");
		btnShowSubTreatment.addActionListener(btnCtrl);
		
		btnShowDone = new JButton("Show Done");
		btnShowDone.addActionListener(btnCtrl);
		
		btnShowWasted = new JButton("Show Wasted");
		btnShowWasted.addActionListener(btnCtrl);
		
		GroupLayout gl_buttonsPanel = new GroupLayout(buttonsPanel);
		gl_buttonsPanel.setHorizontalGroup(
			gl_buttonsPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(btnInfo, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
				.addComponent(btnWaste, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
				.addComponent(btnProductInfo, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
				.addComponent(btnNewProduct, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
				.addComponent(btnTreatment, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
				.addComponent(btnShowSubTreatment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(btnNewSubProduct, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
				.addComponent(btnShowDone, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
				.addComponent(btnShowWasted, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
		);
		gl_buttonsPanel.setVerticalGroup(
			gl_buttonsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonsPanel.createSequentialGroup()
					.addComponent(btnInfo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnWaste, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnProductInfo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewSubProduct, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewProduct)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnTreatment)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnShowSubTreatment)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnShowDone)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnShowWasted)
					.addGap(107))
		);
		buttonsPanel.setLayout(gl_buttonsPanel);
		btnNewProduct.addActionListener(btnCtrl);
		
		tabbedPane = new JTabbedPane();
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		// --- Drying table ---
		subProductTableModel = new NewSubProductTableModelTabbed(State.DRYING);
		subProductTable = new JTable(subProductTableModel);
		subProductTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		subProductTable.setDefaultRenderer(subProductTableModel.getColumnClass(0), new HighlighterCellRenderer());

		subProductsScrollPane = new JScrollPane(subProductTable);
		subProductsScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		getContentPane().add(subProductsScrollPane, BorderLayout.CENTER);
		tabbedPane.addTab("Drying", subProductsScrollPane);

		subProductTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		initColumnSizes(subProductTable);
		
		// --- In treatment table ---
		subProductsTreatmentTableModel = new NewSubProductTableModelTabbed(State.TREATMENT);
		subProductsTreatmentTable = new JTable(subProductsTreatmentTableModel);
		subProductsTreatmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		subProductsTreatmentTable.setDefaultRenderer(subProductTableModel.getColumnClass(0), new HighlighterCellRenderer());
		
		subProductsTreatmentScrollPane = new JScrollPane(subProductsTreatmentTable);
		subProductsTreatmentScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		tabbedPane.addTab("In treatment", subProductsTreatmentScrollPane);
		
		// --- Malik Lund start---
		Thread t = new Thread(new UpdaterThreadForTabbedMain(this));
		t.start();
		// -------------- end ----

		this.setVisible(true);

	}
	
	/**
	 * Hardcode some default column sizes.
	 * @author Malik Lund
	 * @param table
	 */
	private void initColumnSizes(JTable table) {
        NewSubProductTableModelTabbed model = (NewSubProductTableModelTabbed)table.getModel();
        TableColumn column = null;
        for (int i = 0; i < model.getColumnCount(); i++){
        	column = table.getColumnModel().getColumn(i);
        	if (i == 1){
        		column.setPreferredWidth(125);
        	}
        	else if (i == 2){
        		column.setPreferredWidth(40);
        	}
        	else if (i == 3){
        		column.setPreferredWidth(40);
        	}
        }
    }

	/*
	 * @author Malik Lund
	 */
	public synchronized void updateList() {
		int selection = subProductTable.getSelectedRow();
		subProductTableModel.updateData();
		subProductTableModel.fireTableDataChanged();
		subProductTable.changeSelection(selection, 0, false, false);
		
		updateTable(subProductsTreatmentTable);
	}
	
	private synchronized void updateTable(JTable table){
		int selection = table.getSelectedRow();
		NewSubProductTableModelTabbed tableModel = (NewSubProductTableModelTabbed) table.getModel();
		tableModel.updateData();
		tableModel.fireTableDataChanged();
		if (selection >= 0 && selection < tableModel.getColumnCount()){
			table.changeSelection(selection, 0, false, false);
		}
	}

	private class Controller implements ActionListener {
		public void actionPerformed(ActionEvent ae) {

			if (ae.getSource().equals(btnInfo)) {
				SubProduct sp = subProductTableModel
						.selctedSubProduct(subProductTable.getSelectedRow());
				if (sp == null) {
					JOptionPane.showMessageDialog(null,
							"You need to selected an object");
				} else {
					SubProductDialog spd = new SubProductDialog(sp);
					spd.setLocationRelativeTo(MainFrameTabbed.this);
					spd.setVisible(true);
				}
			} else if (ae.getSource().equals(btnNewSubProduct)) {
				NewSubProductDialog nspd = new NewSubProductDialog();
				nspd.setLocationRelativeTo(MainFrameTabbed.this);
				nspd.setVisible(true);
				updateList();
			} else if (ae.getSource().equals(btnNewProduct)) {
				CreateNewProductDialog newProductDialog = new CreateNewProductDialog();
				newProductDialog.setLocationRelativeTo(MainFrameTabbed.this);
				newProductDialog.setVisible(true);
			} else if (ae.getSource().equals(btnWaste)) {
				SubProduct sp = subProductTableModel
						.selctedSubProduct(subProductTable.getSelectedRow());
				if (sp == null) {
					JOptionPane.showMessageDialog(null,
							"You need to selected an object");
				}

				else {
					WasteSubProduct wsp = new WasteSubProduct(sp);
					wsp.setLocationRelativeTo(MainFrameTabbed.this);
					wsp.setVisible(true);
					updateList();
				}

			}
			
			else if (ae.getSource().equals(btnTreatment)){
				SubProduct sp = subProductTableModel
						.selctedSubProduct(subProductTable.getSelectedRow());
				if (sp == null) 
				{
					JOptionPane.showMessageDialog(null,
							"You need to selected an object");
				}
				else
				{
					service.changeState(sp, State.TREATMENT);
				}
				updateList();
			}

			else if (ae.getSource().equals(btnProductInfo)) {
				ShowProductFrame spf = new ShowProductFrame();
				spf.setLocationRelativeTo(MainFrameTabbed.this);
				spf.setVisible(true);
			}
			
			else if (ae.getSource().equals(btnShowSubTreatment))
			{
				NextSubTreatmentDialog ntd = new NextSubTreatmentDialog();
				ntd.setLocationRelativeTo(MainFrameTabbed.this);
				ntd.setVisible(true);
			}
			else if (ae.getSource().equals(btnShowDone))
			{
				ShowDoneDialog sdd = new ShowDoneDialog();
				sdd.setLocationRelativeTo(MainFrameTabbed.this);
				sdd.setVisible(true);
			}
			else if (ae.getSource().equals(btnShowWasted))
			{
				ShowWastedDialog swd = new ShowWastedDialog();
				swd.setLocationRelativeTo(MainFrameTabbed.this);
				swd.setVisible(true);
				
			}
		}
	}
	
	/**
	 * Custom cell renderer that colors the "time remaining" cell
	 * white, green, yellow or red depending on the time remaining
	 * until max drying time is exceeded.
	 * 
	 * @author Malik Lund
	 */
	private class HighlighterCellRenderer extends DefaultTableCellRenderer{
		private DefaultTableCellRenderer defaultRenderer;
		
		public HighlighterCellRenderer(){
			super();
			defaultRenderer = new DefaultTableCellRenderer();
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
			Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			SubProduct subProducts = subProductTableModel.getSubProduct(row);
			
			long currentTime = System.currentTimeMillis();
			long minTime = subProducts.getTimeAdded() + subProducts.getCurrentSubTreatment().getDryMin();
			long optimalTime = subProducts.getTimeAdded() + subProducts.getCurrentSubTreatment().getDryPrime();
			long maxTime = subProducts.getTimeAdded() + subProducts.getCurrentSubTreatment().getDryMax();
			
			// Set color
			if (minTime < currentTime && optimalTime > currentTime && column == 0){
				comp.setBackground(Color.green);
			} 
			else if (optimalTime < currentTime && maxTime > currentTime && column == 0){
				comp.setBackground(Color.yellow);
			}
			else if (currentTime > maxTime && column == 0) {
				comp.setBackground(Color.red);
			} else {
				// For reasons I don't understand and don't have time to look into
				// it is necessary to have two different renderers. The color change is
				// apparently persistent.
				return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
			
			return comp;
		}
	}
}