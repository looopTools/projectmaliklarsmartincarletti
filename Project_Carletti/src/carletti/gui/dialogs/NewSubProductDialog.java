package carletti.gui.dialogs;

/**
 *@author Lars Nielsen, Malik Lasse Lund, Martin Rønn Bundgaard
 *@class NewSubProductDialog
 *@programmer Lars Nielsen
 */

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import carletti.model.Product;
import carletti.service.Service;

/*
 * This class is harcoded dou to certain requriments, which windows build
 * couldn't do.
 */
public class NewSubProductDialog extends JDialog {

	private JTextField txfName;
	private JLabel lblName, lblProd;
	private JScrollPane jspProducts;
	private JList prodList;
	private JButton btnSub, btnCan;
	private int x = 20, y = 20;
	private Dimension defaultSize = new Dimension(360, 360);
	private Dimension btnSize = new Dimension(140, 20);
	private Dimension lblSize = new Dimension(140, 20);
	private Dimension txfSize = new Dimension(160, 20);
	private Dimension jspSize = new Dimension(320, 160);

	private Controller btnCtrl;

	public NewSubProductDialog() {
		btnCtrl = new Controller();

		this.setSize(defaultSize);
		this.setResizable(false);
		this.setTitle("New Subproduct");
		this.setLayout(null);

		lblName = new JLabel();
		lblName.setText("Name");
		lblName.setSize(lblSize);
		lblName.setLocation(x, y);
		this.add(lblName);

		x += 160;

		txfName = new JTextField();
		txfName.setSize(txfSize);
		txfName.setLocation(x, y);
		this.add(txfName);

		x = 20;
		y += 40;

		lblProd = new JLabel();
		lblProd.setText("Products");
		lblProd.setSize(lblSize);
		lblProd.setLocation(x, y);
		this.add(lblProd);

		y += 40;

		prodList = new JList();
		prodList.setListData(Service.getProducts().toArray());
		prodList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		jspProducts = new JScrollPane(prodList);
		jspProducts.setSize(jspSize);
		jspProducts.setLocation(x, y);
		this.add(jspProducts);

		y += 180;

		btnSub = new JButton("Submit");
		btnSub.addActionListener(btnCtrl);
		btnSub.setSize(btnSize);
		btnSub.setLocation(x, y);
		this.add(btnSub);

		x += 180;

		btnCan = new JButton("Cancel");
		btnCan.addActionListener(btnCtrl);
		btnCan.setSize(btnSize);
		btnCan.setLocation(x, y);
		this.add(btnCan);

	}

	private void setDialogVisibility(boolean visibility) {
		this.setVisible(visibility);
	}

	/**
	 * The inner class Controller, is orgnaised so we can controll which button
	 * events is actived
	 */
	private class Controller implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(btnSub)) {
				// int id, String name, Product product
				String name = txfName.getText();
				Product p = (Product) prodList.getSelectedValue();
				Service.createSubProduct(name, p);

			} else if (ae.getSource().equals(btnCan)) {
				setDialogVisibility(false);
			}
		}
	}
}