package photo_renamer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;

/**
 * The listener for the button to choose a directory. This is where most of the
 * work is done.
 */
public class FileChooserButton extends JButton implements ActionListener {

	/** The window the button is in. */
	private JFrame jf;
	/** The label for the full path to the chosen directory. */
	private JLabel jlabel;
	/** The file chooser to use when the user clicks. */
	private JFileChooser fileChooser;
	/** The area to use to display the nested directory contents. */
	//private JTextArea textArea;
	private JList<String> imagenames;

	/**
	 * An action listener for window dirFrame, displaying a file path on
	 * dirLabel, using fileChooser to choose a file.
	 *
	 * @param dirFrame
	 *            the main window
	 * @param dirLabel
	 *            the label for the directory path
	 * @param fileChooser
	 *            the file chooser to use
	 */
	public FileChooserButton(JFrame jf, JList<String> imagenames, JLabel jlabel, JFileChooser fileChooser, String buttonname) {
		super(buttonname);
		//selectDirButton
		this.jf = jf;
		this.jlabel = jlabel;
		//this.textArea = textArea;
		this.fileChooser = fileChooser;
		this.imagenames = imagenames;
		this.addActionListener(this);
	}
	
	public JList<String> getImageNames() {
		return imagenames;
	}

	/**
	 * Handle the user clicking on the open button.
	 *
	 * @param e
	 *            the event object
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		int returnVal = fileChooser.showOpenDialog(jf.getContentPane());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (file.exists()) {
				this.jlabel.setText("Selected File" + file.getAbsolutePath());
				
				// Display a temporary message while the directory is
				// traversed.
				//this.textArea.setText("Building file tree …");

				// Make the root.
				Directory fileTree = new Directory(file.getName(), null, file.getAbsolutePath());
				
				fileTree.buildImageTree(file);

				// Build the string representation and put it into the text
				// area.
				StringBuffer contents = new StringBuffer();
				
				fileTree.buildListOfImages(contents);
				String[] data = fileTree.stringBufferToArray(contents);
				this.imagenames = new JList<String>(data);
				//this.textArea.setText(contents.toString());
			}
		} else {
			jlabel.setText("No Path Selected");
		}
	}
}
