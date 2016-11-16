package photo_renamer;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ImageListSelectionListener implements ListSelectionListener {

	private JFrame jf;
	private JList<Image> images;
	private User user;
	private JPanel imagePanel;

	public ImageListSelectionListener(JFrame jf, JList<Image> images, User user, JPanel imagePanel) {
		this.jf = jf;
		this.images = images;
		this.user = user;
		this.imagePanel = imagePanel;
		// tagPanel
		// imageTagPanel
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		
		
		user.selectImage(images.getSelectedValue());

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(images.getSelectedValue().getPath()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		images.getSelectedValue();
		ImageIcon icon = new ImageIcon(img);
		imagePanel.removeAll();
		JLabel imageLabel = new JLabel(null, icon, JLabel.CENTER);

		// add image JPanel at the start
		imagePanel.add(imageLabel);
		imagePanel.setPreferredSize(imagePanel.getPreferredSize());
		jf.add(imagePanel, BorderLayout.CENTER);

		jf.pack();
	}

}
