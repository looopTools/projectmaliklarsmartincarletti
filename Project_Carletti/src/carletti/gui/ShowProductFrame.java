package carletti.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;

import carletti.gui.dialogs.NewSubProductDialog;
import carletti.gui.dialogs.SubProductDialog;
import carletti.model.SubProduct;
import carletti.service.Service;

public class ShowProductFrame extends JDialog
{

	private JPanel contentPane;
	private JPanel panel;
	private JButton btnOk;
	private JSplitPane splitPane;
	private JTextPane textPane;
	private JScrollPane scrollPane;
	private JScrollPane screooPane2;
	private JList list;
	private Dimension minimumSize = new Dimension(400, 500);
	
	private Controller btnCtrl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ShowProductFrame frame = new ShowProductFrame();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ShowProductFrame()
	{
		btnCtrl = new Controller();
		this.setMinimumSize(minimumSize);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		btnOk = new JButton("OK");
		panel.add(btnOk);
		btnOk.addActionListener(btnCtrl);
		
		splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		splitPane.setRightComponent(textPane);
		
		list = new JList();
		contentPane.add(list, BorderLayout.NORTH);
		list.setListData(Service.getProducts().toArray());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		list.addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		       	        
		        if (e.getValueIsAdjusting())
		        	updateTextPane();
		    }
			
		});
		
		scrollPane = new JScrollPane(list);
		splitPane.setLeftComponent(scrollPane);
	
		screooPane2 = new JScrollPane(textPane);
		splitPane.setRightComponent(screooPane2);
		
	}
	
	public void updateTextPane()
	{
		int index = list.getSelectedIndex();
		if (index >= 0)
			textPane.setText(Service.getProducts().get(list.getSelectedIndex()).getName() + "\n" +
					Service.getProducts().get(list.getSelectedIndex()).getDescription() + "\n" + "Treatment:" + "\n" +
					Service.getProducts().get(list.getSelectedIndex()).getTreatment());
	}
	
	private class Controller implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			
			if(ae.getSource().equals(btnOk)){
			    ShowProductFrame.this.setVisible(false);
				}
		}
	}
}
