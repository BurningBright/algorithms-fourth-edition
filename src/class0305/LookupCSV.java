package class0305;

import java.util.ArrayList;
import java.util.List;

import rlgs4.ST;
import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

/**
 * @Description 3.5.12 遍历CSV
 * @author Leon
 * @date 2017-10-17 17:04:00
 */
public class LookupCSV {

    public static void main(String[] args) {
        In in = new In(args[0]);
        int keyField = Integer.parseInt(args[1]);
        int valField = Integer.parseInt(args[2]);
        ST<String, List<String>> st = new ST<String, List<String>>();
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] tokens = line.split(",");
            String key = tokens[keyField];
            String val = tokens[valField];
            if (st.contains(key)) {
                if (!st.get("st").contains(val))
                    st.get("st").add(val);
            } else {
                List<String> tmp = new ArrayList<String>();
                tmp.add(val);
                st.put(key, tmp);
            }
        }
        StdOut.println("Ready");
        while (!StdIn.isEmpty()) {
            String query = StdIn.readString();
            if ("exit".equalsIgnoreCase(query) || "quit".equalsIgnoreCase(query))
                break;
            if (st.contains(query))
                StdOut.println(st.get(query).toString());
        }
    }

}
