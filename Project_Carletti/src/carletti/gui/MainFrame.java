package carletti.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import carletti.gui.dialogs.NewSubProductDialog;
import carletti.gui.dialogs.SubProductDialog;
import carletti.model.SubProduct;
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
		
		btnInfo = new JButton("Info");
		btnInfo.setMinimumSize(btnMinSize);
		btnInfo.addActionListener(btnCtrl);
		panel.add(btnInfo);
		
		btnKasser = new JButton("Waste");
		btnKasser.setMinimumSize(btnMinSize);
		panel.add(btnKasser);
		
		btnNew = new JButton("New");
		btnNew.setMinimumSize(btnMinSize);
		btnNew.addActionListener(btnCtrl);
		panel.add(btnNew);
		
		list = new JList();
		list.setListData(Service.showAllSubProduct().toArray());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		System.out.println(Service.showAllSubProduct());
		
		scrollPane = new JScrollPane(list);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
		
		
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
			else if(ae.getSource().equals(btnNew)){
				NewSubProductDialog nspd = new NewSubProductDialog();
				nspd.setVisible(true);
			}
			
			
		}
	}
}