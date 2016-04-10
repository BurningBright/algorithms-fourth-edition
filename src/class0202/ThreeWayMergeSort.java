package class0202;

/**
 * 
 * describe: sort each third, and combine using a 3-way merge
 * date:	 2015年10月14日 上午9:23:38
 * @author:	 Chen
 *
 */
public class ThreeWayMergeSort {
	
	public static void sort(Comparable<Object>[] src) {
		
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String in = "jfoqiaewgpoajidnebvfpoaueihro90qa";
		Comparable<Object>[] src = new Comparable[in.length()];
		
		for(int i=0; i<in.length(); i++) {
			src[i] = (Comparable<Object>) in.subSequence(i, i+1);
		}
		
		sort(src);
	}

}
