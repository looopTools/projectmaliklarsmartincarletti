package carletti.gui.dialogs;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import carletti.model.State;
import carletti.model.SubProduct;

/**
 * @group Lars Nielsen, Malik Lasse Lund, Martin R¿n Bundgaard
 * @class SubProductDialog
 * @author Lars Nielsen
 */
public class SubProductDialog extends JDialog{

	private SubProduct sub; //holds the current sub product which is wanted for inspection
	
	/*
	 *GUI components 
	 */
	private JPanel panel;
	private JLabel labID, labName, labState, labProduct;
	private JTextField txfID, txfName, txfState, txfProd;
	private JButton btnOK;
	private Controller btnCtrl;
	
	public SubProductDialog(SubProduct sub){
		
		this.sub = sub;
		btnCtrl = new Controller();
		
		//Preferences for the dialog 
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		//GUI components and their preferences
		panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(6, 2, 20, 20));
		
		labID = new JLabel();
		labID.setText("ID:");
		panel.add(labID);
		
		txfID = new JTextField();
		txfID.setEditable(false); //User should not be able to change the info from heir
		panel.add(txfID);
		
		labName = new JLabel();
		labName.setText("Name:");
		panel.add(labName);
		
		txfName = new JTextField();
		txfName.setEditable(false);
		panel.add(txfName);
		
		labState = new JLabel();
		labState.setText("State:");
		panel.add(labState);
		
		txfState = new JTextField();
		txfState.setEditable(false);
		panel.add(txfState);
		
		labProduct = new JLabel();
		labProduct.setText("Product");
		panel.add(labProduct);
		
		txfProd = new JTextField();
		txfProd.setEditable(false);
		panel.add(txfProd);
		
		btnOK = new JButton();
		btnOK.setText("OK");
		btnOK.addActionListener(btnCtrl);
		panel.add(btnOK);
		
		setInfo();
		this.pack();
	}
	
	//Sets the info for the JTextFields 
	private void setInfo(){
		txfID.setText(Integer.toString(sub.getId()));
		txfName.setText(sub.getName());
		checkAndSetState();
		txfProd.setText(sub.getProduct().getName());
		
		
	}
	
	//Sets the info for the txfState
	private void checkAndSetState(){
		if(sub.getState() == State.DONE){
			txfState.setText("Done");
		}
		else if(sub.getState() == State.TREATMENT){
			txfState.setText("Treatment");
		}
		else if(sub.getState() == State.DRYING){
			txfState.setText("Drying");
		}
		else if(sub.getState() == State.WASTE){
			txfState.setText("Waste");
		}
	}
	
	//Allows to change the visibility of the dialog in the Controller
	private void thisSetVisible(boolean visiblity){
		this.setVisible(visiblity);
	}
	
	/*
	 * Keeps track of the actions on the buttons
	 */
	private class Controller implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae) {
			// TODO Auto-generated method stub
			if(ae.getSource().equals(btnOK)){
				thisSetVisible(false);
			}
		}
		
	}
}
