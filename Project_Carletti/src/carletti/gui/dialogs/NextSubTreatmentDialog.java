/**
 * @author Lars Nielsen, Malik Lasse Lund, Martin R¿nn Bungaard
 * @class NewSubProductTableModel
 * @programmer Lars Nielsen
 */
package carletti.gui.dialogs;

import java.awt.Dimension;

import javax.swing.JFrame;
import java.awt.BorderLayout;

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
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private NextSubTreatmentDialogTableModel tableModel;
	private JScrollPane nextSubProcuctScrollPane;
	
	public NextSubTreatmentDialog() 
	{
		
		this.setMinimumSize(minsize);
		
		tableModel = new NextSubTreatmentDialogTableModel();
		nextSubProcuctTable = new JTable(tableModel);
		
		nextSubProcuctScrollPane = new JScrollPane(nextSubProcuctTable);
		nextSubProcuctScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(nextSubProcuctScrollPane, BorderLayout.CENTER);
		
		
		btnNewButton = new JButton("Next Subtreatment");
		
		btnNewButton_1 = new JButton("Annuller");
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(nextSubProcuctScrollPane, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnNewButton)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnNewButton_1)))
					.addGap(0))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(nextSubProcuctScrollPane, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnNewButton_1))
					.addGap(3))
		);
		getContentPane().setLayout(groupLayout);
		
		
		this.setVisible(true);
	}
}