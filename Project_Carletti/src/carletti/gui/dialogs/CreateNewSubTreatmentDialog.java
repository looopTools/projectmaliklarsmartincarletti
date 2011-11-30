package carletti.gui.dialogs;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

public class CreateNewSubTreatmentDialog extends JDialog {

	private Controller controller;
	private JPanel mainPanel, infoPanel;
	private TimeSelectorPanel minPanel, optimalPanel, maxPanel;
	private JLabel lblNew, lblName, lblMin, lblOptimal, lblMax;
	private JTextField txfName, txfMin, txfOptimal, txfMax;
	private JButton btnCreate, btnCancel;

	private boolean succes;
	private NewProductSubTreatmentsTableModel subTreatmentsTableModel;
	
	/**
	 * 
	 * @param subTreatmentsTableModel
	 */
	public CreateNewSubTreatmentDialog(NewProductSubTreatmentsTableModel subTreatmentsTableModel){
		this.getContentPane().setLayout(new FlowLayout());
		this.setModal(true);
		this.setResizable(false);
		this.subTreatmentsTableModel = subTreatmentsTableModel;
		
		controller = new Controller();
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.add(mainPanel);
		
		infoPanel = new JPanel();
		GroupLayout layout = new GroupLayout(infoPanel);
		infoPanel.setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		minPanel = new TimeSelectorPanel();
		optimalPanel = new TimeSelectorPanel();
		maxPanel = new TimeSelectorPanel();
		lblNew = new JLabel("New subtreatment");
		lblName = new JLabel("Name:");
		lblMin = new JLabel("Minimum:");
		lblOptimal = new JLabel("Optimal:");
		lblMax = new JLabel("Maximum:");
		txfName = new JTextField();
		txfMin = new JTextField();
		txfOptimal = new JTextField();
		txfMax = new JTextField();
		btnCreate = new JButton("Create");
		btnCancel = new JButton("Cancel");
		
		btnCreate.addActionListener(controller);
		btnCancel.addActionListener(controller);
		
		mainPanel.add(lblNew, BorderLayout.NORTH);
		
		/**
		 * Define main grouping horizontally.
		 */
		layout.setHorizontalGroup(
		    layout.createSequentialGroup()
		    	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		    		.addComponent(lblName)
		    		.addComponent(lblMin)
		    		.addComponent(lblOptimal)
		    		.addComponent(lblMax)
		    		.addComponent(btnCreate))
		    	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		    		.addComponent(txfName)
		    		.addComponent(minPanel)
		    		.addComponent(optimalPanel)
		    		.addComponent(maxPanel)
		    		.addComponent(btnCancel))
		);
		
		/**
		 * Define main grouping vertically.
		 */
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lblName)
					.addComponent(txfName))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lblMin)
					.addComponent(minPanel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lblOptimal)
					.addComponent(optimalPanel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(lblMax)
					.addComponent(maxPanel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(btnCreate)
					.addComponent(btnCancel))
		);

		mainPanel.add(infoPanel);
		
		pack();
	}
	
	private class Controller implements ActionListener{


		@Override
		public void actionPerformed(ActionEvent ae) {
		
			if (ae.getSource() == btnCreate){
				subTreatmentsTableModel.newSubTreatment(txfName.getText(), minPanel.getTime(), optimalPanel.getTime(), maxPanel.getTime());
				CreateNewSubTreatmentDialog.this.setVisible(false);
			}
			else if (ae.getSource() == btnCancel){
				CreateNewSubTreatmentDialog.this.setVisible(false);
			}
		}
	}
	
	private class TimeSelectorPanel extends JPanel{
		
		private JLabel lblDays, lblHours, lblMinutes, lblSeconds;
		private SelectorPanel daySelector, hourSelector, minuteSelector, secondSelector;
		
		public TimeSelectorPanel(){
			super();
			GroupLayout layout = new GroupLayout(this);
			this.setLayout(layout);
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			
			lblDays = new JLabel("Days");
			lblHours = new JLabel("Hours");
			lblMinutes = new JLabel("Minutes");
			lblSeconds = new JLabel("Seconds");
			daySelector = new SelectorPanel(Integer.MAX_VALUE);
			hourSelector = new SelectorPanel(24);
			minuteSelector = new SelectorPanel(60);
			secondSelector = new SelectorPanel(60);
			
			layout.setHorizontalGroup(
				layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(lblDays)
						.addComponent(daySelector))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(lblHours)
						.addComponent(hourSelector))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(lblMinutes)
						.addComponent(minuteSelector))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(lblSeconds)
						.addComponent(secondSelector))
			);
			
			layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lblDays)
						.addComponent(lblHours)
						.addComponent(lblMinutes)
						.addComponent(lblSeconds))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(daySelector)
						.addComponent(hourSelector)
						.addComponent(minuteSelector)
						.addComponent(secondSelector))
			);
		}
		
		public long getTime(){
			long result = daySelector.getTime() * 1000 * 60 * 60 * 24;
			result += hourSelector.getTime() * 1000 * 60 * 60;
			result += minuteSelector.getTime() * 1000 * 60;
			result += secondSelector.getTime() * 1000;
			return result;
		}
	}
	
	private class SelectorPanel extends JPanel implements ActionListener {
		private int crossover;
		private JTextField txfNumber;
		private JButton btnPlus, btnMinus;
		
		public SelectorPanel(int crossover){
			super();
			this.crossover = crossover;
			GroupLayout layout = new GroupLayout(this);
			this.setLayout(layout);
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			
			txfNumber = new JTextField("0");
			btnPlus = new JButton("+");
			btnMinus = new JButton("-");
			
			btnPlus.addActionListener(this);
			btnMinus.addActionListener(this);
			
			layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.CENTER)
					.addComponent(btnPlus)
					.addComponent(txfNumber)
					.addComponent(btnMinus)
			);
			
			layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addComponent(btnPlus, 20, 20, 20)
					.addComponent(txfNumber)
					.addComponent(btnMinus, 20, 20, 20)
			);
		}
		
		public long getTime() throws NumberFormatException{
			return Long.parseLong(txfNumber.getText());
		}

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == btnPlus){
				int number = getNumber();
				number = (number + 1) % crossover;
				putNumber(number);
			}
			else if (ae.getSource() == btnMinus){
				int number = getNumber();
				number = (number - 1);
				if (number < 0){
					number = crossover - 1;
				}
				putNumber(number);
			}
		}
		
		private int getNumber() throws NumberFormatException{
			int number = 0;
			if (txfNumber.getText().length() > 0){
				number = Integer.parseInt(txfNumber.getText());
			} else {
				number = 0;
			}
			
			if (number >= crossover){
				number = crossover - 1;
			}
			if (number < 0){
				number = 0;
			}
			return number;
		}
		
		private void putNumber(int n){
			txfNumber.setText(n + "");
		}
	}
}
