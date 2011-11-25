package carletti.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import carletti.gui.dialogs.CreateNewProductDialogThree;
import carletti.gui.dialogs.NewSubProductDialog;
import carletti.gui.dialogs.SubProductDialog;
import carletti.gui.dialogs.WasteSubProduct;
import carletti.model.SubProduct;
import carletti.service.Service;

public class NewMainFrame extends JFrame{
	
	private JPanel buttonsPanel;
	private JScrollPane subProductsScrollPane;
	private JList subProductList;
	private JButton btnNewSubProduct;
	private JButton btnInfo;
	private JButton btnWaste;
	private JTable subProductTable;
	private NewSubProductTableModel subProductTableModel;
	
	private Controller btnCtrl;
	
	private Dimension minimumSize = new Dimension(400, 400);
	private Dimension btnMinSize = new Dimension(20, 180);
	private JButton btnNewProduct;
	private JButton btnProductInfo;

	public NewMainFrame() {
		
		btnCtrl = new Controller();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		this.setTitle("Carletti");
		this.setMinimumSize(minimumSize);
		buttonsPanel = new JPanel();
		getContentPane().add(buttonsPanel, BorderLayout.EAST);
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
		
		btnInfo = new JButton("Info");
		btnInfo.setMinimumSize(btnMinSize);
		btnInfo.addActionListener(btnCtrl);
		buttonsPanel.add(btnInfo);
		
		btnWaste = new JButton("Waste");
		btnWaste.setMinimumSize(btnMinSize);
		btnWaste.addActionListener(btnCtrl);
		
		
		btnProductInfo = new JButton("Product Info");
		btnProductInfo.addActionListener(btnCtrl);
		buttonsPanel.add(btnWaste);
		buttonsPanel.add(btnProductInfo);
		
		
		
		btnNewSubProduct = new JButton("New subproduct");
		btnNewSubProduct.setMinimumSize(btnMinSize);
		btnNewSubProduct.addActionListener(btnCtrl);
		buttonsPanel.add(btnNewSubProduct);
		
		btnNewProduct = new JButton("New Product");
		buttonsPanel.add(btnNewProduct);
		btnNewProduct.addActionListener(btnCtrl);
		
//		subProductList = new JList();
//		subProductList.setListData(Service.getAllNotWastedSubProducts().toArray());
//		subProductList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		subProductTableModel = new NewSubProductTableModel();
		subProductTable = new JTable(subProductTableModel);
		subProductTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		subProductsScrollPane = new JScrollPane(subProductTable);
		subProductsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(subProductsScrollPane, BorderLayout.CENTER);
		
		subProductTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//--- Malik Lund ---
		Thread t = new Thread(new UpdaterThread(this));
		t.start();
		//------------------
		
		this.setVisible(true);
		
	}

	/*
	 * @author Malik 
	 */
	public void updateList() {
		int selection = subProductTable.getSelectedRow();
		subProductTableModel.fireTableDataChanged();
		subProductTable.changeSelection(selection, 0, false, false);
	}

	private class Controller implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			
			if(ae.getSource().equals(btnInfo)){
			    SubProduct sp = (SubProduct) subProductList.getSelectedValue();
				if(sp == null){
					JOptionPane.showMessageDialog(null, "You need to selected an object");
				}
				else{
				    SubProductDialog spd = new SubProductDialog(sp);
				    spd.setVisible(true);
				}
			}
			else if(ae.getSource().equals(btnNewSubProduct)){
				NewSubProductDialog nspd = new NewSubProductDialog();
				nspd.setVisible(true);
			    updateList();
			}
			else if (ae.getSource().equals(btnNewProduct)){
				CreateNewProductDialogThree newProductDialog = new CreateNewProductDialogThree();
				newProductDialog.setVisible(true);
			}
			else if(ae.getSource().equals(btnWaste)){
				SubProduct sp = (SubProduct) subProductList.getSelectedValue();
				if(sp == null){
					JOptionPane.showMessageDialog(null, "You need to selected an object");
				}
					
				else{
				    WasteSubProduct wsp = new WasteSubProduct(sp);
				    wsp.setVisible(true);
				    updateList();
				}
			
			}
			
			else if(ae.getSource().equals(btnProductInfo)){
				ShowProductFrame spf = new ShowProductFrame();
				spf.setVisible(true);
					}
		}
	}
}