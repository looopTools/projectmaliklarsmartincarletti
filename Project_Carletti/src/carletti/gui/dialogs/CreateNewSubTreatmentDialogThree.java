package carletti.gui.dialogs;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

public class CreateNewSubTreatmentDialogThree extends JDialog {

	private Controller controller;
	private JPanel mainPanel, infoPanel;
	private JLabel lblNew, lblName, lblMin, lblOptimal, lblMax;
	private JTextField txfName, txfMin, txfOptimal, txfMax;
	private JButton btnCreate, btnCancel;

	private boolean succes;
	private NewProductSubTreatmentsTableModel subTreatmentsTableModel;
	
	public CreateNewSubTreatmentDialogThree(NewProductSubTreatmentsTableModel subTreatmentsTableModel){
		this.getContentPane().setLayout(new FlowLayout());
		this.setModal(true);
		this.setResizable(false);
		this.subTreatmentsTableModel = subTreatmentsTableModel;
		
		controller = new Controller();
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.add(mainPanel);
		
		infoPanel = new JPanel();
		GroupLayout layout = new GroupLayout(infoPanel);
		infoPanel.setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		lblNew = new JLabel("New subtreatment");
		lblName = new JLabel("Name:");
		lblMin = new JLabel("Minimum:");
		lblOptimal = new JLabel("Optimal:");
		lblMax = new JLabel("Maximum:");
		txfName = new JTextField();
		txfMin = new JTextField();
		txfOptimal = new JTextField();
		txfMax = new JTextField();
		btnCreate = new JButton("Create");
		btnCancel = new JButton("Cancel");
		
		btnCreate.addActionListener(controller);
		btnCancel.addActionListener(controller);
		
		mainPanel.add(lblNew, BorderLayout.NORTH);
		
		/**
		 * Define grouping horizontally.
		 */
		layout.setHorizontalGroup(
		    layout.createSequentialGroup()
		    	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		    		.addComponent(lblName)
		    		.addComponent(lblMin)
		    		.addComponent(lblOptimal)
		    		.addComponent(lblMax)
		    		.addComponent(btnCreate))
		    	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		    		.addComponent(txfName, 50, 75, 100)
		    		.addComponent(txfMin, 50, 75, 100)
		    		.addComponent(txfOptimal, 50, 75, 100)
		    		.addComponent(txfMax, 50, 75, 100)
		    		.addComponent(btnCancel))
		);
		
		/**
		 * Define grouping vertically.
		 */
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lblName)
					.addComponent(txfName))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lblMin)
					.addComponent(txfMin))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lblOptimal)
					.addComponent(txfOptimal))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lblMax)
					.addComponent(txfMax))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(btnCreate)
					.addComponent(btnCancel))
		);

		mainPanel.add(infoPanel);
		
		pack();
	}
	
	private class Controller implements ActionListener{


		@Override
		public void actionPerformed(ActionEvent ae) {
		
			if (ae.getSource() == btnCreate){
				String tempName = txfName.getText();
				long tempMin = 0, tempOptimal = 0, tempMax = 0;
				try {
					tempMin = Long.parseLong(txfMin.getText());
					tempOptimal = Long.parseLong(txfOptimal.getText());
					tempMax = Long.parseLong(txfMax.getText());
					succes = true;
				}
				catch(NumberFormatException nfe){
					succes = false;
				}
				if (succes){
					subTreatmentsTableModel.newSubTreatment(tempName, tempMin, tempOptimal, tempMax);
				}
				CreateNewSubTreatmentDialogThree.this.setVisible(false);
			}
			else if (ae.getSource() == btnCancel){
				CreateNewSubTreatmentDialogThree.this.setVisible(false);
			}
		}
	}
}
