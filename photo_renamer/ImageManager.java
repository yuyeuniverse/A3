package photo_renamer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.awt.Desktop;

public class ImageManager {

	/** A list of image nodes */
	private ArrayList<Image> imageList;

	/** The current image being selected */
	private Image selectedImage;

	/** A logger for the ImageManager tracking actions performed by user */
	private static final Logger logger = Logger.getLogger(ImageManager.class.getName());

	/** A console handler for the logger */
	private static Handler fileHandler = null;

	/** The file path to the logger history file */
	private final String loggerPath = "/Users/zikunchen/Desktop/TEST/logger.txt";

	/**
	 * Creates a new ImageManager according to serialized data at filePath with
	 * a logger to track user actions.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public ImageManager(String filePath) throws ClassNotFoundException, IOException {

		this.imageList = (new ArrayList<Image>());

		// Associate the handler with the logger.
		fileHandler = new FileHandler(loggerPath, true);
		SimpleFormatter formatter = new SimpleFormatter();
		fileHandler.setFormatter(formatter);
		logger.setLevel(Level.ALL);
		fileHandler.setLevel(Level.ALL);
		logger.addHandler(fileHandler);

		// Reads serializable objects from file.
		// Populates the record list using stored data, if it exists.
		File file = new File(filePath);
		if (file.exists()) {
			readFromFile(filePath);
		} else {
			file.createNewFile();
		}
	}

	/**
	 * Populates imageList and tagList from the file at path filePath.
	 * 
	 * @param filePath
	 *            the path of the data file
	 * @throws ClassNotFoundException
	 *             if filePath is not a valid path
	 */
	private void readFromFile(String path) throws ClassNotFoundException {
		try {
			InputStream file = new FileInputStream(path);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			imageList = (ArrayList<Image>) input.readObject();
			input.close();
		} catch (IOException ex) {
			logger.log(Level.SEVERE, "Cannot read from input.", ex);
		}
	}

	/**
	 * Saves imageList to the file at path filePath.
	 * 
	 * @param filePath
	 *            the path of the data file
	 * @throws IOException
	 *             if filePath is not a valid path
	 */
	public void saveToFile(String filePath) throws IOException {
		OutputStream file = new FileOutputStream(filePath);
		OutputStream buffer = new BufferedOutputStream(file);
		ObjectOutput output = new ObjectOutputStream(buffer);
		output.writeObject(imageList);
		output.close();
	}

	/**
	 * Return the currently selected image for this ImageManager.
	 * 
	 * @return the selected image
	 */
	public Image getSelectedImage() {
		return selectedImage;
	}

	/**
	 * Set the currently selected image for this ImageManager.
	 * 
	 * @param selectedImage
	 *            the selected image to set
	 */
	public void setSelectedImage(Image selectedImage) {
		this.selectedImage = selectedImage;
	}

	/**
	 * Return the list of images the manager is currently tracking.
	 * 
	 * @return list of images
	 */
	public ArrayList<Image> getImageList() {
		return imageList;
	}

	/**
	 * Add an image to ImageList for tracking
	 * 
	 * @param image
	 *            the image to be added to imageList
	 */
	public void addToImageList(Image image) {
		if (!imageList.contains(image)) {
			this.imageList.add(image);
		} else {
			System.out.println("image already exists!");
		}
	}

	/**
	 * Returns a string array representation of original names for the images in
	 * the image list
	 * 
	 * @return a string array of original names for the images
	 */
	public String[] getOriginalNames() {
		StringBuffer buffer = new StringBuffer("");
		for (Image image : imageList) {
			image.getOriginal();
		}
		return buffer.toString().split("\n");
	}

	/**
	 * Searches for the images called name (Will be displayed in GUI)
	 * 
	 * @param name
	 *            the name of the image to search for
	 * @return list of images
	 */
	public ArrayList<Image> searchImage(String name) {
		ArrayList<Image> toReturn = new ArrayList<Image>();
		for (Image image : getImageList()) {
			if (image.getName() == name) {
				toReturn.add(image);
			}
		}
		return toReturn;
	}

	/**
	 * Returns Image chosen from an array of images by the path selected by
	 * user.
	 * 
	 * @return the image chosen
	 */
	public void chooseImage(ArrayList<Image> images, String path) {
		for (Image image : images) {
			if (image.getPath() == path) {
				this.selectedImage = image;
			}
		}
	}

	/**
	 * Opens (in the OS) the directory containing the selected image
	 * 
	 * @param image
	 *            image that user wants to open directory in.
	 */
	public void openDirectory() throws IOException {
		Desktop desktop = Desktop.getDesktop();
		try {
			String parentDir = selectedImage.getPath();
			File fileToOpen = new File(parentDir);
			desktop.open(fileToOpen);
		} catch (IllegalArgumentException iae) {
			System.out.println("File Not Found");
		}
	}

	public ArrayList<String> getPaths() {
		ArrayList<String> paths = new ArrayList<String>();
		for (Image i : imageList) {
			paths.add(i.getPath());
		}
		return paths;
	}

	public Image findImageWithPath(String path) {
		for (Image i : imageList) {
			if (i.getPath() == path) {
				return i;
			}
		}
		return null;
	}

	/**
	 * Returns the logger for this ImageManager.
	 * 
	 * @return the logger for this ImageManager
	 */
	public static Logger getLogger() {
		return logger;
	}

	/**
	 * Returns the file path to the logger history file for this ImageManager.
	 * 
	 * @return the file path to the logger history file for this ImageManager
	 */
	public String getLoggerFilePath() {
		return loggerPath;
	}

	/**
	 * Returns all tags that two images have in common
	 * 
	 * @return a list of the commonTags
	 */
	public ArrayList<String> getCommonTags(Image image1, Image image2) {
		ArrayList<String> common = new ArrayList<String>(image1.getTags());
		common.retainAll(image2.getTags());
		return common;
	}

	// //for demo:
	// public String[] showImageList() {
	// String result = "";
	// for (Image i: imageList) {
	// result += i.toString();
	// result += "\n";
	// }
	// return result.split("\n");
	// }

}
