package carletti.gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;

import carletti.gui.dialogs.SubProductDialog;
import carletti.model.SubProduct;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import carletti.service.Service;

public class MainFrame extends JFrame{
	private JPanel panel;
	private JScrollPane scrollPane;
	private JList list;
	private JButton btnNew;
	private JButton btnInfo;
	private JButton btnKasser;
	
	private Controller btnCtrl;
	
	private Dimension minimumSize = new Dimension(400, 400);
	private Dimension btnMinSize = new Dimension(20, 180);

	public MainFrame() {
		
		btnCtrl = new Controller();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		this.setTitle("Carletti");
		this.setMinimumSize(minimumSize);
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.EAST);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		btnInfo = new JButton("Ifo");
		btnInfo.setMinimumSize(btnMinSize);
		btnInfo.addActionListener(btnCtrl);
		panel.add(btnInfo);
		
		btnKasser = new JButton("Waste");
		btnKasser.setMinimumSize(btnMinSize);
		panel.add(btnKasser);
		
		btnNew = new JButton("New");
		btnNew.setMinimumSize(btnMinSize);
		panel.add(btnNew);
		
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		list = new JList();
		list.setListData(Service.showAllSubProduct().toArray());
		scrollPane.setViewportView(list);
		
		this.setVisible(true);
		
	}

	private class Controller implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			
			if(ae.getSource().equals(btnInfo)){
			    SubProduct sp = (SubProduct) list.getSelectedValue();
				if(sp == null){
					JOptionPane.showMessageDialog(null, "You need to selected an object");
				}
				else{
				    SubProductDialog spd = new SubProductDialog(sp);
				    spd.setVisible(true);  
				}
			}
			
		}
	}
}