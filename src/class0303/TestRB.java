package class0303;

//import class0302.DrawTree;

/**
 * @Description 3.3.21
 *      test 3.2.10
 * @author Leon
 * @date 2017-07-13 10:29:13
 */
public class TestRB {

    public static void main(String[] args) {
        /*
        DrawTree<String, String> dt = new DrawTree<String, String>();
        dt.put("E", "1");
        dt.put("A", "2");
        dt.put("S", "3");
        dt.put("Y", "4");
        dt.put("Q", "5");
        dt.put("U", "6");
        dt.put("T", "7");
        dt.put("I", "8");
        dt.put("O", "9");
        dt.put("N", "0");
        
        dt.draw();
        */
        
        DrawRedBlack<String, String> drb = new DrawRedBlack<String, String>();
        drb.put("E", "1");
        drb.put("A", "2");
        drb.put("S", "3");
        drb.put("Y", "4");
        drb.put("Q", "5");
        drb.put("U", "6");
        drb.put("T", "7");
        drb.put("I", "8");
        drb.put("O", "9");
        drb.put("N", "0");
        drb.draw();
        
    }

}
