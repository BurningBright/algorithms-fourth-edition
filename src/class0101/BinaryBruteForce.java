package class0101;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
/**
 * 1.1.38
 * use PrepareVSData create largeTAR.txt
 * @author soft01
 *
 */
public class BinaryBruteForce {
	
	public static int[] src = null;
	
	public static void BruteForce() {
		int count = 0;
		File tFile = new File("1.1/largeTAR.txt");
		BufferedReader br = null; 
		
		try{
			br = new BufferedReader(
					new InputStreamReader(
						new FileInputStream(tFile)));
			String tmpStr = "";
			
			while((tmpStr=br.readLine()) != null) {
				int current = Integer.valueOf(tmpStr);
				for(int i=0; i<src.length; i++) {
					if(src[i] == current) {
						count++;
						break;
					}
				}
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(count+" matchs");
	}
	
	public static void Binary() {
		Arrays.sort(src);
//		System.out.println(Arrays.toString(src));
		
		int count = 0;
		File tFile = new File("1.1/largeTAR.txt");
		BufferedReader br = null; 
		
		try{
			br = new BufferedReader(
					new InputStreamReader(
						new FileInputStream(tFile)));
			String tmpStr = "";
			
			while((tmpStr=br.readLine()) != null) {
				int current = Integer.valueOf(tmpStr);
//				int picket = -10;
//				if((picket=rank(current, src)) > -1) {
				if(rank(current, src) > -1) {
					count++;
//					System.out.print(current);
				}
//				System.out.println("  "+picket);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(count+" matchs");
		
	}
	
	public static int rank(int key, int[] a) {
		// Array must be sorted.
		int lo = 0;
		int hi = a.length - 1;
		while (lo <= hi) {
			// Key is in a[lo..hi] or not present.
			int mid = lo +(hi - lo) / 2;
//			if(key==55026) {
//				System.out.println(lo+" "+mid+" "+hi);
//			}
			if (key < a[mid]) {
				hi = mid - 1;
			}else if (key > a[mid]) {
				lo = mid + 1;
			}else return mid;
		}
		return -1;
	}
	
	public static void main(String[] args) {
		int N = 1000000;
		
		src = new int[N];
		BufferedReader br = null; 
		File sFile = new File("1.1/largeSRC.txt");
		try{
			br = new BufferedReader(
					new InputStreamReader(
						new FileInputStream(sFile)));
			String tmpStr = "";
			int index = 0;
			while((tmpStr=br.readLine()) != null) {
				src[index] = Integer.parseInt(tmpStr);
				index++;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		long start = System.currentTimeMillis();
//		BruteForce();
		Binary();
		long end = System.currentTimeMillis();
		System.out.println(end-start+" mm");
	}

}
