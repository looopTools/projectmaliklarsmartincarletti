package carletti.gui.dialogs;

import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import carletti.model.Position;
import carletti.model.Product;
import carletti.service.Service;

/**
 *@group Lars Nielsen, Malik Lasse Lund, Martin Rønn Bundgaard
 *@class NewSubProductDialog
 *@programmer Lars Nielsen
 */
/*
 * This class is harcoded due to certain requriments, which couldn't 
 * be achive with JWindowsBuild
 */
public class NewSubProductDialog extends JDialog {
	
	private Service service; // Holds an singelton instance of the Service
	
	//GUI componenst
	private JTextField txfName;
	private JComboBox comboPos;
	private JLabel lblName, lblProd;
	private JScrollPane jspProducts;
	private JList prodList;
	private JButton btnSub, btnCan;
	
	//Start coordinates for the different GUI objects
	private int x = 20, y = 20;
	
	//Dimensions for the GUI components
	private Dimension defaultSize = new Dimension(360, 360);
	private Dimension btnSize = new Dimension(140, 20);
	private Dimension lblSize = new Dimension(140, 20);
	private Dimension txfSize = new Dimension(160, 20);
	private Dimension jspSize = new Dimension(320, 160);

	private Controller btnCtrl; //Holds an instance of the Controller 

	public NewSubProductDialog() {
		
		service = Service.getInstance(); //Service is instantiated 
		
		btnCtrl = new Controller(); //Controller is instantiated 

		//Generel preference  for the JDialog
		this.setSize(defaultSize);
		this.setResizable(false);
		this.setTitle("New Subproduct");
		this.setLayout(null);
		this.setModal(true);

		/*
		 * Instansation of all GUI componetens and the preferences 
		 */
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
		
		comboPos = new JComboBox(getPositionsAvailable().toArray());
		comboPos.setSize(txfSize);
		comboPos.setLocation(x, y);
		this.add(comboPos);

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

	/**
	 * Allows to set the visibility in the Controller class
	 * @param visibility
	 */
	private void setDialogVisibility(boolean visibility) {
		this.setVisible(visibility);
	}
	
	/**
	 * Used to get all positions which is not occurpied by a SubProudct
	 * @return
	 */
	private List<Position> getPositionsAvailable(){
		
		ArrayList<Position> avaPos = new ArrayList<Position>();
		List<Position> allPos = service.getPositions();
		
		for(int i = 0; i < allPos.size(); i++){
			if(allPos.get(i).getSubProduct() == null){
				avaPos.add(allPos.get(i));
			}
		}
		return avaPos;
	}

	/**
	 * The inner class Controller, is orgnaised so we can controll which button
	 * events is actived
	 */
	private class Controller implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(btnSub)) {
				String name = txfName.getText();
				Product p = (Product) prodList.getSelectedValue();
				Position pos = (Position) comboPos.getSelectedItem();
				
				service.createSubProduct(name, p, pos);
				NewSubProductDialog.this.setVisible(false);

			} else if (ae.getSource().equals(btnCan)) {
				setDialogVisibility(false);
			}
		}
	}
	
	
}