package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PhotoRenamer {
	/**
	 * Create and return the window for the directory explorer.
	 *
	 * @return the window for the directory explorer
	 */
	public static JFrame buildWindow() {
		JFrame directoryFrame = new JFrame("Photo Renamer");

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		JLabel directoryLabel = new JLabel("Select a directory");

		// Set up the area for the directory contents.
		JTextArea textArea = new JTextArea(15, 50);
		textArea.setEditable(true);
		
		JList<String> imagenames = new JList<String>();

		// Put it in a scroll pane in case the output is long.
		JScrollPane scrollPane = new JScrollPane(textArea);

		// The directory choosing button.
		JButton openButton = new JButton("Choose Directory");
		openButton.setVerticalTextPosition(AbstractButton.CENTER);
		openButton.setHorizontalTextPosition(AbstractButton.LEADING); 
		
		openButton.setMnemonic(KeyEvent.VK_D);
		openButton.setActionCommand("disable");

		

		// Put it all together.
		Container c = directoryFrame.getContentPane();
		c.add(directoryLabel, BorderLayout.PAGE_START);
		c.add(scrollPane, BorderLayout.CENTER);
		c.add(openButton, BorderLayout.PAGE_END);
		directoryFrame.add(imagenames, BorderLayout.WEST);

		directoryFrame.pack();
		return directoryFrame;
	}

	/**
	 * Create and show a photo renamer
	 *
	 * @param argsv
	 *            the command-line arguments.
	 */
	public static void main(String[] args) {
		PhotoRenamer.buildWindow().setVisible(true);
	}
}
