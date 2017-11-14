package class0401;

/**
 * @Description 4.1.30
 *          欧拉环和汉密尔顿环
 *          2 - E[]
 *          4 - H[0 5 4 1 6 3 7 9 8 2 0]
 * @author Leon
 * @date 2017-11-10 09:15:00
 */

public class CyclesCheck {
    
    public static void draw(int[] src) {
        StdDraw.setScale(0, 10);
        int R = 3;
        int centerX = 5;
        int centerY = 5;
        double[] drawX = new double[10];
        double[] drawY = new double[10];
        
        for (int i=0; i<10; i++) {
            double offsetX = Math.cos(2 * Math.PI * i / 10) * R;
            double offsetY = Math.sin(2 * Math.PI * i / 10) * R;
            drawX[i] = centerX + offsetX;
            drawY[i] = centerY + offsetY;
            StdDraw.filledCircle(drawX[i], drawY[i], .05);
            
            double offsetXX = Math.cos(2 * Math.PI * i / 10) * (R+1);
            double offsetYY = Math.sin(2 * Math.PI * i / 10) * (R+1);
            StdDraw.text(centerX + offsetXX, centerY + offsetYY, i+"", 2 * Math.PI * i / 10);
        }
        
        for (int i=0; i<src.length; i+=2) 
            StdDraw.line(drawX[src[i]], drawY[src[i]], drawX[src[i+1]], drawY[src[i+1]]);
        
    }
    
    public static void main(String[] args) {
        int[][] a = {{0, 1, 0, 2, 0, 3, 1, 3, 1, 4, 2, 5, 2, 9, 3, 6, 4, 7, 4, 8, 5, 8, 5, 9, 6, 7, 6, 9, 7, 8},
                    {0, 1, 0, 2, 0, 3, 1, 3, 0, 3, 2, 5, 5, 6, 3, 6, 4, 7, 4, 8, 5, 8, 5, 9, 6, 7, 6, 9, 8, 8},
                    {0, 1, 1, 2, 1, 3, 0, 3, 0, 4, 2, 5, 2, 9, 3, 6, 4, 7, 4, 8, 5, 8, 5, 9, 6, 7, 6, 9, 7, 8},
                    {4, 1, 7, 9, 6, 2, 7, 3, 5, 0, 0, 2, 0, 8, 1, 6, 3, 9, 6, 3, 2, 8, 1, 5, 9, 8, 4, 5, 4, 7}};
        
        draw(a[3]);
        
    }

}
