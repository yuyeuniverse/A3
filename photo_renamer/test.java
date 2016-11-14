package photo_renamer;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class test {
//	/** A logger for the ImageManager tracking actions performed by user */
//	private static final Logger logger = Logger.getLogger(ImageManager.class.getName());
//
//	/** A console handler for the logger */
//	private static Handler fileHandler = null;
//	
//	/** A formatter for the logger */
//	private static SimpleFormatter formatter = null;
	
	
	
	public static void main(String[] args) throws SecurityException, IOException {
		
		//ImageManager im = new ImageManager();
		//TagManager tm = new TagManager();
		
		File file = new File("/Users/zikunchen/Desktop/TEST");
		Directory TEST = new Directory("TEST", null, file.getAbsolutePath());
		TEST.buildImageTree(file);
		Image image1 = (Image)TEST.findChild("1.jpg");
		image1.addTag("Samantha");
		System.out.println(image1.getName());
		System.out.println(image1.getName().substring(1));
		
//		DateFormat df = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
//		Calendar calobj = Calendar.getInstance();
//		System.out.println(df.format(calobj.getTime()));
		// String filename = "jack.jpg";
		// String[] tokens = filename.split("\\.");
		// System.out.println(Arrays.toString(tokens));
		// for (String s : tokens) {
		// System.out.println(s);
		// }
		// System.out.println(tokens);
		// String extension = tokens[1];
		// System.out.println(tokens.length);
		// System.out.println(extension);

		// Directory d = new Directory("d",null);
		// d.addChild("Image1", new Image("Image1", d));
		// d.addChild("D1", new Directory("D1", d));
		// System.out.println(d.findChild("D1").getName());
		// System.out.println(d.findChild("Image1"));
		// for (FileNode child: d.getChildren()) {
		// System.out.println(child.getName());
		// }
//		 File file = new File("/Users/zikunchen/Desktop/Screen Shot 2016-11-09
//		 at 2.33.03 AM.png");
//		 File newfile = new File("/Users/zikunchen/Desktop/haha.png");
//		 file.renameTo(newfile);
		//
		
//		File file = new File("./Logger.txt");
//		if (file.exists()) {
//			fileHandler = FileHanlder(file);
//		} else {
//			fileHandler = new FileHandler ("./Logger.txt");
//		}
		
		
//		formatter = new SimpleFormatter();
//		fileHandler.setFormatter(formatter);
//		
//	    String yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd").format(new Date(0));
//		
//	    fileHandler = new FileHandler ("./testlogger.txt", true);
//		fileHandler.setFormatter(yyyyMMdd);
//		logger.setLevel(Level.ALL);
//		fileHandler.setLevel(Level.ALL);
//		logger.addHandler(fileHandler);
//		
//		StringBuffer s = new StringBuffer("");
//		s.append("asdfasdfasdf\nasdfasdfasdfde\n");
//		String s1 = s.toString();
//		for (String kk : s1.split("\n")) {
//			logger.info("for loop executed");
//			System.out.println(kk);
//		}
//		fileHandler.close();
		
//		@SuppressWarnings("deprecation")
//		Date date = new Date();
//	    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm:ss zzz");
//	    System.out.println(sdf.format(date));

	}
}
