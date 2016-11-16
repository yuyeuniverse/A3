package photo_renamer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {

	/** A image manager for the user */
	private ImageManager im;

	/** A tag manager for the user */
	private TagManager tm;

	/** A record tracking which directory has been opened before */
	private ArrayList<String> historyDir;

	/** A logger for tracking actions performed by user */
	private static final Logger logger = ImageManager.getLogger();

	/**
	 * Creates a new user by reading serialized data of images and tags history
	 * from filePath1 and filePath2. Also reads the history of directory opened
	 * from userPath.
	 * 
	 * @param filePath1
	 *            the file path to the images data
	 * @param filePath2
	 *            the file path to the tags data
	 * @param userPath
	 *            the path to the historical directory data
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public User(String imPath, String tmPath, String userPath) throws ClassNotFoundException, IOException {

		historyDir = new ArrayList<String>();
		this.im = new ImageManager(imPath);
		this.tm = new TagManager(tmPath);

		File file = new File(userPath);
		if (file.exists()) {
			readFromFile(userPath);
		} else {
			file.createNewFile();
		}
	}

	/**
	 * Populates historyDir from the file at path filePath.
	 * 
	 * @param filePath
	 *            the filePath of the data file
	 * @throws ClassNotFoundException
	 */
	private void readFromFile(String filePath) throws ClassNotFoundException {
		try {
			InputStream file = new FileInputStream(filePath);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			historyDir = (ArrayList<String>) input.readObject();
			input.close();
		} catch (IOException ex) {
			logger.log(Level.SEVERE, "Cannot read from input.", ex);
		}
	}

	/**
	 * Serializes class instance and saves it to file path.
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	public void saveToFile(String filePath) throws IOException {
		OutputStream file = new FileOutputStream(filePath);
		OutputStream buffer = new BufferedOutputStream(file);
		ObjectOutput output = new ObjectOutputStream(buffer);

		// serialize the Map
		output.writeObject(historyDir);
		output.close();
	}

	/**
	 * Saves ImageManager and TagManager to the file at path filePath1 and
	 * filePath2 respectively.
	 * 
	 * @param filePath1
	 *            the path ImageManager is saved to
	 * @param filePath2
	 *            the path TagManager is saved to
	 * @throws IOException
	 */
	public void saveIMTM(String filePath1, String filePath2) throws IOException {
		im.saveToFile(filePath1);
		tm.saveToFile(filePath2);
	}

	/**
	 * Adds an image to ImageList for tracking.
	 * 
	 * @param image
	 *            the image to be added to imageList
	 */
	public void addToImageList(Image image) {
		im.addToImageList(image);
	}

	/**
	 * Selects an image from list of images in GUI.
	 * 
	 * @param image
	 *            the image user selects
	 */
	public void selectImage(Image image) {
		if (im.getImageList().contains(image)) {
			im.setSelectedImage(image);
		}
	}

	/**
	 * Adds a user edited tag to available tag list so user can select this new
	 * tag from tag list and add it to photos.
	 *
	 * @param t
	 *            an tag that will be added to available tag list
	 * @see tagImage
	 */
	public void addTag(String t) {
		tm.addTag(t);
	}

	/**
	 * Deletes a tag from available tag list if this tag is not used in any
	 * image, otherwise returns a warning, and lists all the photos with this
	 * tag.
	 *
	 * @param t
	 *            the tag user want to delete from available tag list
	 */
	public void deleteTag(String t) {
		tm.deleteTag(t);
	}

	/**
	 * Selects a tag from the list of tags displayed and add it to selectedTags
	 * list.
	 * 
	 * @param t
	 *            the tag being selected
	 */
	public void selectTag(String t) {
		if (tm.getTagList().containsKey(t)) {
			tm.selectTag(t);
		}
	}

	/**
	 * Selects one tag from the list of tags displayed and remove it from the
	 * list of selected tags.
	 * 
	 * @param t
	 *            the tag being unselected
	 */
	public void unselectTag(String t) {
		if (tm.getSelectedTags().contains(t)) {
			tm.removeSelectedTags(t);
		}
	}

	/**
	 * Adds newly opened directory path to history for tracking.
	 * 
	 * @param dirPath
	 *            the path of the directory
	 */
	public void addDir(String dirPath) {
		this.historyDir.add(dirPath);
	}

	/**
	 * Adds a tag to selected image's filename. Also calls
	 * ImageMnager,TagManager to update tags count.
	 *
	 * @param image
	 *            the name of image user wants to add tag to
	 * @param t
	 *            the tag that will be added to the image selected
	 * @see untageImage
	 */
	private void tagImage(Image image, String t) {
		if (tm.getTagList().containsKey(t)) {
			tm.getTagList().put(t, tm.getTagList().get(t) + 1);
			image.addTag(t);
		}
	}

	/**
	 * Add user selected tags to selected image.
	 */
	public void tagSelectedImage() {
		String oldname = im.getSelectedImage().getName();
		for (String tag : tm.getSelectedTags()) {
			tagImage(im.getSelectedImage(), tag);
		}
		String newname = im.getSelectedImage().getName();
		File file = new File(im.getSelectedImage().getParent().getPath() + "/" + oldname);
		File newfile = new File(im.getSelectedImage().getParent().getPath() + "/" + newname);
		file.renameTo(newfile);
		logger.info("oldname: " + oldname + " newname: " + newname);
	}

	/**
	 * Remove a tag from an image's name, if image does not have that tag,
	 * return an error, also call image manager to update tag count.
	 *
	 * @param image
	 *            name of the image
	 * @param t
	 *            the tag that will be removed from image name
	 * @see tagImage
	 */
	private void untagImage(Image image, String t) {
		HashMap<String, Integer> tagList = tm.getTagList();
		if (tagList.containsKey(t)) {
			if (tagList.get(t) > 0) {
				tagList.put(t, tagList.get(t) - 1);
			}
			image.untag(t);
		}
	}

	/**
	 * Removes selected tags from selected images, note that tags can only come
	 * from the current tags attached to the selected image.
	 */
	public void untagSelectedImage() {
		String oldname = im.getSelectedImage().getName();
		for (String tag : tm.getSelectedTags()) { // ??
			untagImage(im.getSelectedImage(), tag);
		}

		String newname = im.getSelectedImage().getName();
		File file = new File(im.getSelectedImage().getParent().getPath() + "/" + oldname);
		File newfile = new File(im.getSelectedImage().getParent().getPath() + "/" + newname);
		file.renameTo(newfile);
		logger.info("oldname: " + oldname + " newname: " + newname);
	}

	/**
	 * Calls system search function to search any image with name under current
	 * directory, return a message if nothing is found
	 *
	 * @param name
	 *            the name of image user wants to search
	 * @return a list of paths leading to images with the name or null
	 */
	public void searchImage(String name) {
		im.searchImage(name);
		// display this result in GUI, a pop-up window
	}

	/**
	 * Returns a string array representation of original names for the list of
	 * images in the directory
	 * 
	 * @return a string array of original names for the images
	 */
	public String[] viewOriginalNames() {
		return im.getOriginalNames();
	}

	/**
	 * Returns the number of times a tag has been used to tag images.
	 * 
	 * @return number of times a tag has been used.
	 */
	public Integer getCount(String tag) {
		return tm.getCount(tag);
	}

	/**
	 * Returns the number of times all tags have been used to tag images.
	 * 
	 * @return number of times all tags are used.
	 */
	public String[] getAllTagCount() {
		return tm.getAllTagCount();
	}

	/**
	 * Returns history of all previous tagging, untagging,etc activities made on
	 * image including change To be implemented: also include date/time
	 * information
	 * 
	 * @param image
	 *            image that user wants to see history
	 * @return a list of all changes made with date/time
	 */
	public String[] showNameHistory(Image image) {
		return image.showNameHistory();
	}

	/**
	 * Restores an image's name to its previous name Note that name can only
	 * come from nameHistory
	 *
	 * @param image
	 *            the image to be restored
	 * @param date
	 *            the date image's name will be restored to
	 * @see restoreToDate
	 */
	public void restoreToName(Image image, String name) {
		String oldname = image.getName();
		String newname = name;
		for (String tag : image.getTags()) {
			tm.subtractCount(tag);
			// Subtract all old tag's count by 1
		}
		image.restoreToName(name);
		for (String tag : image.getTags()) {
			tm.addCount(tag);
			// add all new tag's count by 1
		}
		File file = new File(im.getSelectedImage().getParent().getPath() + "/" + oldname);
		File newfile = new File(im.getSelectedImage().getParent().getPath() + "/" + newname);
		file.renameTo(newfile);

		logger.info("oldname: " + oldname + " newname: " + newname);
	}

	/**
	 * Returns the contents of the logger tracking image file name changing
	 * actions of the user.
	 * 
	 * @return a string array containing the contents of the logging history
	 * @throws FileNotFoundException
	 */
	public String[] viewLog() throws FileNotFoundException {
		StringBuffer buffer = new StringBuffer("");
		String filePath = im.getLoggerFilePath();
		Scanner scanner;
		scanner = new Scanner(new FileInputStream(filePath));
		while (scanner.hasNextLine()) {
			buffer.append(scanner.nextLine());
			buffer.append("\n");
		}
		scanner.close();
		return buffer.toString().split("\n");
	}
	
	public ArrayList<String> getPaths() {
		return im.getPaths();
	}
	
	public Image findImageWithPath(String path) {
		return im.findImageWithPath(path);
	}
	
//	// for demo:
//	public String[] showImageList() {
//		return im.showImageList();
//	}

	// Main Methods for A2 Demo (Will Paste Later):

}
