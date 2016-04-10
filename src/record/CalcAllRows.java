package record;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * calculate the hole code i have written
 * @author soft01
 *
 */
public class CalcAllRows {

	public static int sumRow = 0;
	public static int sumChar = 0;
	
	public static int calcAll(File current) {
		int curRow = 0;
		if (current.isDirectory()) {
			File[] curFileList = current.listFiles();
			for(File i: curFileList) {
				curRow = curRow +calcAll(i);
			}
			System.out.println(current.getName()+" have : "+curRow);
		} else {
			try {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(
						new FileInputStream(current)));
				String tRow = null;
				while((tRow = br.readLine()) != null) {
					if(!"".equals(tRow)){
						sumRow++;
						curRow++;
					}
				}
				br.close();
				
				InputStreamReader isr =new InputStreamReader(
						new FileInputStream(current), "UTF-8");
				
				while(isr.read() != -1) {
					sumChar++;
				}
				isr.close();
				
			} catch (IOException e) {
				System.out.println("an exception appear");
			}
		}
		return curRow;
	}
	
	public static void main(String[] args) {
		calcAll(new File("."+File.separator+"src"+File.separator+"class0105"));
		System.out.println("sum row is : "+sumRow);
		System.out.println("sum char is : "+sumChar);
	}
}
