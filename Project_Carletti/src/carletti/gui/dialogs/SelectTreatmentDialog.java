package carletti.gui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import carletti.model.SubTreatment;
import carletti.model.Treatment;
import carletti.service.Service;

/**
 * Handles the selection of an existing treatment plan.
 * This treatment plan is then associated with a
 * produt in creation.
 * @author Malik Lund
 *
 */
public class SelectTreatmentDialog extends JDialog {
	
	private Service service;
	private NewProductSubTreatmentsTableModel tableModel;
	private Controller controller;
	private List<Treatment> treatments;
	
	private JPanel buttonsPanel;
	private JLabel lblTreatments, lblSubTreatments;
	private JList treatmentsList;
	private JScrollPane treatmentsListScrollPane, subTreatmentsTableScrollPane;
	private JTable subTreatmentsTable;
	private JButton btnSelect, btnCancel;
	
	public SelectTreatmentDialog(NewProductSubTreatmentsTableModel tableModel){
		this.setModal(true);
		this.setTitle("Select treatment");
		
		service = Service.getInstance();
		this.tableModel = tableModel;
		controller = new Controller();
		
		lblTreatments = new JLabel("Treatments:");
		treatments = service.getTreatments();
		String[] treatmentsArray = new String[treatments.size()];
		for (int i = 0; i < treatmentsArray.length; i++){
			treatmentsArray[i] = treatments.get(i).getName();
		}
		treatmentsList = new JList(treatmentsArray);
		treatmentsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		treatmentsList.addListSelectionListener(controller);
		treatmentsListScrollPane = new JScrollPane(treatmentsList);

		lblSubTreatments = new JLabel("Subtreatments");
		subTreatmentsTable = new JTable(tableModel);
		subTreatmentsTableScrollPane = new JScrollPane(subTreatmentsTable);

		buttonsPanel = new JPanel();
		btnSelect = new JButton("Select");
		btnSelect.addActionListener(controller);
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(controller);
		buttonsPanel.add(btnSelect);
		buttonsPanel.add(btnCancel);
		
		
		// ----------------
		// Main layout
		
		GroupLayout layout = new GroupLayout(this.getContentPane());
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(lblTreatments)
					.addComponent(treatmentsListScrollPane)
					.addComponent(buttonsPanel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(lblSubTreatments)
					.addComponent(subTreatmentsTableScrollPane))
		);
			
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lblTreatments)
					.addComponent(lblSubTreatments))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(treatmentsListScrollPane)
					.addComponent(subTreatmentsTableScrollPane))
				.addComponent(buttonsPanel)
		);
		
		// ---------------------
		// buttons
		GroupLayout buttonsLayout = new GroupLayout(buttonsPanel);
		buttonsPanel.setLayout(buttonsLayout);
		buttonsLayout.setAutoCreateGaps(true);
		buttonsLayout.setAutoCreateContainerGaps(true);
		
		buttonsLayout.setHorizontalGroup(
			buttonsLayout.createSequentialGroup()
				.addComponent(btnSelect)
				.addComponent(btnCancel)
		);
		
		buttonsLayout.setVerticalGroup(
			buttonsLayout.createSequentialGroup()
				.addGroup(buttonsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(btnSelect)
					.addComponent(btnCancel))
		);
		
		this.pack();

	}
	
	private class Controller implements ActionListener, ListSelectionListener{

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == btnSelect){
				SelectTreatmentDialog.this.setVisible(false);
			}
			else if (ae.getSource() == btnCancel){
				tableModel.setData(new Treatment("Temp"));
				SelectTreatmentDialog.this.setVisible(false);
			}
		}

		@Override
		public void valueChanged(ListSelectionEvent lse) {
			if (lse.getSource() == treatmentsList){
				int index = treatmentsList.getSelectedIndex();
				if (index > 0){
					Treatment t = treatments.get(index);
					if (t.getName().equals(treatmentsList.getSelectedValue())){
						tableModel.setData(t);
					}
				}
			}
		}
		
	}
}
