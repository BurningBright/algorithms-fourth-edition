package class0305;

import rlgs4.LinearProbingHashST;
import rlgs4.SET;
import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

/**
 * @Description 3.5.22
 *          CSV全索引
 *          resource/3.5/movies-top-grossing.txt
 * @author Leon
 * @date 2017-10-24 16:15:00
 */
public class FullLookupCSV {
    
    private int n;
    private int INIT = 8;
    private String[] origin = new String[INIT];
    private LinearProbingHashST<String, SET<String>> st = 
            new LinearProbingHashST<String, SET<String>>();
    
    public FullLookupCSV(String csv) {
        In in = new In(csv);
        while(in.hasNextLine()) {
            if(n > origin.length*2/3)
                resize(origin.length * 2);
            
            String line = in.readLine();
            origin[n] = line;
            n++;
        }
        System.out.println("Input Done");
        
        for(int i=0; i<n; i++) {
            String[] result = origin[i].split("/");
            SET<String> set = new SET<String>();
            for(int j=1; j<result.length; j++)
                set.add(result[j]);
            st.put(result[0], set);
        }
    }
    
    private void resize(int m) {
        String[] tmp = new String[m];
        System.arraycopy(origin, 0, tmp, 0, origin.length);
        origin = tmp;
    }
    
    public static void main(String[] args) {
        FullLookupCSV fl = new FullLookupCSV(args[0]);
        
        while(!StdIn.isEmpty()) {
            StdOut.print("Movi :");
            String movi = StdIn.readLine();
            StdOut.print("Actor:");
            String actor = StdIn.readLine();
            
            if(fl.st.contains(movi)) {
                SET<String> set = fl.st.get(movi);
                if(set.contains(actor)) {
                    StdOut.println("Exists");
                } else {
                    StdOut.println("No such actor");
                }
            } else {
                StdOut.println("No such movi");
            }
        }
        
    }

}
