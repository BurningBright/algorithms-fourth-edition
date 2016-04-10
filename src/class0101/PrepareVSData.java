package class0101;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Binary search versus brute-force search
 * Data prepare
 * 
 * largeSRC.txt	 source data file
 * largeTAR.txt	 target data file
 * 
 * goal: find these data not in largeSRC from largeTAR;
 * @author soft01
 *
 */
public class PrepareVSData {
	
	public static Random ran = new Random();
	
	public static String create() {
		int a = (int) (ran.nextDouble()*990000+10000);
		return String.valueOf(a);
	}
	
	public static void main(String[] args) {
//		String fileName = "largeSRC.txt";
//		int count = 1000000;
		String fileName = "largeTAR.txt";
		int count = 500000;
		
		long start = System.currentTimeMillis();
		
		PrintWriter pw = null;
		try{
			File f = new File(fileName);
			f.createNewFile();
			if(f.exists()) {
				pw = new PrintWriter(
						new OutputStreamWriter(
								new FileOutputStream(f)), true);
				for(int i=0; i<count; i++) {
					pw.println(create());
				}
			}else{
				System.out.println("not find");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			pw.close();
		}
		
		long end = System.currentTimeMillis();
		System.out.println("End cost:  "+(end-start));
	}

}
