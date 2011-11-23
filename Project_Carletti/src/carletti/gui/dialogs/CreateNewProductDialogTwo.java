package carletti.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import carletti.model.Treatment;
import carletti.service.Service;

/**
 * 
 * @author Malik Lund
 *
 */
public class CreateNewProductDialogTwo extends JDialog {
	
	public static void main(String[] args){
		JDialog dialog = new CreateNewProductDialogTwo();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
	
	private Controller controller;
	
	private JPanel mainPanel, productAndSubproductPanel, productInfoPanel,
	               productButtonsPanel, subTreatmentPanel, subTreatmentButtonsPanel;
	private JLabel lblNewProduct, lblName, lblDescription, lblSubTreatment;
	private JTextField txfName;
	private JTextArea txtAreaDescription;
	private JButton btnCreate, btnCancel, btnAddSubTreatment;
	private JList subTreatmentsList;
	private JScrollPane productDescriptionScrollPane, subTreatmentsScrollPane;

	private ArrayList<String> subTreatmentsNames;
	private ArrayList<Long> subTreatmentsMin;
	private ArrayList<Long> subTreatmentsOptimal;
	private ArrayList<Long> subTreatmentsMax;

	public CreateNewProductDialogTwo(){
		this.setTitle("Create new product");
//		this.getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		this.setModal(true);

		controller = new Controller();
		subTreatmentsNames = new ArrayList<String>();
		subTreatmentsMin = new ArrayList<Long>();
		subTreatmentsOptimal = new ArrayList<Long>();
		subTreatmentsMax = new ArrayList<Long>();
		
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
					.addComponent(productDescriptionScrollPane, 150, 175, Short.MAX_VALUE))
		);
		
		productGroupLayout.setVerticalGroup(
			productGroupLayout.createSequentialGroup()
				.addGroup(productGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lblName)
					.addComponent(txfName))
				.addGroup(productGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lblDescription)
					.addComponent(productDescriptionScrollPane, 100, 125, Short.MAX_VALUE))
		);
		
		//--------------
		
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
		
		GroupLayout subTreatmentsGroupLayout = new GroupLayout(subTreatmentPanel);
		subTreatmentPanel.setLayout(subTreatmentsGroupLayout);
		
		subTreatmentsGroupLayout.setAutoCreateGaps(true);
		subTreatmentsGroupLayout.setAutoCreateContainerGaps(true);
		
		lblSubTreatment = new JLabel("Subtreatments");
		subTreatmentsList = new JList();
		subTreatmentsScrollPane = new JScrollPane(subTreatmentsList);
		
		subTreatmentsGroupLayout.setHorizontalGroup(
			subTreatmentsGroupLayout.createSequentialGroup()
				.addGroup(subTreatmentsGroupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(lblSubTreatment)	
					.addComponent(subTreatmentsScrollPane, 200, 300, 400))
		);
		
		subTreatmentsGroupLayout.setVerticalGroup(
			subTreatmentsGroupLayout.createSequentialGroup()
				.addComponent(lblSubTreatment)
				.addComponent(subTreatmentsScrollPane)
		);
		
		//-----------
		
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

	private void updateList() {
		String[] listData = new String[subTreatmentsNames.size()];
		for (int i = 0; i < listData.length; i++){
			String buffer = subTreatmentsNames.get(i) + " " +
		                    subTreatmentsMin.get(i) + " " +
					        subTreatmentsOptimal.get(i) + " " +
		                    subTreatmentsMax.get(i);
			listData[i] = buffer;
		}
		subTreatmentsList.setListData(listData);
	}
	
	private class Controller implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == btnAddSubTreatment){
				CreateNewSubTreatmentDialogTwo createSubTreatmentDialog = new CreateNewSubTreatmentDialogTwo();
				createSubTreatmentDialog.setVisible(true);
				if (createSubTreatmentDialog.succes()){
					subTreatmentsNames.add(createSubTreatmentDialog.getName());
					subTreatmentsMin.add(createSubTreatmentDialog.getMin());
					subTreatmentsOptimal.add(createSubTreatmentDialog.getOptimal());
					subTreatmentsMax.add(createSubTreatmentDialog.getMax());
					updateList();
				}
			}
			if (ae.getSource() == btnCreate){
				Treatment treatment = Service.createTreatment(txfName.getText());
				for (int i = 0; i < subTreatmentsNames.size(); i++){
					treatment.createSubTreatment(subTreatmentsNames.get(i), subTreatmentsMin.get(i), 
							subTreatmentsOptimal.get(i), subTreatmentsMax.get(i));
				}
				Service.createProduct(txfName.getText(), txtAreaDescription.getText(), treatment);
				CreateNewProductDialogTwo.this.setVisible(false);
			}
			if (ae.getSource() == btnCancel){
				CreateNewProductDialogTwo.this.setVisible(false);
			}
		}
	}
}
