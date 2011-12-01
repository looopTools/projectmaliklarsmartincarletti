/**
 * @author Martin
 */

package carletti.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import carletti.service.Service;

public class ShowDoneDialog extends JFrame
{
	
	private Dimension minsize = new Dimension(600, 400);
	private Service service;
	private Controller btnCtrl;
	private JButton btnOk;
	private JScrollPane scrollPane;
	private ShowDoneDialogTableModel tableModel;
	private JTable doneTable;
	
	public ShowDoneDialog()
	{
		
		btnCtrl = new Controller();
		service = Service.getInstance();
		this.setMinimumSize(minsize);

		tableModel = new ShowDoneDialogTableModel();
		doneTable = new JTable(tableModel);

		scrollPane = new JScrollPane(doneTable);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		btnOk = new JButton("OK");
		btnOk.addActionListener(btnCtrl);

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
					.addGap(0))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnOk)
					.addContainerGap(527, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnOk)
					.addContainerGap())
		);
		
		getContentPane().setLayout(groupLayout);

		this.setVisible(true);
		
	}

	private class Controller implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			if (ae.getSource().equals(btnOk))
			{
				ShowDoneDialog.this.setVisible(false);
			}
		}
	}
	
		
}
