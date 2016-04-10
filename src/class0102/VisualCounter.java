package class0102;
/**
 * allows both increment and decrement
 * operations	
 * 		N -> maximum number of operations
 * 		max -> specifies the maximum 
 * 				absolute value for the counter
 * @author soft01
 *
 */
public class VisualCounter {
	private int numCount;
	private int oprCount;
	private int maxNum;
	private int oprNum;
	public VisualCounter(int N, int max) {
		this.numCount = 0;
		this.oprCount = 0;
		this.maxNum = max;
		this.oprNum = N;
	}
	
    /**
     * Returns the current count.
     */
	public int tally() {
		return numCount;
    } 
	
    /**
     * Increments the counter by 1.
     */
    	public void increment() {
    		if(oprCount<oprNum) {
    			if(Math.abs(++numCount)>maxNum) {
    				numCount--;
    				return;
    		}
    			oprCount++;
    	}
    }
    
    /**
     * Increments the counter by 1.
     */
    	public void decrement() {
    		if(oprCount<oprNum) {
    			if(Math.abs(--numCount) > maxNum) {
    				numCount++;
    				return;
    		}
    			oprCount++;
    	}
    }
    	
	public static void main(String[] args) {
		VisualCounter vc = new VisualCounter(10, 3);
		vc.decrement();
		System.out.println(vc.maxNum+" "+vc.numCount+" "+vc.oprNum+" "+vc.oprCount);
		vc.decrement();
		System.out.println(vc.maxNum+" "+vc.numCount+" "+vc.oprNum+" "+vc.oprCount);
		vc.decrement();
		System.out.println(vc.maxNum+" "+vc.numCount+" "+vc.oprNum+" "+vc.oprCount);
		vc.decrement();
		System.out.println(vc.maxNum+" "+vc.numCount+" "+vc.oprNum+" "+vc.oprCount);
		vc.increment();
		System.out.println(vc.maxNum+" "+vc.numCount+" "+vc.oprNum+" "+vc.oprCount);
	}

}
