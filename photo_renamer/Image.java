package photo_renamer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Image extends FileNode {

	/** Default serial version ID */
	private static final long serialVersionUID = 4382052753044837399L;

	/** The prefix before tags */
	private static String PREFIX = "@";

	/** The original name of the image */
	private final String original = super.getName();

	/** A list of tags that the node currently has. */
	private ArrayList<String> tags;

	/**
	 * A history of names that the node has had. also included is the date/time
	 * it was edited.
	 */
	private ArrayList<String[]> nameHistory;

	/**
	 * Creates a new directory with its name, its parent directory and an empty
	 * set of tags and nameHistory.
	 */
	public Image(String name, Directory parent) {
		super(name, parent);
		this.tags = new ArrayList<String>();
		this.nameHistory = new ArrayList<String[]>();
	}

	/**
	 * Adds a new tag t to the tags.
	 *
	 * @param t
	 *            the new tag to be added
	 */
	public void addNewTag(String t) {
		this.tags.add(t);
	}

	/**
	 * Adds a new tag t to this Image and update its nameHistory and path.
	 *
	 * @param t
	 *            the new tag to be added
	 */
	public void addTag(String t) {
		if (!this.tags.contains(t)) {
			String extension = getExtension(this.getName());
			String nameWithoutExtension = getNameWithoutExtension(this.getName());
			String oldname = this.getName();
			String newname = nameWithoutExtension + PREFIX + t + extension;
			addNameToHistory(oldname, getDateTime());
			this.tags.add(t);
			this.name = newname;
			this.setPath(this.getParent().getPath() + "/" + newname);
		} else {
			System.out.println("This image already has this tag.");
		}
	}

	/**
	 * Removes tag t from current image's name and update its nameHistory and
	 * path. or returns a message if the tag is not currently attached to the
	 * image.
	 *
	 * @param t
	 *            the tag to be deleted
	 */
	public void untag(String t) {
		if (tags.contains(t)) {
			this.tags.remove(t);
			String oldname = this.name;
			addNameToHistory(oldname, getDateTime());
			String[] pieces = this.getName().split(PREFIX + t);
			String newname = "";
			for (String p : pieces) {
				newname += p;
			}
			this.name = newname;
			this.setPath(this.getParent().getPath() + "/" + newname);
		} else {
			System.out.println("This image does not have this tag, cannot untag.");
		}
	}

	/**
	 * Restores an image's name to a name it had before, or returns error
	 * message if the name was not in the history.
	 *
	 * @param name
	 *            a historical name of this image to be restored to.
	 */
	public void restoreToName(String newname) {
		String oldname = this.getName();
		addNameToHistory(oldname, getDateTime());
		this.name = newname;
		this.setPath(this.getParent().getPath() + "/" + newname);
		String newnameWithoutExtension = getNameWithoutExtension(newname);
		String[] list = newnameWithoutExtension.split(PREFIX);
		this.tags = new ArrayList<String>();
		String s = getNameWithoutExtension(this.getOriginal());
		for (String t : list) {
			if (!t.equals(s)) {
				this.addNewTag(t);
			}
		}
	}

	/**
	 * Returns the set of tags that the image currently has.
	 * 
	 * @return an array list of tags that the image currently has
	 */
	public ArrayList<String> getTags() {
		return tags;
	}

	/**
	 * Returns history of all previous tag, untag etc activities made on image
	 * including change, and date/time
	 *
	 * @param image
	 *            image that user wants to see history
	 * @return a list of all changes made with date/time
	 * @see restoreName
	 */
	public ArrayList<String[]> getNameHistory() {
		return this.nameHistory;
	}

	/**
	 * Returns an string array representing the naming history and time for this
	 * Image. Used in GUI when user decides to see the naming history of a
	 * particular image.
	 * 
	 * @return an string array representing name history and time for this Image
	 */
	public String[] showNameHistory() {
		String result = "";
		for (String[] x : this.getNameHistory()) {
			for (String y : x) {
				result += y + " ";
			}
			result += "\n";
		}
		return result.split("\n");
	}

	/**
	 * Returns image's original name without any tags without changing image's
	 * name
	 *
	 * @return image's original name
	 */
	public String getOriginal() {
		return this.original;
	}

	/**
	 * Returns whether the image is tagged.
	 *
	 * @return true if the image is tagged
	 */
	public boolean isTagged() {
		return !this.tags.isEmpty();
	}

	@Override
	public boolean isDirectory() {
		return false;
	}

	@Override
	public boolean isImage() {
		return true;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	// Helper functions:
	/**
	 * Adds a new name and the current time to the nameHistory.
	 * 
	 * @param name
	 *            the new name to be added
	 * @param time
	 *            the current time
	 */
	private void addNameToHistory(String name, String time) {
		String[] record = { time, name };
		nameHistory.add(record);
	}

	/**
	 * Returns the extension at the end of the name of an image file.
	 * 
	 * @param name
	 *            the name of an image file
	 * @return the extension of the image file
	 */
	private String getExtension(String name) {
		String[] list = name.split("\\.");
		return "." + list[list.length - 1];
	}

	/**
	 * Returns the name of an image file without the extension
	 * 
	 * @param name
	 *            the name of an image file
	 * @return the name of the image file without the extension
	 */
	private String getNameWithoutExtension(String name) {
		String extension = getExtension(name);
		return name.substring(0, name.length() - extension.length());
	}

	/**
	 * Returns a string representing the current date and time.
	 * 
	 * @return current date and time
	 */
	private static String getDateTime() {
		DateFormat df = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		Calendar calobj = Calendar.getInstance();
		return df.format(calobj.getTime());
	}
}
