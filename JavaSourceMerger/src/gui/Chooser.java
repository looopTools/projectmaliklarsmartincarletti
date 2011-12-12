package gui;

import javax.swing.JFileChooser;
import service.Service;

/**
 * 
 * @author Lars Nielsen
 *
 */
public class Chooser {

	public Chooser() {
		JFileChooser pathChooser = new JFileChooser();
		pathChooser.setCurrentDirectory(new java.io.File("."));
		pathChooser.setDialogTitle("Select source folder");
		pathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// Disable the "All files" option
		pathChooser.setAcceptAllFileFilterUsed(false);
		if (pathChooser.showOpenDialog(pathChooser) == JFileChooser.APPROVE_OPTION) {
			Service.setPath(pathChooser.getCurrentDirectory()); //
			
			// Hvis jeg kalder getCurrentDirectory, så mangler jeg altid den
			// "sidste" mappe?
			// Service.setPath(pathChooser.getSelectedFile().getAbsolutePath());
		}
	}

}
