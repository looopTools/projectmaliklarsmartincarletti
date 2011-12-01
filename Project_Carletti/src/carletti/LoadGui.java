 /** @author Lars Nielsen, Malik L. Lund, Martin R. Bundgaard
  * 
  */
package carletti;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class LoadGui extends JFrame{
	
	private Dimension frameSize = new Dimension(300, 200);
	private Dimension rBtnSize = new Dimension(20, 150);
	//private int x = 20, y = 80;
	
	
	
	public LoadGui() {
		
		this.setTitle("Carletti - Load Screen");
		this.setSize(frameSize);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(null);
		
		
		
		this.setVisible(true);
		
		
	}
	
	private class SelectorPanel extends JPanel implements ActionListener {
		private int crossover;
		private Dimension rBtnSize = new Dimension(20, 150);
		private JButton btnOk;
		private JRadioButton rbtnLocal, rbtnDao;
		
		public SelectorPanel(int crossover){
			super();
			this.crossover = crossover;
			GroupLayout layout = new GroupLayout(this);
			this.setLayout(layout);
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			
			rbtnLocal = new JRadioButton("Local");
			rbtnDao = new JRadioButton("Database");
			btnOk = new JButton("Ok");
			
			
			rbtnLocal.addActionListener(this);
			btnOk.addActionListener(this);
			
			
			layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.CENTER)
					.addComponent(btnOk)
					
			);
			
			layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addComponent(btnOk, 20, 20, 20)
			);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}

}
