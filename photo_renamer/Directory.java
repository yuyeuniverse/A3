package photo_renamer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Directory extends FileNode {

	/** Default serial version ID */
	private static final long serialVersionUID = -8747911201648835832L;

	/** This directory's children, mapped from the file names to the nodes */
	private Map<String, FileNode> children;

	/** A list of extensions representing an image */
	private static final ArrayList<String> IMAGE_EXTENSION = new ArrayList<String>() {
		private static final long serialVersionUID = -8282854775975384587L;

		{
			add("jpeg");
			add("jpg");
			add("png");
			add("gif");
			add("bmp");
		}
	};

	/**
	 * Creates a new Directory with its name and its parent directory and also
	 * records the path of itself from the path of its parent
	 */
	public Directory(String name, Directory parent) {
		super(name, parent);
		this.children = new HashMap<String, FileNode>();
	}

	/**
	 * Creates a new Directory with its name and its parent directory and also
	 * specifies the path of the directory it represents
	 */
	public Directory(String name, Directory parent, String path) {
		super(name, parent, path);
		this.children = new HashMap<String, FileNode>();
	}

	/**
	 * Returns the child nodes of this directory.
	 *
	 * @return the child nodes directly underneath this directory.
	 */
	public Collection<FileNode> getChildren() {
		return this.children.values();
	}

	/**
	 * Adds childNode, representing an image or directory called name, as a
	 * child of this directory.
	 * 
	 * @param name
	 *            the name of the file or directory
	 * @param childNode
	 *            the node to add as a child
	 */
	public void addChild(String name, FileNode childNode) {
		this.children.put(name, childNode);
	}

	/**
	 * Builds the tree of images and directories under this directory rooted at
	 * file in the file system; note this only adds nodes for children of this
	 * directory to the tree. Precondition: file represents a directory. (the
	 * tree will be built after user selects a directory in the JFileChooser)
	 * 
	 * @param file
	 *            the directory we are building the tree at
	 */
	public void buildImageTree(File file) {
		if (file.isDirectory()) {
			File[] childList = file.listFiles();
			for (File child : childList) {
				if (child.isDirectory()) {
					this.addChild(child.getName(), new Directory(child.getName(), this));
				} else {
					String[] tokens = child.getName().split("\\.");
					String extension = tokens[tokens.length - 1];
					if (IMAGE_EXTENSION.contains(extension)) {
						this.addChild(child.getName(), new Image(child.getName(), this));
					}
				}
			}
			for (File child : childList) {
				if (this.findDirectChild(child.getName()) != null) {
					if (this.findDirectChild(child.getName()).isDirectory()) {
						Directory childDirectory = (Directory) this.findDirectChild(child.getName());
						childDirectory.buildImageTree(child);
					}
				}
			}
		}
	}

	/**
	 * Returns a ArrayList of Images under this directory.
	 * 
	 * @return a ArrayList of Images under this directory
	 */
	public ArrayList<Image> getListOfImages() {
		ArrayList<Image> result = new ArrayList<Image>();
		for (FileNode childNode : this.getChildren()) {
			if (childNode.isImage()) {
				result.add((Image) childNode);
			} else {
				result.addAll(((Directory) childNode).getListOfImages());
			}
		}
		return result;
	}

	/**
	 * Builds a string array representation of the list of the images rooted at
	 * this directory (to be viewed as a JList in GUI)
	 *
	 * @param contents
	 *            the string buffer for adding the image names
	 * @return a string array representation of the list of the images
	 */
	public String[] buildListOfImages(StringBuffer contents) {
		for (FileNode childNode : this.getChildren()) {
			if (childNode.isImage()) {
				contents.append(childNode.getName());
				contents.append("\n");
			} else {
				((Directory) childNode).buildListOfImages(contents);
			}
		}
		return contents.toString().split("\n");
	}

	@Override
	public boolean isDirectory() {
		return true;
	}

	@Override
	public boolean isImage() {
		return false;
	}

	// Helper Functions:

	/**
	 * Finds and returns a child node called name directly under this directory
	 * tree (non-recursive), or null if there is no such child node.
	 *
	 * @param name
	 *            the file name to search for
	 * @return node called name
	 */
	private FileNode findDirectChild(String name) {
		FileNode result = null;
		if (this.isDirectory()) {
			result = this.children.get(name);
			if (result == null) {
				for (FileNode child : this.children.values()) {
					if (child.getName() == name) {
						result = child;
					}
				}
			}
		}
		return result;
	}

	/**
	 * Finds and returns a child node called name in this directory tree, or
	 * null if there is no such child node.
	 *
	 * @param name
	 *            the file name to search for
	 * @return node called name
	 */
	public FileNode findChild(String name) {
		FileNode result = null;
		if (this.isDirectory()) {
			result = this.children.get(name);
			if (result == null) {
				for (FileNode child : this.children.values()) {
					if (child.getName() == name) {
						result = child;
					}
				}
			}
			if (result == null) {
				for (FileNode child : this.children.values()) {
					if (child.isDirectory()) {
						result = ((Directory) child).findChild(name);
					}
					if (result != null) {
						return result;
					}
				}
			}
		}
		return result;
	}
}
