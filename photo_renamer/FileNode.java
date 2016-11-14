package photo_renamer;

import java.io.Serializable;

public abstract class FileNode implements Serializable {

	/** Default serial version ID */
	private static final long serialVersionUID = -5374282158284780436L;

	/** The name of the image or directory this node represents. */
	protected String name;

	/** This node's parent. */
	private Directory parent;

	/** This node's path. */
	private String path;

	/**
	 * Creates a new FileNode with its name and its parent directory and also
	 * records the path of itself from the path of its parent
	 */
	public FileNode(String name, Directory parent) {
		this.name = name;
		this.parent = parent;
		this.path = this.parent.getPath() + "/" + this.name;
	}

	/**
	 * Creates a new FileNode with its name and its parent directory and also
	 * specifies the path of the file it represents.
	 */
	public FileNode(String name, Directory parent, String path) {
		this.name = name;
		this.parent = parent;
		this.path = path;
	}

	/**
	 * Returns the path of the file represented by this FileNode.
	 * 
	 * @return the path of the file represented by this FileNode
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the path of the file represented by this FileNode.
	 * 
	 * @param path
	 *            the new path of the file
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Returns the name of the file or directory represented by this node.
	 *
	 * @return name of this Node
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the parent of this FileNode.
	 * 
	 * @return the parent of this FileNode
	 */
	public Directory getParent() {
		return parent;
	}

	/**
	 * Sets this FileNode's parent to p.
	 * 
	 * @param p
	 *            the parent to set
	 */
	public void setParent(Directory p) {
		this.parent = p;
	}

	/**
	 * Returns whether this FileNode is a Directory.
	 * 
	 * @return true if this FileNode is a Directory
	 */
	public abstract boolean isDirectory();

	/**
	 * Returns whether this FileNode is an Image.
	 * 
	 * @return true if this FileNode is an Image
	 */
	public abstract boolean isImage();

}
