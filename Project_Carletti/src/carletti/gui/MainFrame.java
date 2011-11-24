package carletti.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import carletti.gui.dialogs.CreateNewProductDialogThree;
import carletti.gui.dialogs.NewSubProductDialog;
import carletti.gui.dialogs.SubProductDialog;
import carletti.gui.dialogs.WasteSubProduct;
import carletti.model.SubProduct;
import carletti.service.Service;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class MainFrame extends JFrame{
	private JPanel buttonsPanel;
	private JScrollPane subProductsScrollPane;
	private JList subProductList;
	private JButton btnNewSubProduct;
	private JButton btnInfo;
	private JButton btnWaste;
	
	private Controller btnCtrl;
	
	private Dimension minimumSize = new Dimension(600, 400);
	private Dimension btnMinSize = new Dimension(20, 180);
	private JButton btnNewProduct;
	private JButton btnProductInfo;
	private int subProductListlIndex;

	public MainFrame() {
		
		btnCtrl = new Controller();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		this.setTitle("Carletti");
		this.setMinimumSize(minimumSize);
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
		GroupLayout gl_buttonsPanel = new GroupLayout(buttonsPanel);
		gl_buttonsPanel.setHorizontalGroup(
			gl_buttonsPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(btnNewSubProduct, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addComponent(btnInfo, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
				.addComponent(btnWaste, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
				.addComponent(btnProductInfo, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
				.addComponent(btnNewProduct, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
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
					.addGap(229))
		);
		buttonsPanel.setLayout(gl_buttonsPanel);
		btnNewProduct.addActionListener(btnCtrl);
		
		subProductList = new JList();
		subProductList.setListData(Service.getAllNotWastedSubProducts().toArray());
		subProductList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		subProductsScrollPane = new JScrollPane(subProductList);
		subProductsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(subProductsScrollPane, BorderLayout.CENTER);
		
		//--- Malik Lund ---
		Thread t = new Thread(new UpdaterThread(this));
		t.start();
		//------------------
		
		this.setVisible(true);
		
	}

	public void updateList() {
		subProductListlIndex = subProductList.getSelectedIndex(); //Martin
		subProductList.setListData(Service.getAllNotWastedSubProducts().toArray());
		subProductList.setSelectedIndex(subProductListlIndex); //Martin
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