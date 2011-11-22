package carletti.gui.dialogs;

import javax.swing.JDialog;

import carletti.model.SubProduct;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

public class NextSubTreatmentDialog extends JDialog{
	
	private SubProduct sub;
	public NextSubTreatmentDialog(SubProduct sub){
		this.sub = sub;
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel lblTxt = new JPanel();
		getContentPane().add(lblTxt, BorderLayout.CENTER);
		
		JLabel lblMes = new JLabel("Do you want to continue to next Subtreatment?");
		lblTxt.add(lblMes);
		
		JPanel btnPanel = new JPanel();
		getContentPane().add(btnPanel, BorderLayout.SOUTH);
		
		JButton btnYes = new JButton("YES");
		btnPanel.add(btnYes);
		
		JButton btnNO = new JButton("NO");
		btnPanel.add(btnNO);
		
	}

}
