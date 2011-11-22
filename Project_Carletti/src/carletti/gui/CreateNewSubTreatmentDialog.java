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

public class CreateNewSubTreatmentDialog extends JDialog {
	private boolean succes = false;
	private String name;
	private long min, optimal, max;
	
	JButton btnNewSubTreatmentCancel, btnNewSubTreatmentCreate;
	private Controller controller;
	private JTextField textFieldNewSubTreatmentName;
	private JTextField textFieldNewSubTreatmentMin;
	private JTextField textFieldNewSubTreatmentOptimal;
	private JTextField textFieldNewSubTreatmentMax;
	public CreateNewSubTreatmentDialog() {
		setTitle("New subtreatment");
		getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		this.setModal(true);
		controller = new Controller();
		
		JPanel subTreatmentPanel = new JPanel();
		getContentPane().add(subTreatmentPanel);
		subTreatmentPanel.setLayout(new BoxLayout(subTreatmentPanel, BoxLayout.Y_AXIS));
		
		JLabel lblNewSubtreatmentLabel = new JLabel("New subtreatment");
		subTreatmentPanel.add(lblNewSubtreatmentLabel);
		
		JPanel newSubTreatmentNamePanel = new JPanel();
		subTreatmentPanel.add(newSubTreatmentNamePanel);
		newSubTreatmentNamePanel.setLayout(new BoxLayout(newSubTreatmentNamePanel, BoxLayout.X_AXIS));
		
		JLabel lblNewSubTreatmentName = new JLabel("Name:");
		newSubTreatmentNamePanel.add(lblNewSubTreatmentName);
		
		textFieldNewSubTreatmentName = new JTextField();
		newSubTreatmentNamePanel.add(textFieldNewSubTreatmentName);
		textFieldNewSubTreatmentName.setColumns(10);
		
		JLabel lblDryingTimes = new JLabel("Drying times");
		subTreatmentPanel.add(lblDryingTimes);
		
		JPanel newSubTreatmentMinPanel = new JPanel();
		subTreatmentPanel.add(newSubTreatmentMinPanel);
		newSubTreatmentMinPanel.setLayout(new BoxLayout(newSubTreatmentMinPanel, BoxLayout.X_AXIS));
		
		JLabel lblNewSubTreatmentMin = new JLabel("Minimum:");
		newSubTreatmentMinPanel.add(lblNewSubTreatmentMin);
		
		textFieldNewSubTreatmentMin = new JTextField();
		newSubTreatmentMinPanel.add(textFieldNewSubTreatmentMin);
		textFieldNewSubTreatmentMin.setColumns(10);
		
		JPanel newSubTreatmentOptimalPanel = new JPanel();
		subTreatmentPanel.add(newSubTreatmentOptimalPanel);
		newSubTreatmentOptimalPanel.setLayout(new BoxLayout(newSubTreatmentOptimalPanel, BoxLayout.X_AXIS));
		
		JLabel lblNewSubTreatmentOptimal = new JLabel("Optimal:");
		newSubTreatmentOptimalPanel.add(lblNewSubTreatmentOptimal);
		
		textFieldNewSubTreatmentOptimal = new JTextField();
		newSubTreatmentOptimalPanel.add(textFieldNewSubTreatmentOptimal);
		textFieldNewSubTreatmentOptimal.setColumns(10);
		
		JPanel newSubTreatmentMaxPanel = new JPanel();
		subTreatmentPanel.add(newSubTreatmentMaxPanel);
		newSubTreatmentMaxPanel.setLayout(new BoxLayout(newSubTreatmentMaxPanel, BoxLayout.X_AXIS));
		
		JLabel lblNewSubTreatmentMax = new JLabel("Maximum:");
		newSubTreatmentMaxPanel.add(lblNewSubTreatmentMax);
		
		textFieldNewSubTreatmentMax = new JTextField();
		newSubTreatmentMaxPanel.add(textFieldNewSubTreatmentMax);
		textFieldNewSubTreatmentMax.setColumns(10);
		
		JPanel newSubTreatmentButtonsPanel = new JPanel();
		subTreatmentPanel.add(newSubTreatmentButtonsPanel);
		
		btnNewSubTreatmentCreate = new JButton("Create");
		newSubTreatmentButtonsPanel.add(btnNewSubTreatmentCreate);
		btnNewSubTreatmentCreate.addActionListener(controller);
		
		btnNewSubTreatmentCancel = new JButton("Cancel");
		newSubTreatmentButtonsPanel.add(btnNewSubTreatmentCancel);
		btnNewSubTreatmentCancel.addActionListener(controller);
		
		this.pack();
	}
	
	private class Controller implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae) {
		
			if (ae.getSource() == btnNewSubTreatmentCreate){
				String tempName = textFieldNewSubTreatmentName.getText();
				long tempMin = 0, tempOptimal = 0, tempMax = 0;
				try {
					tempMin = Long.parseLong(textFieldNewSubTreatmentMin.getText());
					tempOptimal = Long.parseLong(textFieldNewSubTreatmentOptimal.getText());
					tempMax = Long.parseLong(textFieldNewSubTreatmentMax.getText());
					succes = true;
				}
				catch(NumberFormatException nfe){
					succes = false;
				}
				if (succes){
					name = tempName;
					min = tempMin;
					optimal = tempOptimal;
					max = tempMax;
				}
				CreateNewSubTreatmentDialog.this.setVisible(false);
			}
			else if (ae.getSource() == btnNewSubTreatmentCancel){
				CreateNewSubTreatmentDialog.this.setVisible(false);
			}
		}
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
