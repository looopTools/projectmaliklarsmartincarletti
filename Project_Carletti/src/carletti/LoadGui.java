/** @author Lars Nielsen, Malik L. Lund, Martin R. Bundgaard
 * 
 */
package carletti;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import carletti.dao.Dao;
import carletti.dao.JpaDao;
import carletti.dao.LocalDao;
import carletti.gui.MainFrame;
import carletti.service.Service;

@SuppressWarnings("serial")
public class LoadGui extends JFrame {

	private Dimension frameSize = new Dimension(300, 200);
	private JPanel selectPanel;

	// private int x = 20, y = 80;

	public LoadGui() {

		this.setTitle("Carletti - Load Screen");
		this.setSize(frameSize);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);

		selectPanel = new SelectorPanel();
		this.add(selectPanel);

		this.setVisible(true);

	}

	private class SelectorPanel extends JPanel implements ActionListener {
		private JButton btnOk;
		private JRadioButton rbtnLocal, rbtnDao;

		public SelectorPanel() {
			super();
			GroupLayout layout = new GroupLayout(this);
			this.setLayout(layout);
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);

			rbtnLocal = new JRadioButton("Local");
			rbtnDao = new JRadioButton("Database");
			btnOk = new JButton("Ok");

			rbtnLocal.addActionListener(this);
			rbtnDao.addActionListener(this);
			btnOk.addActionListener(this);

			layout.setHorizontalGroup(layout
					.createSequentialGroup()
					.addGroup(
							layout.createParallelGroup(
									GroupLayout.Alignment.CENTER)
									.addComponent(rbtnLocal)
									.addComponent(btnOk)).addComponent(rbtnDao));

			layout.setVerticalGroup(layout
					.createSequentialGroup()
					.addGroup(
							layout.createParallelGroup(
									GroupLayout.Alignment.CENTER)
									.addComponent(rbtnLocal)
									.addComponent(rbtnDao))
					.addComponent(btnOk, 20, 20, 20));
		}

		@Override
		public void actionPerformed(ActionEvent ae) {
			Dao dao;
			if (ae.getSource().equals(btnOk)) {
				if (rbtnLocal.isSelected() == true
						&& rbtnDao.isSelected() == false) {
					dao = LocalDao.getInstance();
					startProgram(dao);
				} else if (rbtnLocal.isSelected() == false
						&& rbtnDao.isSelected() == true) {
					dao = JpaDao.getInstance();
					startProgram(dao);
				} 
			}
			else if(ae.getSource().equals(rbtnLocal)){
				rbtnDao.setSelected(false);
			}
			else if(ae.getSource().equals(rbtnDao)){
				rbtnLocal.setSelected(false);
			}
		}
	}
	
	private void startProgram(Dao dao){
		Service s = Service.getInstance(dao);
		s.createSomeObjects();
		this.setVisible(false);
		MainFrame mf = new MainFrame();
	}

}
