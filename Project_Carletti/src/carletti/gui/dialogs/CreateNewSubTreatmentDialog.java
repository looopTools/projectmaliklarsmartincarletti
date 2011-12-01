package carletti.gui.dialogs;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Handles the creation of a new SubTreatment and
 * associates it with creation of a new Treatment.
 * @author Malik Lund
 *
 */
public class CreateNewSubTreatmentDialog extends JDialog {

	private Controller controller;
	private JPanel mainPanel, infoPanel;
	private TimeSelectorPanel minPanel, optimalPanel, maxPanel;
	private JLabel lblNew, lblName, lblMin, lblOptimal, lblMax;
	private JTextField txfName;
	private JButton btnCreate, btnCancel;

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
	
	/**
	 * This private class handles all input events.
	 * @author Malik
	 *
	 */
	private class Controller implements ActionListener{


		@Override
		public void actionPerformed(ActionEvent ae) {
			/**
			 * Create button.
			 */
			if (ae.getSource() == btnCreate){
				// --- input verification start ---
				boolean error = false;
				String name = txfName.getText();
				if (name.length() <= 0){
					JOptionPane.showMessageDialog(null, "Name is empty!");
					error = true;
				}
				
				long min = minPanel.getTime();
				if (!error && min < 0){
					JOptionPane.showMessageDialog(null, "Minimum must be larger than 0!");
					error = true;
				}
				
				long optimal = optimalPanel.getTime();
				if (!error && optimal < min){
					JOptionPane.showMessageDialog(null, "Optimal must be larger than minimum!");
					error = true;
				}
				
				long max = maxPanel.getTime();
				if (!error && max < optimal){
					JOptionPane.showMessageDialog(null, "Maximum must be larger than optimal!");
					error = true;
				}
				// --- input verification end ---
				
				if (!error){
					subTreatmentsTableModel.newSubTreatment(name, min, optimal, max);
					CreateNewSubTreatmentDialog.this.setVisible(false);
				}
			}
			/**
			 * Cancel button.
			 */
			else if (ae.getSource() == btnCancel){
				CreateNewSubTreatmentDialog.this.setVisible(false);
			}
		}
	}
	
	/**
	 * Creates a panel containing four SelectorPanel objects (see below). One for
	 * selecting number of days, one for number of hours, one for minutes
	 * and one for seconds (it is assumed that it is unnecessary to input
	 * milliseconds as values).
	 * @author Malik Lund
	 *
	 */
	class TimeSelectorPanel extends JPanel{
		
		private JLabel lblDays, lblHours, lblMinutes, lblSeconds;
		private SelectorPanel daySelector, hourSelector, minuteSelector, secondSelector;
		
		/**
		 * 
		 */
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
			daySelector = new SelectorPanel(100);
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
		
		/**
		 * Returns the time represented by all four textfields
		 * represented in milliseconds.
		 * @return
		 */
		public long getTime(){
			long result = daySelector.getTime() * 1000 * 60 * 60 * 24;
			result += hourSelector.getTime() * 1000 * 60 * 60;
			result += minuteSelector.getTime() * 1000 * 60;
			result += secondSelector.getTime() * 1000;
			return result;
		}
	}
	
	/**
	 * A panel containing a plus- and minus-button and a textfield with
	 * a number. Pressing the plus button increments the value in the 
	 * textfield by one, while pressing the minus button decrements the
	 * value by one.
	 * If the value gets larger than the given crossover point the value
	 * wraps around to zero. If a value gets smaller than 0 it wraps around
	 * to the larges value below the crossover point.
	 * @author Malik Lund
	 *
	 */
	private class SelectorPanel extends JPanel implements ActionListener {
		private int crossover;
		private JTextField txfNumber;
		private JButton btnPlus, btnMinus;
		
		/**
		 * 
		 * @param crossover Value where the number crosses over
		 */
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
			
			txfNumber.setHorizontalAlignment(JTextField.RIGHT);
			
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
		
		/**
		 * 
		 * @return The the number in the text field as a long.
		 * @throws NumberFormatException
		 */
		public long getTime() throws NumberFormatException{
			return Long.parseLong(txfNumber.getText());
		}

		@Override
		public void actionPerformed(ActionEvent ae) {
			/**
			 * Increment button.
			 */
			if (ae.getSource() == btnPlus){
				int number = getNumber();
				number = (number + 1) % crossover;
				txfNumber.setText(number + "");
			}
			
			/**
			 * Decrement button.
			 */
			else if (ae.getSource() == btnMinus){
				int number = getNumber();
				number = (number - 1);
				if (number < 0){
					number = crossover - 1;
				}
				txfNumber.setText(number + "");
			}
		}
		
		/**
		 * Performs various safety checks on the value in the
		 * text field.
		 * @return
		 * @throws NumberFormatException
		 */
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
	}
}
