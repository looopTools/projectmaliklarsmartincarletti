package carletti.gui;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class CopyOfCreateNewSubTreatmentDialog extends JDialog {
	private boolean succes = false;
	private String name;
	private long min, optimal, max;
//	private Controller controller;
	public CopyOfCreateNewSubTreatmentDialog() {
		setTitle("New subtreatment");
		getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblNew = new JLabel("New subtreatment");
		JLabel lblName = new JLabel("Name:");
		JLabel lblMin = new JLabel("Minimum:");
		JLabel lblOptimal = new JLabel("Optimal:");
		JLabel lblMax = new JLabel("Maximum:");
		JTextField txfName = new JTextField();
		JTextField txfMin = new JTextField();
		JTextField txfOptimal = new JTextField();
		JTextField txfMax = new JTextField();
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setAutoCreateGaps(true);
		gl_panel.setAutoCreateContainerGaps(true);
		gl_panel.setHorizontalGroup(
			gl_panel.createSequentialGroup()
				.addGroup(gl_panel.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(lblNew)
					.addComponent(lblName)
					.addComponent(lblMin)
					.addComponent(lblOptimal)
					.addComponent(lblMax))
				.addGroup(gl_panel.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(txfName, 50, 75, 100)
					.addComponent(txfMin, 50, 75, 100)
					.addComponent(txfOptimal, 50, 75, 100)
					.addComponent(txfMax, 50, 75, 100))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createSequentialGroup()
				.addComponent(lblNew)
				.addGroup(gl_panel.createParallelGroup()
						.addComponent(lblNew)
						.addGroup(gl_panel.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(lblName)
							.addComponent(txfName))
						.addGroup(gl_panel.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(lblMin)
							.addComponent(txfMin))
						.addGroup(gl_panel.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(lblOptimal)
							.addComponent(txfOptimal))
						.addGroup(gl_panel.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(lblMax)
							.addComponent(txfMax)))
		);
		panel.setLayout(gl_panel);
		this.setModal(true);
		
		this.pack();
	}

	public String getName() {
		return name;
	}

	public long getMin() {
		return min;
	}

	public long getOptimal() {
		return optimal;
	}

	public long getMax() {
		return max;
	}
	
	public boolean succes(){
		return succes;
	}
}
