package photo_renamer;

import java.io.File;
import java.io.IOException;

public class test2 {
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		String filePath1 = "/Users/zikunchen/Desktop/TEST/im.ser";
		String filePath2 = "/Users/zikunchen/Desktop/TEST/tm.ser";
		String filePath3 = "/Users/zikunchen/Desktop/TEST/user.ser";
		User user = new User(filePath1, filePath2, filePath3);

		File file = new File("/Users/zikunchen/Desktop/TEST/");
		user.addDir("/Users/zikunchen/Desktop/TEST/");
		user.saveToFile(filePath3);
		Directory TEST = new Directory("TEST", null, file.getAbsolutePath());
		TEST.buildImageTree(file);

		// Showing list of images in file
		StringBuffer contents = new StringBuffer();
		String[] list = TEST.buildListOfImages(contents);
		for (String s : list) {
			System.out.println(s);
		}

		// Showing adding tag and delete tag in action
		Image image1 = (Image) TEST.findChild("third.jpg");

		// Add tag @Samantha to image1

		// Show tag counts
		System.out.println("Before Everything");
		for (String s : user.getAllTagCount()) {
			System.out.println(s);
		}

		user.addToImageList(image1);
		user.selectImage(image1);

		user.addTag("Samantha");
		user.selectTag("Samantha");

		// Show tag counts
		System.out.println("Added New Tag, Before Tag Image");
		System.out.println(image1.getTags());
		for (String s : user.getAllTagCount()) {
			System.out.println(s);
		}

		user.tagSelectedImage();

		System.out.println("After Tag");
		System.out.println(image1.getTags());

		// Show tag counts
		for (String s : user.getAllTagCount()) {
			System.out.println(s);
		}

		user.saveIMTM(filePath1, filePath2);

		// Show image1 and tag @Samantha already exist and selected

		user.addToImageList(image1);
		user.addTag("Samantha");
		user.selectTag("Samantha");

		// Remove tag @Samantha from image1
		user.untagSelectedImage();

		System.out.println("After Untag");

		System.out.println(image1.getTags());
		// Show tag counts
		for (String s : user.getAllTagCount()) {
			System.out.println(s);
		}

		user.saveIMTM(filePath1, filePath2);

		// Revert image1 to third@Samantha.jpg
		user.restoreToName(image1, "third@Samantha.jpg");

		System.out.println("After Restore");

		System.out.println(image1.getTags());
		// Show tag counts
		for (String s : user.getAllTagCount()) {
			System.out.println(s);
		}

		// Restore Again to third.jpg
		user.restoreToName(image1, "third.jpg");
		
		System.out.println("After Restore Again");

		System.out.println(image1.getTags());
		// Show tag counts
		for (String s : user.getAllTagCount()) {
			System.out.println(s);
		}
		
		// Show nameHistory of image1
		for (String x : image1.showNameHistory()) {
			System.out.println(x);
		}

		// User can view Log
		for (String s : user.viewLog()) {
			System.out.println(s);
		}

		user.saveIMTM(filePath1, filePath2);
	}
}
