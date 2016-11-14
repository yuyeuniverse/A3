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
import java.util.HashMap;

public class TagManager {

	/**
	 * A mapping of tags to how many times they have been currently attached to
	 * an images.
	 */
	private HashMap<String, Integer> tagList;

	/** The current tags being selected */
	private ArrayList<String> selectedTags;

	/**
	 * Creates a new TagManager according to serialized data at filePath.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public TagManager(String filePath) throws ClassNotFoundException, IOException {
		tagList = new HashMap<String, Integer>();
		selectedTags = new ArrayList<String>();
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
	 * Populates tagList from the file at path filePath.
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

			tagList = (HashMap<String, Integer>) input.readObject();
			input.close();
		} catch (IOException ex) {
			System.out.println("Cannot read from input.");
		}
	}

	/**
	 * Saves tagList to the file at path filePath.
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

		output.writeObject(tagList);
		output.close();
	}

	/**
	 * Returns selected tags in this TagManager.
	 * 
	 * @return selected tags in this TagManager
	 */
	public ArrayList<String> getSelectedTags() {
		return selectedTags;
	}

	/**
	 * Adds a tag to the list of selected tags.
	 * 
	 * @param t
	 *            tag to be added
	 */
	public void selectTag(String t) {
		if (!selectedTags.contains(t)) {
			this.selectedTags.add(t);
		} else {
			System.out.println("Tag already selected!");
		}
	}

	/**
	 * Removes a tag from the list of selected tags.
	 * 
	 * @param t
	 *            tag to be remove
	 */
	public void removeSelectedTags(String t) {
		if (selectedTags.contains(t)) {
			this.selectedTags.remove(t);
		} else {
			System.out.println("Tag not selected!");
		}
	}

	/**
	 * Returns the tag list for this TagManager.
	 * 
	 * @return tag list for this TagManager
	 */
	public HashMap<String, Integer> getTagList() {
		return tagList;
	}

	/**
	 * Returns the number of times a tag has been used to tag images.
	 * 
	 * @return number of times a tag has been used.
	 */
	public Integer getCount(String tag) {
		return tagList.get(tag);
	}

	/**
	 * Returns the number of times all tags have been used to tag images.
	 * 
	 * @return number of times all tags are used.
	 */
	public String[] getAllTagCount() {
		String result = "";
		for (String tag : this.getTagList().keySet()) {
			result += tag;
			result += " ";
			result += getCount(tag);
			result += "\n";
		}
		return result.split("\n");
	}

	/**
	 * Adds a new tag to tagList in this TagManager.
	 * 
	 * @param t
	 *            the new tag to be added
	 */
	public void addTag(String t) {
		if (!tagList.containsKey(t)) {
			tagList.put(t, 0);
		} else {
			System.out.print("Tag already exists!");
		}
	}

	/**
	 * Deletes a tag from tagList in this TagManager. Note: This method only
	 * takes tags from selectedTags
	 * 
	 * @param t
	 *            the tag to be deleted
	 */
	public void deleteTag(String t) {
		if (tagList.containsKey(t)) {
			if (tagList.get(t) == 0) {
				tagList.remove(t);
			} else {
				System.out.println("There are images with this tag!" + "Please untag them before deleting tag.");
			}
		} else {
			System.out.print("Tag does not exist!");
		}
	}

	/**
	 * Add a count for the tag t in the tagList in this TagManager.
	 * 
	 * @param t
	 *            the tag to be counted
	 */
	public void addCount(String t) {
		if (tagList.containsKey(t)) {
			Integer previousCount = tagList.get(t);
			tagList.put(t, previousCount + 1);
		} else {
			System.out.print("Tag does not exist!");
		}
	}

	/**
	 * Subtract a count for the tag t in the tagList in this TagManager.
	 * 
	 * @param t
	 *            the tag whose count to be subtracted
	 */
	public void subtractCount(String t) {
		if (tagList.containsKey(t)) {
			if (tagList.get(t) > 0) {
				Integer previousCount = tagList.get(t);
				tagList.put(t, previousCount - 1);
			} else {
				System.out.print("Tag count is already zero!");
			}
		} else {
			System.out.print("Tag does not exist!");
		}
	}

}
