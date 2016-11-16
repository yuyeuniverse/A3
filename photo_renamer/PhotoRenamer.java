package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;;

public class PhotoRenamer {
	
	public static JFrame buildWindow() throws ClassNotFoundException, IOException {
		
		String filePath1 = "/Users/zikunchen/Desktop/TEST/im.ser";
		String filePath2 = "/Users/zikunchen/Desktop/TEST/tm.ser";
		String filePath3 = "/Users/zikunchen/Desktop/TEST/user.ser";
		User user = new User(filePath1, filePath2, filePath3);
		
		
		JFrame jf = new JFrame("Photo Renamer");
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		JLabel jlabel = new JLabel("Select a directory");
		
		JList<Image> images = new JList<Image> ();
		
		JPanel imagePanel = new JPanel();
		
//		JTextArea textArea = new JTextArea(15, 50);
//		textArea.setEditable(true);
//		// Put it in a scroll pane in case the output is long.
//		JScrollPane scrollPane = new JScrollPane(textArea);
		
		SelectDirButton selectDirButton = new SelectDirButton(jf, images, jlabel, fileChooser, 
				imagePanel, user, "Select Directory");
		selectDirButton.setVerticalTextPosition(AbstractButton.CENTER);
		selectDirButton.setHorizontalTextPosition(AbstractButton.LEADING); 
		selectDirButton.setMnemonic(KeyEvent.VK_D);
		selectDirButton.setActionCommand("disable");
		

		
		// new ListSelectionListener
		
		Container c = jf.getContentPane();
		c.add(jlabel, BorderLayout.PAGE_START);
//		c.add(scrollPane, BorderLayout.CENTER);
		c.add(selectDirButton, BorderLayout.PAGE_END);
		//jf.add(imagePanel, BorderLayout.CENTER);
		
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
		jf.setLocationRelativeTo(null);

		return jf;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		PhotoRenamer.buildWindow().setVisible(true);
	}
}
