package carletti.gui.dialogs;

/**
 *@author Lars Nielsen, Malik Lasse Lund, Martin R¿nn Bundgaard
 *@class NewSubProductDialog
 *@programmer Lars Nielsen
 */

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class NewSubProductDialog extends JDialog{

    private JTextField txfName, txfID;
    private JScrollPane jspProducts;
    private JList prodList;
    private int x = 20, i = 20;
    private Dimension minimumSize = new Dimension(250, 200);
    public NewSubProductDialog(){
    	
    }
    
}