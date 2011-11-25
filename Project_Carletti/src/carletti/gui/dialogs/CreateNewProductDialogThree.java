package carletti.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import carletti.model.Treatment;
import carletti.service.Service;

/**
 * 
 * @author Malik Lund
 *
 */
public class CreateNewProductDialogThree extends JDialog {
	
	private Controller controller;
	
	private JPanel mainPanel, productAndSubproductPanel, productInfoPanel,
	               productButtonsPanel, subTreatmentPanel, subTreatmentButtonsPanel;
	private JLabel lblNewProduct, lblName, lblDescription, lblSubTreatment;
	private JTextField txfName;
	private JTextArea txtAreaDescription;
	private JButton btnCreate, btnCancel, btnAddSubTreatment;
	private JTable subTreatmentsTable;
	private NewProductSubTreatmentsTableModel subTreatmentsTableModel;
	private JScrollPane productDescriptionScrollPane, subTreatmentsScrollPane;

	public CreateNewProductDialogThree(){
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
		
		lblSubTreatment = new JLabel("Subtreatments");
		subTreatmentsTableModel = new NewProductSubTreatmentsTableModel();
		subTreatmentsTable = new JTable(subTreatmentsTableModel);
		subTreatmentsScrollPane = new JScrollPane(subTreatmentsTable);
		
		subTreatmentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		subTreatmentsGroupLayout.setHorizontalGroup(
			subTreatmentsGroupLayout.createSequentialGroup()
				.addGroup(subTreatmentsGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(lblSubTreatment)	
					.addComponent(subTreatmentsScrollPane, 400, 400, 400))
		);
		
		subTreatmentsGroupLayout.setVerticalGroup(
			subTreatmentsGroupLayout.createSequentialGroup()
				.addComponent(lblSubTreatment)
				.addComponent(subTreatmentsScrollPane, 100, 100, Short.MAX_VALUE)
		);
		
		//-----------
		// Subtreatment Add button
		
		GroupLayout subTreatmentButtonsGroupLayout = new GroupLayout(subTreatmentButtonsPanel);
		subTreatmentButtonsPanel.setLayout(subTreatmentButtonsGroupLayout);
		
		subTreatmentButtonsGroupLayout.setAutoCreateGaps(true);
		subTreatmentButtonsGroupLayout.setAutoCreateContainerGaps(true);
		
		btnAddSubTreatment = new JButton("Add subtreatment");
		
		btnAddSubTreatment.addActionListener(controller);
		
		subTreatmentButtonsGroupLayout.setHorizontalGroup(
			subTreatmentButtonsGroupLayout.createSequentialGroup().addComponent(btnAddSubTreatment)
		);
		
		subTreatmentButtonsGroupLayout.setVerticalGroup(
				subTreatmentButtonsGroupLayout.createSequentialGroup().addComponent(btnAddSubTreatment)
		);
		
		this.pack();
	}
	
	/**
	 * 
	 * @author Malik Lund
	 *
	 */
	private class Controller implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == btnAddSubTreatment){
				CreateNewSubTreatmentDialogFour createSubTreatmentDialog = new CreateNewSubTreatmentDialogFour(subTreatmentsTableModel);
				createSubTreatmentDialog.setVisible(true);
			}
			if (ae.getSource() == btnCreate){
				Treatment treatment = Service.createTreatment(txfName.getText());
				List<Object[]> data = subTreatmentsTableModel.getData();
				for (int i = 0; i < data.size(); i++){
					String name = (String)(data.get(i)[0]);
					long min = (Long)(data.get(i)[1]);
					long optimal = (Long)(data.get(i)[2]);
					long max = (Long)(data.get(i)[3]);
					treatment.createSubTreatment(name, min, optimal, max);
				}
				Service.createProduct(txfName.getText(), txtAreaDescription.getText(), treatment);
				CreateNewProductDialogThree.this.setVisible(false);
			}
			if (ae.getSource() == btnCancel){
				CreateNewProductDialogThree.this.setVisible(false);
			}
		}
	}
}
