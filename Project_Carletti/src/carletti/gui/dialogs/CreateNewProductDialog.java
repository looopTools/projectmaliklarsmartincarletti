package carletti.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import carletti.model.SubTreatment;
import carletti.model.Treatment;
import carletti.service.Service;

/**
 * The CreateNewProductDialog class handles the creation
 * of a new product and it's associated treatment plan.
 * @author Malik Lund
 *
 */
public class CreateNewProductDialog extends JDialog {
	private Service service;
	private Controller controller;
	
	private boolean newTreatment;
	
	private JPanel mainPanel, productAndSubproductPanel, productInfoPanel,
	               productButtonsPanel, treatmentNamePanel, subTreatmentPanel, subTreatmentButtonsPanel;
	private JLabel lblNewProduct, lblName, lblDescription, lblTreatmentName, lblSubTreatment;
	private JTextField txfName, txfTreatmentName;
	private JTextArea txtAreaDescription;
	private JButton btnCreate, btnCancel, btnAddSubTreatment, btnSelectTreatment;
	private JTable subTreatmentsTable;
	private NewProductSubTreatmentsTableModel subTreatmentsTableModel;
	private JScrollPane productDescriptionScrollPane, subTreatmentsScrollPane;

	/**
	 * 
	 */
	public CreateNewProductDialog(){
		service = service.getInstance();
		this.setTitle("Create new product");
		this.setModal(true);

		controller = new Controller();
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.add(mainPanel);
		
		productAndSubproductPanel = new JPanel();
		GroupLayout outerGroupLayout = new GroupLayout(productAndSubproductPanel);
		productAndSubproductPanel.setLayout(outerGroupLayout);
		
		outerGroupLayout.setAutoCreateGaps(true);
		outerGroupLayout.setAutoCreateContainerGaps(true);
		
		productInfoPanel = new JPanel();
		productButtonsPanel = new JPanel();
		subTreatmentPanel = new JPanel();
		subTreatmentButtonsPanel = new JPanel();
		lblNewProduct = new JLabel("New Product");
		
		mainPanel.add(lblNewProduct, BorderLayout.NORTH);
		
		//------------
		// General grouping of product information and subtreatment information
		
		outerGroupLayout.setHorizontalGroup(
			outerGroupLayout.createSequentialGroup()
				.addGroup(outerGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(productInfoPanel)
					.addComponent(productButtonsPanel))
				.addGroup(outerGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(subTreatmentPanel)
					.addComponent(subTreatmentButtonsPanel))
		);
		
		outerGroupLayout.setVerticalGroup(
			outerGroupLayout.createSequentialGroup()
				.addGroup(outerGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(productInfoPanel)
					.addComponent(subTreatmentPanel))
				.addGroup(outerGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(productButtonsPanel)
					.addComponent(subTreatmentButtonsPanel))
		);
		
		mainPanel.add(productAndSubproductPanel);
		
		//------------
		// Product name and description
		
		GroupLayout productGroupLayout = new GroupLayout(productInfoPanel);
		productInfoPanel.setLayout(productGroupLayout);
		
		productGroupLayout.setAutoCreateGaps(true);
		productGroupLayout.setAutoCreateContainerGaps(true);
		
		lblName = new JLabel("Name:");
		lblDescription = new JLabel("Description:");
		txfName = new JTextField();
		txtAreaDescription = new JTextArea();
		txtAreaDescription.setLineWrap(true);
		productDescriptionScrollPane = new JScrollPane(txtAreaDescription);
		
		productGroupLayout.setHorizontalGroup(
			productGroupLayout.createSequentialGroup()
				.addGroup(productGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(lblName)
					.addComponent(lblDescription))
				.addGroup(productGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(txfName, 150, 175, 200)
					.addComponent(productDescriptionScrollPane, 250, 250, Short.MAX_VALUE))
		);
		
		productGroupLayout.setVerticalGroup(
			productGroupLayout.createSequentialGroup()
				.addGroup(productGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lblName)
					.addComponent(txfName))
				.addGroup(productGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lblDescription)
					.addComponent(productDescriptionScrollPane, 100, 100, Short.MAX_VALUE))
		);
		
		//--------------
		// Product create and cancel buttons
		
		GroupLayout productButtonsGroupLayout = new GroupLayout(productButtonsPanel);
		productButtonsPanel.setLayout(productButtonsGroupLayout);
		
		productButtonsGroupLayout.setAutoCreateGaps(true);
		productButtonsGroupLayout.setAutoCreateContainerGaps(true);
		
		btnCreate = new JButton("Create");
		btnCancel = new JButton("Cancel");
		
		btnCreate.addActionListener(controller);
		btnCancel.addActionListener(controller);
		
		productButtonsGroupLayout.setHorizontalGroup(
			productButtonsGroupLayout.createSequentialGroup()
				.addComponent(btnCreate)
				.addComponent(btnCancel)
		);
		
		productButtonsGroupLayout.setVerticalGroup(
			productButtonsGroupLayout.createSequentialGroup()
				.addGroup(productButtonsGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(btnCreate)
					.addComponent(btnCancel))
		);
		
		//-------------
		// Subtreatments table
		
		GroupLayout subTreatmentsGroupLayout = new GroupLayout(subTreatmentPanel);
		subTreatmentPanel.setLayout(subTreatmentsGroupLayout);
		
		subTreatmentsGroupLayout.setAutoCreateGaps(true);
		subTreatmentsGroupLayout.setAutoCreateContainerGaps(true);
		
		lblSubTreatment = new JLabel("Treatments");
		treatmentNamePanel = new JPanel();
		lblTreatmentName = new JLabel("Name:    ");
		txfTreatmentName = new JTextField();
		subTreatmentsTableModel = new NewProductSubTreatmentsTableModel();
		subTreatmentsTable = new JTable(subTreatmentsTableModel);
		subTreatmentsScrollPane = new JScrollPane(subTreatmentsTable);
		
		treatmentNamePanel.setLayout(new BoxLayout(treatmentNamePanel, BoxLayout.X_AXIS));
		treatmentNamePanel.add(lblTreatmentName);
		treatmentNamePanel.add(txfTreatmentName);
		txfTreatmentName.setEnabled(false);
		subTreatmentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		subTreatmentsGroupLayout.setHorizontalGroup(
			subTreatmentsGroupLayout.createSequentialGroup()
				.addGroup(subTreatmentsGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(lblSubTreatment)
					.addComponent(treatmentNamePanel)
					.addComponent(subTreatmentsScrollPane, 400, 400, 400))
		);
		
		subTreatmentsGroupLayout.setVerticalGroup(
			subTreatmentsGroupLayout.createSequentialGroup()
				.addComponent(lblSubTreatment)
				.addComponent(treatmentNamePanel)
				.addComponent(subTreatmentsScrollPane, 100, 100, Short.MAX_VALUE)
		);
		
		//-----------
		// Treatment buttons.
		
		GroupLayout subTreatmentButtonsGroupLayout = new GroupLayout(subTreatmentButtonsPanel);
		subTreatmentButtonsPanel.setLayout(subTreatmentButtonsGroupLayout);
		
		subTreatmentButtonsGroupLayout.setAutoCreateGaps(true);
		subTreatmentButtonsGroupLayout.setAutoCreateContainerGaps(true);
		
		btnAddSubTreatment = new JButton("New Treatment");
		btnSelectTreatment = new JButton("Select existing treatment");
		
		btnAddSubTreatment.addActionListener(controller);
		btnSelectTreatment.addActionListener(controller);
		
		subTreatmentButtonsGroupLayout.setHorizontalGroup(
			subTreatmentButtonsGroupLayout
				.createSequentialGroup()
					.addComponent(btnAddSubTreatment)
					.addComponent(btnSelectTreatment)
		);
		
		subTreatmentButtonsGroupLayout.setVerticalGroup(
			subTreatmentButtonsGroupLayout
				.createParallelGroup()
					.addComponent(btnAddSubTreatment)
					.addComponent(btnSelectTreatment)
		);
		
		this.pack();
	}
	
	/**
	 * Handles all input for this class.
	 * @author Malik Lund
	 *
	 */
	private class Controller implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae) {
			
			/**
			 * Create product button.
			 */
			if (ae.getSource() == btnCreate){
				boolean success = true;
				
				String subProductName = txfName.getText();
				if (subProductName.length() <= 0){
					JOptionPane.showMessageDialog(null, "Subproduct name is empty!");
					success = false;
				}
				
				String treatmentName = txfTreatmentName.getText();
				if (success && treatmentName.length() <= 0){
					JOptionPane.showMessageDialog(null, "Treatment name is empty!");
					success = false;
				}
				
				List<SubTreatment> data = subTreatmentsTableModel.getData().getSubTreatments();
				if (success && data.size() <= 0){
					JOptionPane.showMessageDialog(null, "Treatment has no subtreatments!");
					success = false;
				}
				
				if (success){
					Treatment treatment = service.createTreatment(txfTreatmentName.getText());
					for (int i = 0; i < data.size(); i++){
						String name = data.get(i).getName();
						long min = data.get(i).getDryMin();
						long optimal = data.get(i).getDryPrime();
						long max = data.get(i).getDryMax();
						treatment.createSubTreatment(name, min, optimal, max);
					}
					service.createProduct(txfName.getText(), txtAreaDescription.getText(), treatment);
					CreateNewProductDialog.this.setVisible(false);
				}
			}
			/**
			 * Cancel creation of product button.
			 */
			else if (ae.getSource() == btnCancel){
				CreateNewProductDialog.this.setVisible(false);
			}
			
			/**
			 * New/Add subtreatment button.
			 */
			else if (ae.getSource() == btnAddSubTreatment){
				if (newTreatment){
					CreateNewSubTreatmentDialog createSubTreatmentDialog = new CreateNewSubTreatmentDialog(subTreatmentsTableModel);
					createSubTreatmentDialog.setVisible(true);
				} else {
					newTreatment = true;
					txfTreatmentName.setEnabled(true);
					txfTreatmentName.setText("");
					btnAddSubTreatment.setText("Add subtreatment");
					subTreatmentsTableModel.setData(new Treatment("Temp"));
				}
			}
			/**
			 * Select treatment button. 
			 */
			else if (ae.getSource() == btnSelectTreatment){
				// save new Treatment in case you regret 
				// selecting an existing one
				Treatment temporaryTreatment = null;
				if (newTreatment){
					temporaryTreatment = subTreatmentsTableModel.getData();
					temporaryTreatment.setName(txfTreatmentName.getText());
				}
				
				// prepare table and tablemodel
				subTreatmentsTableModel.setData(new Treatment("Temp"));
				subTreatmentsTableModel.removeTableModelListener(subTreatmentsTable); // "disconnect" table 
				txfTreatmentName.setText("");
				
				// start selection
				SelectTreatmentDialog selectTreatmentDialog = new SelectTreatmentDialog(subTreatmentsTableModel);
				selectTreatmentDialog.setVisible(true);
				
				// handle selection
				Treatment treatment = subTreatmentsTableModel.getData();
				if (!treatment.getName().equals("Temp")){
					// selected an existing treatment
					txfTreatmentName.setText(treatment.getName());
					txfTreatmentName.setEnabled(false);
					btnAddSubTreatment.setText("New subtreatment");
					newTreatment = false;
				} else {
					// didn't select an existing treatment
					if (newTreatment){
						// revert to previous data.
						subTreatmentsTableModel.setData(temporaryTreatment);
						txfTreatmentName.setText(temporaryTreatment.getName());
					} else {
						// no previsous data.
						subTreatmentsTableModel.setData(new Treatment("Temp"));
					}
				}
				
				subTreatmentsTableModel.addTableModelListener(subTreatmentsTable); // "reconnect" table
				subTreatmentsTableModel.fireTableDataChanged();

			}
		}
	}
}
