package record;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rlgs4.Queue;
import rlgs4.ResizingArrayBag;

/**
 * sort record by number or alphabet
 * @author soft01
 */
public class SortRecord {
	
	private class Record implements Comparable<Record>{
		
		int number;
		String record;
		
		public Record(int a, String b) {
			number = a;
			record = b;
		}
		@Override
		public int compareTo(Record o) {
			return this.number-o.number;
		}
		
	}
	
	public void work(File src) {
		BufferedReader br = null;
		Queue<String> que = new Queue<String>();
		ResizingArrayBag<Record> al = new ResizingArrayBag<Record>();
		
		try{
			br = new BufferedReader(
					new InputStreamReader(
						new FileInputStream(src)));
			String current;
			while((current=br.readLine()) != null) {
				if(current.startsWith("\t")) {
					Pattern pat = Pattern.compile("(?<=[\\d+][\\.][\\d+][\\.])\\d+");
					Matcher mat = pat.matcher(current);
					int number;
					if(mat.find()) {
						number = Integer.parseInt(mat.group());
					}else{
						throw new RuntimeException();
					}
					al.add(new Record(number, current));
					continue;
				}
				if(current.startsWith("class") && al.isEmpty()) {
					que.enqueue(current);
				}else{
					Record[] tmp = new Record[al.size()];
					int i = 0;
					for(Record r: al) {
						tmp[i] = r;
						i++;
					}
					al = new ResizingArrayBag<Record>();
					Arrays.sort(tmp);
					for(Record r: tmp) {
						que.enqueue(r.record);
					}
					que.enqueue(current);
				}
			}
			if(!al.isEmpty()) {
				Record[] tmp = new Record[al.size()];
				int i = 0;
				for(Record r: al) {
					tmp[i] = r;
					i++;
				}
				al = new ResizingArrayBag<Record>();
				Arrays.sort(tmp);
				for(Record r: tmp) {
					que.enqueue(r.record);
				}
			}
			while(!que.isEmpty()) {
				System.out.println(que.dequeue());
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(br!=null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		File src = new File("src/record/record.txt");
		new SortRecord().work(src);
	}
}
