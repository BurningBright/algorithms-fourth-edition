package class0103;

import java.io.File;
import java.util.Arrays;

/**
 * A folder is a list of files and folders
 * @author soft01
 *
 */
public class ListingFiles {
	private static GeneralizedAQueue<String> gaq;
	
	/**
	 * prepare data for enqueue
	 * @param root
	 */
	public static void listingFiles(String root) {
		gaq = new GeneralizedAQueue<String>();
		
		File src = new File(root);
		gaq.insert(src.getName());
//		StdOut.println(gaq.toString());
		filesEnqueue(src, "  |-");
	}
	
	/**
	 * this function will put all files
	 *  name in queue be sorted and indented
	 * @param s
	 */
	private static void filesEnqueue(File src, String indent) {
	
		File[] fs = src.listFiles();
		Arrays.sort(fs);
		for(File f: fs) {
			if(f.isFile()) {
				gaq.insert(indent+f.getName());
			}else if(f.isDirectory()) {
				gaq.insert(indent+f.getName());
				filesEnqueue(f, "  "+indent);
			}
		}
	}
	
	public static void printQueue() {
		for(String s: gaq) {
			System.out.println(s);
		}
	}
	
	public static void main(String[] args) {
		ListingFiles.listingFiles("./");
		ListingFiles.printQueue();
	}

}
