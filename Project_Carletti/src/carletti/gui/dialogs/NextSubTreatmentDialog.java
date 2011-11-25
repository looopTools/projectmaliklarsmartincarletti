/**
 * @author Lars Nielsen, Malik Lasse Lund, Martin R¿nn Bungaard
 * @class NewSubProductTableModel
 * @programmer Lars Nielsen
 */
package carletti.gui.dialogs;

import java.awt.Dimension;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;

public class NextSubTreatmentDialog extends JFrame
{
	private Dimension minsize = new Dimension (800, 400);
	private JTable nextSubProcuctTable;
	private JButton btnNextBubTreatment;
	private JButton btnAnnuller;
	private NextSubTreatmentDialogTableModel tableModel;
	private JScrollPane nextSubProcuctScrollPane;
	private Controller btnCtrl;
	
	public NextSubTreatmentDialog() 
	{
		btnCtrl = new Controller();
		this.setMinimumSize(minsize);
		
		tableModel = new NextSubTreatmentDialogTableModel();
		nextSubProcuctTable = new JTable(tableModel);
		
		nextSubProcuctScrollPane = new JScrollPane(nextSubProcuctTable);
		nextSubProcuctScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(nextSubProcuctScrollPane, BorderLayout.CENTER);
		
		
		btnNextBubTreatment = new JButton("Next Subtreatment");
		
		btnAnnuller = new JButton("Annuller");
		btnAnnuller.addActionListener(btnCtrl);
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(nextSubProcuctScrollPane, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnNextBubTreatment)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnAnnuller)))
					.addGap(0))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(nextSubProcuctScrollPane, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNextBubTreatment)
						.addComponent(btnAnnuller))
					.addGap(3))
		);
		getContentPane().setLayout(groupLayout);
		
		
		this.setVisible(true);
	}
	
	private class Controller implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			
			if (ae.getSource().equals(btnAnnuller))
			{
				NextSubTreatmentDialog.this.setVisible(false);
			}
		}
	}
}