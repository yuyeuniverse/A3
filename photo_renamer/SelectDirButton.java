package photo_renamer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * The listener for the button to choose a directory. This is where most of the
 * work is done.
 */
public class SelectDirButton extends JButton implements ActionListener {

	/** The window the button is in. */
	private JFrame jf;
	/** The label for the full path to the chosen directory. */
	private JLabel jlabel;
	/** The file chooser to use when the user clicks. */
	private JFileChooser fileChooser;
	/** The area to use to display the nested directory contents. */
	// private JTextArea textArea;
	private JList<Image> images;

	private JPanel imagePanel;

	private User user;

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
	public SelectDirButton(JFrame jf, JList<Image> images, JLabel jlabel, JFileChooser fileChooser, JPanel imagePanel,
			User user, String buttonname) {
		super(buttonname);
		// selectDirButton
		this.jf = jf;
		this.jlabel = jlabel;
		// this.textArea = textArea;
		this.fileChooser = fileChooser;
		this.images = images;
		this.imagePanel = imagePanel;
		this.user = user;
		this.addActionListener(this);
	}

	// public JList<Image> getImageNames() {
	// return images;
	// }

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
				
				// need to discuss
				// if dir not been selected before, what if it has? ImageManager?
				
				// can change imageList to HashMap<FilePath, Image> and 
				// check the path (unique) Images in the tree every time it's built
				// but how to put into JList? 
				// Replace empty Image in the tree with the one in history? New Method for that?
				user.addDir(file.getAbsolutePath());
				
				
				
				// Display a temporary message while the directory is
				// traversed.
				// this.textArea.setText("Building file tree …");

				// Make the root.
				Directory fileTree = new Directory(file.getName(), null, file.getAbsolutePath());

				fileTree.buildImageTree(file);

				// Build the string representation and put it into the text
				// area.
				// StringBuffer contents = new StringBuffer();

				// fileTree.buildListOfImages(contents);
				// String[] data = fileTree.buildListOfImages(contents);
				Image[] data = fileTree.getImages();
				
				// solution? added two im and user methods getPaths and findImWithPath
				ArrayList<Image> result = new ArrayList<Image> ();
				ArrayList<String> paths = user.getPaths();
				
				for (Image i : data) {
					if (paths.contains(i.getPath())) {
						result.add(user.findImageWithPath(i.getPath())) ;
					} else {
						result.add(i);
					}
				}
				data = result.toArray(new Image[data.length]);
				
				this.images = new JList<Image>(data);
				// this.textArea.setText(contents.toString());

				images.setLayoutOrientation(images.VERTICAL);
				images.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				images.setVisible(true);
				jf.add(images, BorderLayout.WEST);
				jf.setSize(720, 540);
				jf.setLocationRelativeTo(null);

				// seperate JList listener class?
				images.addListSelectionListener(new ImageListSelectionListener(jf, images, user, imagePanel));
//				{
//					@Override
//					public void valueChanged(ListSelectionEvent e) {
//						
//						user.selectImage(images.getSelectedValue());
//
//						BufferedImage img = null;
//						try {
//							img = ImageIO.read(new File(images.getSelectedValue().getPath()));
//						} catch (IOException e1) {
//							e1.printStackTrace();
//						}
//						images.getSelectedValue();
//						ImageIcon icon = new ImageIcon(img);
//						imagePanel.removeAll();
//						JLabel imageLabel = new JLabel(null, icon, JLabel.CENTER);
//
//						// add image JPanel at the start
//						imagePanel.add(imageLabel);
//						imagePanel.setPreferredSize(imagePanel.getPreferredSize());
//						jf.add(imagePanel, BorderLayout.CENTER);
//
//						jf.pack();
//					}
//				});

			}
		} else {
			jlabel.setText("Please Select A Directory");
		}
	}
}
