/**
 * @author Lars Nielsen, Malik Lasse Lund, Martin R¿n Bundgaard
 * @class newProductDialog
 * @programmer: Lars Nielsen
 */
package carletti.gui.dialogs;

import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewProductDialog extends JDialog{
	
	private JPanel panel;
	private JLabel labID, labName, labState, labProduct;
	private JTextField txfID, txfName, txfState, txfProd;
	
	public NewProductDialog(){
			getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
			
			panel = new JPanel();
			getContentPane().add(panel);
			panel.setLayout(new GridLayout(4, 2, 20, 20));
			
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
		}

}
