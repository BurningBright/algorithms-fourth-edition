package class0202;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * describe: 
 * find a sorted subarray 
 * ps:by incrementing a pointer until finding 
 * 		an entry that is smaller than its predecessor 
 * 		in the array
 * then find the next.
 * date:	 2015年5月26日 上午10:23:52
 * @author:	 Chen
 *
 */
public class NaturalMergeSort {
	
	/**
	 * initial data resource
	 * @param src
	 */
	public static void sort(Comparable<Object>[] src) {
		
		List<ArrayList<Comparable<Object>>> tmp = new ArrayList<ArrayList<Comparable<Object>>>();
		
		for(int i=0; i<src.length; i++) {
			ArrayList<Comparable<Object>> inner_tmp = new ArrayList<Comparable<Object>>();
			inner_tmp.add(src[i]);
			tmp.add(inner_tmp);
		}
		
		sort(tmp, 0);
		
		assert tmp.size()==1;
		
		System.out.println(tmp.get(0).toString());
	}
	
	/**
	 * this method will recursively 
	 * solve the merge problem
	 * @param src		resource
	 * @param current	current index
	 * @param cursor	cursor index
	 */
	private static void sort(List<ArrayList<Comparable<Object>>> src, int current) {
		int size = src.size();
		
		if(current >= size-1) {
			if(src.size() != 1) {
				sort(src, 0);
			}
			return;
		}
		
		/* find left one */
		finder(src, current);
		ArrayList<Comparable<Object>> left = src.get(current);
		if(current == src.size()-1) return;		//prevent out range
		
		/* find right one */
		finder(src, current+1);
		ArrayList<Comparable<Object>> right = src.get(current+1);
		if(current == src.size()-1) return;
		
		/* merge them */
		ArrayList<Comparable<Object>> tmp = new ArrayList<Comparable<Object>>();
		int l_size = left.size();
		int r_size = right.size();
		for(int i=0,j=0; i<l_size || j<r_size;) {
			if(left.isEmpty()) {
				tmp.add(right.remove(0));
				j++;
			} else if(right.isEmpty()) {
				tmp.add(left.remove(0));
				i++;
			} else if(less(left.get(0), right.get(0))) {
				tmp.add(left.remove(0));
				i++;
			} else {
				tmp.add(right.remove(0));
				j++;
			}
		}
		src.remove(current+1);		//remove the right part
		src.set(current, tmp);		//set the new part
		
		/* recursive */
		sort(src, current+1);
	}
	
	private static void finder(List<ArrayList<Comparable<Object>>> src, int begin) {
		//key point
		if(begin >= src.size()-1) {
			return;
		}
		
		//find beginner
		ArrayList<Comparable<Object>> beginner = src.get(begin);
		//find beginner's last element
		Comparable<Object> flag = beginner.get(beginner.size()-1);
		
		//for(int i=begin+1; i<src.size();) {}
		
		if(less(flag, src.get(begin+1).get(0))) {
			for(Comparable<Object> e : src.get(begin+1)) {
				beginner.add(e);
			}
			src.remove(begin+1);
			finder(src, begin);
		} else {
			return;
		}
		
		return;
	}
	
	public static boolean less(Comparable<Object> v, Comparable<Object> w) {
		return v.compareTo(w) < 0;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String in = "jfoqiaewgpoajidnebvfpoaueihro90qa";
		Comparable<Object>[] src = new Comparable[in.length()];
		
		for(int i=0; i<in.length(); i++) {
			src[i] = (Comparable<Object>) in.subSequence(i, i+1);
		}
		
		sort(src);
		
		//System.out.println(Arrays.toString(src));
	}

}
