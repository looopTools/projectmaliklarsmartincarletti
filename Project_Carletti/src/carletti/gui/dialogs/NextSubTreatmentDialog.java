/**
 * @author Martin
 */
package carletti.gui.dialogs;

import carletti.gui.*;
import java.awt.Dimension;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;

import carletti.gui.UpdaterThread;
import carletti.model.Position;
import carletti.model.State;
import carletti.model.SubProduct;
import carletti.service.Service;
import carletti.dao.JpaDao;

public class NextSubTreatmentDialog extends JFrame
{
	private Service service;
	
	private Dimension minsize = new Dimension(600, 400);
	private JTable nextSubProcuctTable;
	private JButton btnNextSubTreatment;
	private JButton btnAnnuller;
	private NextSubTreatmentDialogTableModel tableModel;
	private JScrollPane nextSubProcuctScrollPane;
	private Controller btnCtrl;
	private NextSubTreatmentDialogTableModel nextSubTreatmentDialogTableModel;

	public NextSubTreatmentDialog()
	{
		service = Service.getInstance();
		btnCtrl = new Controller();
		this.setMinimumSize(minsize);

		tableModel = new NextSubTreatmentDialogTableModel();
		nextSubProcuctTable = new JTable(tableModel);

		nextSubProcuctScrollPane = new JScrollPane(nextSubProcuctTable);
		nextSubProcuctScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(nextSubProcuctScrollPane, BorderLayout.CENTER);

		btnNextSubTreatment = new JButton("Next Subtreatment");
		btnNextSubTreatment.addActionListener(btnCtrl);

		btnAnnuller = new JButton("Cancel");
		btnAnnuller.addActionListener(btnCtrl);

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		.addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout
		.createParallelGroup(Alignment.TRAILING).addComponent(nextSubProcuctScrollPane,
		GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE).addGroup(groupLayout
		.createSequentialGroup()
		.addComponent(btnNextSubTreatment)
		.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		.addComponent(btnAnnuller)))
		.addGap(0)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
		.addGroup(groupLayout.createSequentialGroup()
		.addComponent(nextSubProcuctScrollPane, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
		.addPreferredGap(ComponentPlacement.RELATED)
		.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
		.addComponent(btnNextSubTreatment)
		.addComponent(btnAnnuller))
		.addGap(3)));
		
		getContentPane().setLayout(groupLayout);

		this.setVisible(true);
	}

	public void update()
	{

	}

	private class Controller implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{

			if (ae.getSource().equals(btnAnnuller))
			{
				NextSubTreatmentDialog.this.setVisible(false);
			}

			else if (ae.getSource().equals(btnNextSubTreatment))
			{
				int i = nextSubProcuctTable.getSelectedRow();
				if (i < 0)
				{
					JOptionPane.showMessageDialog(null,
							"You need to selected an object");
				} 
				else {
					// --- Malik Lund start---
					SubProduct sp = service.getSubProducts(State.TREATMENT).get(i);
					
					if (sp.getCurrentSubTreatmentIndex() < sp.getSubtreatments().size() - 1){
						Position p = new Position("null");
						PositionSelectionDialog selectionDialog = new PositionSelectionDialog(p);
						selectionDialog.setVisible(true);
						
						if (!p.getPosID().equals("null")){
							List<Position> positions = service.getPositions();
							boolean found = false;
							int index = 0;
							Position wantedPosition = null;
							while (!found && index < positions.size()){
								wantedPosition = positions.get(i);
								if (wantedPosition.getPosID().equals(p.getPosID())){
									found = true;
								} else {
									i++;
								}
							}
							
							if (found){
								service.nextTreatnemt(sp);
								sp.setPosition(wantedPosition);
								NextSubTreatmentDialog.this.setVisible(false);
							}
						}
						
					} else {
						service.nextTreatnemt(sp);
						NextSubTreatmentDialog.this.setVisible(false);
					}
					// --- Malik Lund end ---
				}
			}
		}
	}
	
	/**
	 * 
	 * @author Malik Lund
	 */
	private class PositionSelectionDialog extends JDialog implements ActionListener{
		private Position position;
		
		private JPanel mainPanel, buttonsPanel;
		private JComboBox comboBox;
		private JButton btnOk, btnCancel;
		
		public PositionSelectionDialog(Position position){
			this.position = position;
			this.setLocationRelativeTo(NextSubTreatmentDialog.this);
			this.setModal(true);
			
			mainPanel = new JPanel();
			
			GroupLayout layout = new GroupLayout(this.getContentPane());
			this.getContentPane().setLayout(layout);
			
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			
			btnOk = new JButton("Ok");
			btnCancel = new JButton("Cancel");
			comboBox = new JComboBox(getPositionsAvailable().toArray());
			
			buttonsPanel = new JPanel();
			buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
			buttonsPanel.add(btnOk);
			buttonsPanel.add(btnCancel);
			
			btnOk.addActionListener(this);
			btnCancel.addActionListener(this);
			
			layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.CENTER)
					.addComponent(comboBox)
					.addComponent(buttonsPanel)
			);
			
			layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addComponent(comboBox)
					.addComponent(buttonsPanel)
			);
			
			this.pack();
		}
		
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

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == btnOk){
				position.setPosID(((Position)comboBox.getSelectedItem()).getPosID());
				this.setVisible(false);
			}
			else if (ae.getSource() == btnCancel){
				this.setVisible(false);
			}
		}
	}
}