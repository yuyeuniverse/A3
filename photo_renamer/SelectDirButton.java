package photo_renamer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class SelectDirButton extends JButton implements ActionListener{
	
	public SelectDirButton(String label) {
		super(label);
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//buildTree and stuff
	}

}
