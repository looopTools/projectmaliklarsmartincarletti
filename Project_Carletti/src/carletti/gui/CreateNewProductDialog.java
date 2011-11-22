package carletti.gui;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import carletti.model.SubTreatment;
import carletti.model.Treatment;
import carletti.service.Service;

public class CreateNewProductDialog extends JDialog {
	private JButton btnCreateSubTreatment;
	private JTextField txfNewProductName;
	private JTextArea textAreaNewProductDescription;
	private Controller controller;
	
	private ArrayList<String> subTreatmentsNames;
	private ArrayList<Long> subTreatmentsMin;
	private ArrayList<Long> subTreatmentsOptimal;
	private ArrayList<Long> subTreatmentsMax;
	private JList subTreatmentList;
	private JButton btnNewProductCreate;
	private JButton btnNewProductCancel;
	
	public CreateNewProductDialog() {
		setTitle("Create new product");
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		controller = new Controller();
		subTreatmentsNames = new ArrayList<String>();
		subTreatmentsMin = new ArrayList<Long>();
		subTreatmentsOptimal = new ArrayList<Long>();
		subTreatmentsMax = new ArrayList<Long>();
		
		JPanel treatmentPanel = new JPanel();
		treatmentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		getContentPane().add(treatmentPanel);
		treatmentPanel.setLayout(new BoxLayout(treatmentPanel, BoxLayout.Y_AXIS));
		
		JLabel lblNewProduct = new JLabel("New product");
		treatmentPanel.add(lblNewProduct);
		
		JPanel newProductNamePanel = new JPanel();
		treatmentPanel.add(newProductNamePanel);
		newProductNamePanel.setLayout(new BoxLayout(newProductNamePanel, BoxLayout.X_AXIS));
		
		JLabel lblNewProductName = new JLabel("Name:");
		newProductNamePanel.add(lblNewProductName);
		
		txfNewProductName = new JTextField();
		newProductNamePanel.add(txfNewProductName);
		txfNewProductName.setColumns(10);
		
		JPanel newProductDescriptionPanel = new JPanel();
		treatmentPanel.add(newProductDescriptionPanel);
		newProductDescriptionPanel.setLayout(new BoxLayout(newProductDescriptionPanel, BoxLayout.X_AXIS));
		
		JLabel lblNewProductDescription = new JLabel("Description:");
		newProductDescriptionPanel.add(lblNewProductDescription);
		
		textAreaNewProductDescription = new JTextArea();
		textAreaNewProductDescription.setRows(10);
		textAreaNewProductDescription.setColumns(25);
		textAreaNewProductDescription.setLineWrap(true);
		
		JScrollPane scrollPane = new JScrollPane(textAreaNewProductDescription);
		newProductDescriptionPanel.add(scrollPane);
		
		JPanel newProductButtonsPanel = new JPanel();
		treatmentPanel.add(newProductButtonsPanel);
		
		btnNewProductCreate = new JButton("Create");
		newProductButtonsPanel.add(btnNewProductCreate);
		btnNewProductCreate.addActionListener(controller);
		
		btnNewProductCancel = new JButton("Cancel");
		newProductButtonsPanel.add(btnNewProductCancel);
		btnNewProductCancel.addActionListener(controller);
		
		JPanel subTreatmentPanel = new JPanel();
		getContentPane().add(subTreatmentPanel);
		subTreatmentPanel.setLayout(new BoxLayout(subTreatmentPanel, BoxLayout.Y_AXIS));
		
		JScrollPane subTreatmentListScrollPane = new JScrollPane();
		subTreatmentPanel.add(subTreatmentListScrollPane);
		
		subTreatmentList = new JList();
		subTreatmentListScrollPane.setViewportView(subTreatmentList);
		
		JPanel subTreatmentButtonsPanel = new JPanel();
		subTreatmentPanel.add(subTreatmentButtonsPanel);
		
		btnCreateSubTreatment = new JButton("Add subtreatment");
		subTreatmentButtonsPanel.add(btnCreateSubTreatment);
		btnCreateSubTreatment.addActionListener(controller);
		
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
		subTreatmentList.setListData(listData);
	}
	
	private class Controller implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == btnCreateSubTreatment){
				CreateNewSubTreatmentDialog createSubTreatmentDialog = new CreateNewSubTreatmentDialog();
				createSubTreatmentDialog.setVisible(true);
				if (createSubTreatmentDialog.succes()){
					subTreatmentsNames.add(createSubTreatmentDialog.getName());
					subTreatmentsMin.add(createSubTreatmentDialog.getMin());
					subTreatmentsOptimal.add(createSubTreatmentDialog.getOptimal());
					subTreatmentsMax.add(createSubTreatmentDialog.getMax());
					updateList();
				}
			}
			if (ae.getSource() == btnNewProductCreate){
				Treatment treatment = Service.createTreatment(txfNewProductName.getText());
				for (int i = 0; i < subTreatmentsNames.size(); i++){
					treatment.createSubTreatment(subTreatmentsNames.get(i), subTreatmentsMin.get(i), 
							subTreatmentsOptimal.get(i), subTreatmentsMax.get(i));
				}
				Service.createProduct(txfNewProductName.getText(), textAreaNewProductDescription.getText(), treatment);
				CreateNewProductDialog.this.setVisible(false);
			}
			if (ae.getSource() == btnNewProductCancel){
				CreateNewProductDialog.this.setVisible(false);
			}
		}
	}
}
