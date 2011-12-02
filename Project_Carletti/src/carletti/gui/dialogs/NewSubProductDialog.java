package carletti.gui.dialogs;

/**
 *@author Lars Nielsen, Malik Lasse Lund, Martin Rønn Bundgaard
 *@class NewSubProductDialog
 *@programmer Lars Nielsen
 */

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import carletti.model.Position;
import carletti.model.Product;
import carletti.service.Service;

/*
 * This class is harcoded dou to certain requriments, which windows build
 * couldn't do.
 */
public class NewSubProductDialog extends JDialog {
	private Service service;
	
	private JTextField txfName, txfPos;
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
	private TextController txtCtrl;

	public NewSubProductDialog() {
		service = Service.getInstance();
		
		btnCtrl = new Controller();
		txtCtrl = new TextController();

		this.setSize(defaultSize);
		this.setResizable(false);
		this.setTitle("New Subproduct");
		this.setLayout(null);
		this.setModal(true);

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
		
		y += 40;
		
		txfPos = new JTextField();
		txfPos.setSize(txfSize);
		txfPos.setLocation(x, y);
		txfPos.setText("Enter position");
		txfPos.addMouseListener(txtCtrl);
		this.add(txfPos);

		x = 20;
		

		lblProd = new JLabel();
		lblProd.setText("Products");
		lblProd.setSize(lblSize);
		lblProd.setLocation(x, y);
		this.add(lblProd);

		y += 40;

		prodList = new JList();
		prodList.setListData(service.getProducts().toArray());
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
				
				String posID = txfPos.getText();
				List<Position> positions = service.getPositions();
				
				boolean found = false;
				int i = 0;
				Position pos = null;
				
				while(!found && i < positions.size()){
					if(i >=  positions.size()){
						JOptionPane.showMessageDialog(null, "Enter a valid position - see manual");
					}
					else{
						if(posID.equals(positions.get(i).getPosID())){
							found = true;
							pos = positions.get(i);
						}
						else{
							i++;
						}
					}
				}
				service.createSubProduct(name, p, pos);
				NewSubProductDialog.this.setVisible(false);

			} else if (ae.getSource().equals(btnCan)) {
				setDialogVisibility(false);
			}
		}
	}
	
	private class TextController implements MouseListener{


		@Override
		public void mouseClicked(MouseEvent ae) {
			// TODO Auto-generated method stub
			if(ae.getSource().equals(txfPos)){
				if(txfPos.hasFocus() == true){
					txfPos.setText("");
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent ae) {
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}