package carletti.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import carletti.model.State;
import carletti.model.SubProduct;
import carletti.dao.JpaDao;
import carletti.service.Service;

public class WasteSubProduct extends JDialog{
	private Service service;
	private JPanel infoPanel, btnPanel;
	private JButton btnWaste;
	private JButton btnCan;
	private JLabel lblId;
	private JTextField txfID;
	private JLabel lblName;
	private JLabel lblNewLabel;
	private JLabel lblProduct;
	private JTextField txfName;
	private JTextField txfState;
	private JTextField txfProduct;
	private SubProduct sub;
	private Controller btnCtrl;
	
	public WasteSubProduct(SubProduct sub) {
		service = Service.getInstance();
		this.sub = sub;
		btnCtrl = new Controller();
		
		setTitle("Waste Subproduct");
		setModal(true);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		infoPanel = new JPanel();
		getContentPane().add(infoPanel, BorderLayout.CENTER);
		GridBagLayout gbl_infoPanel = new GridBagLayout();
		gbl_infoPanel.columnWidths = new int[]{0, 0, 0};
		gbl_infoPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_infoPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_infoPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		infoPanel.setLayout(gbl_infoPanel);
		
		lblId = new JLabel("ID:");
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.anchor = GridBagConstraints.EAST;
		gbc_lblId.gridx = 0;
		gbc_lblId.gridy = 0;
		infoPanel.add(lblId, gbc_lblId);
		
		txfID = new JTextField();
		txfID.setEditable(false);
		GridBagConstraints gbc_txfID = new GridBagConstraints();
		gbc_txfID.insets = new Insets(0, 0, 5, 0);
		gbc_txfID.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfID.gridx = 1;
		gbc_txfID.gridy = 0;
		infoPanel.add(txfID, gbc_txfID);
		txfID.setColumns(10);
		
		lblName = new JLabel("NAME:");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 1;
		infoPanel.add(lblName, gbc_lblName);
		
		txfName = new JTextField();
		txfName.setEditable(false);
		GridBagConstraints gbc_txfName = new GridBagConstraints();
		gbc_txfName.insets = new Insets(0, 0, 5, 0);
		gbc_txfName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfName.gridx = 1;
		gbc_txfName.gridy = 1;
		infoPanel.add(txfName, gbc_txfName);
		txfName.setColumns(10);
		
		lblNewLabel = new JLabel("STATE:\n");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 2;
		infoPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		txfState = new JTextField();
		txfState.setEditable(false);
		GridBagConstraints gbc_txfState = new GridBagConstraints();
		gbc_txfState.insets = new Insets(0, 0, 5, 0);
		gbc_txfState.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfState.gridx = 1;
		gbc_txfState.gridy = 2;
		infoPanel.add(txfState, gbc_txfState);
		txfState.setColumns(10);
		
		lblProduct = new JLabel("PRODUCT");
		GridBagConstraints gbc_lblProduct = new GridBagConstraints();
		gbc_lblProduct.anchor = GridBagConstraints.EAST;
		gbc_lblProduct.insets = new Insets(0, 0, 0, 5);
		gbc_lblProduct.gridx = 0;
		gbc_lblProduct.gridy = 3;
		infoPanel.add(lblProduct, gbc_lblProduct);
		
		txfProduct = new JTextField();
		GridBagConstraints gbc_txfProduct = new GridBagConstraints();
		gbc_txfProduct.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfProduct.gridx = 1;
		gbc_txfProduct.gridy = 3;
		infoPanel.add(txfProduct, gbc_txfProduct);
		txfProduct.setColumns(10);
		
		btnPanel = new JPanel();
		getContentPane().add(btnPanel, BorderLayout.SOUTH);
		btnPanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		btnWaste = new JButton("Waste");
		btnWaste.addActionListener(btnCtrl);
		btnPanel.add(btnWaste);
		
		btnCan = new JButton("Cancel");
		btnCan.addActionListener(btnCtrl);
		btnPanel.add(btnCan);
		
		setInfo();
		checkAndSetState();
		
		this.pack();
	}
	
	private void desposeDialog(boolean despose){
		this.setVisible(despose);
	}
	
	private void setInfo(){
		txfID.setText(Integer.toString(sub.getId()));
		txfName.setText(sub.getName());
		checkAndSetState();
		txfProduct.setText(sub.getProduct().getName());
		
		
	}
	
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
	
	private class Controller implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae) {
			if(ae.getSource().equals(btnWaste)){
				service.changeState(sub, State.WASTE);
				sub.setPosition(null);
				desposeDialog(false);
			}
			else if(ae.getSource().equals(btnCan)){
				desposeDialog(false);
			}
			
		}
		
	}

}
