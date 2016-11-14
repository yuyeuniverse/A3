package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;;

public class GUI {
	public static void main(String[] args) {
		JFrame jf = new JFrame("Photo Renamer");
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		JLabel jlabel = new JLabel("Select a directory");
		
		JList<String> imagenames = new JList<String> ();
		
//		JTextArea textArea = new JTextArea(15, 50);
//		textArea.setEditable(true);
//		// Put it in a scroll pane in case the output is long.
//		JScrollPane scrollPane = new JScrollPane(textArea);
		
		FileChooserButton openButton = new FileChooserButton(jf, imagenames, jlabel, fileChooser, "Select Directory");
//		openButton.setVerticalTextPosition(AbstractButton.CENTER);
//		openButton.setHorizontalTextPosition(AbstractButton.LEADING); 
//		openButton.setMnemonic(KeyEvent.VK_D);
//		openButton.setActionCommand("disable");
		
		Container c = jf.getContentPane();
		c.add(jlabel, BorderLayout.PAGE_START);
//		c.add(scrollPane, BorderLayout.CENTER);
		c.add(openButton, BorderLayout.PAGE_END);
		c.add(openButton.getImageNames(), BorderLayout.WEST);
		
//		JPanel searchPanel = new JPanel();
//		JTextField textField = new JTextField();
//		textField.getPreferredSize();
//		searchPanel.add(textField, BorderLayout.WEST);
//		searchPanel.add(selectDirButton, BorderLayout.EAST);
//		jf.add(searchPanel, BorderLayout.NORTH);
//		
//		JPanel namesPanel = new JPanel();
//		
//		jf.add(namesPanel, BorderLayout.WEST);
		
		jf.pack();
		jf.setVisible(true);
	}
}
