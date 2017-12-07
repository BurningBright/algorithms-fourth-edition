package class0402;

import java.awt.Color;
import class0401.StdDraw;

/**
 * @Description 4.2.2
 *          画小有向图
 * @author Leon
 * @date 2017-11-10 09:32:00
 */
public class DrawTinyDG {

    public static void main(String[] args) {
        StdDraw.setScale(0, 10);
        drawArrow(3, 5, 7, 2, .01, StdDraw.GRAY);
    }
    
    public static void drawArrow(double x0, double y0, double x1, double y1,
            double radius, Color color) {
        
        double M1 = 30;
        double M2 = 15;
        
        Color oldCol = StdDraw.getPenColor();
        double oldRad = StdDraw.getPenRadius();
        StdDraw.setPenColor(color);
        StdDraw.setPenRadius(radius);
        
        // 箭头坐标
        double[] triangleX = new double[3];
        double[] triangleY = new double[3];
        
        triangleX[0] = x1;
        triangleY[0] = y1;
        
        // 箭头长度
        double length = Math.sqrt(Math.pow(x1 - x0, 2) + Math.pow(y1 - y0, 2));
        // 箭头与线交界
        double p1x = x1 - (radius * M1)/length * (x1-x0);
        double p1y = y1 - (radius * M1)/length * (y1-y0);
        // 箭头左翼
        double angle = Math.atan((x1-x0)/ (y1-y0));
        double p2x = p1x - Math.cos(angle) * M2 * radius;
        double p2y = p1y + Math.sin(angle) * M2 * radius;
        // 箭头右翼
        double p3x = p1x + Math.cos(angle) * M2 * radius;
        double p3y = p1y - Math.sin(angle) * M2 * radius;
        
        triangleX[1] = p2x;
        triangleX[2] = p3x;
        triangleY[1] = p2y;
        triangleY[2] = p3y;
        
        StdDraw.line(x0, y0, p1x, p1y);
        StdDraw.filledPolygon(triangleX, triangleY);
        
        /*
        StdDraw.line(x0, y0, x1, y1);
        StdDraw.setPenRadius(radius*1.5);
        StdDraw.point(p1x, p1y);
        StdDraw.point(p2x, p2y);
        StdDraw.point(p3x, p3y);
        */
        
        StdDraw.setPenColor(oldCol);
        StdDraw.setPenRadius(oldRad);
    }
    
}
