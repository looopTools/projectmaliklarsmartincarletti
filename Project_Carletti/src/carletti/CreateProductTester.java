package carletti;

import javax.swing.JDialog;
import javax.swing.JFrame;

import carletti.gui.CreateNewProductDialog;
import carletti.service.Service;

public class CreateProductTester extends JFrame {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		JFrame frame = new CreateProductTester();
		JDialog dialog = new CreateNewProductDialog();
		dialog.setModal(true);
		dialog.setVisible(true);
		System.out.println(Service.getProducts());

	}

}
