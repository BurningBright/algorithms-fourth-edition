package class0402;

import java.awt.Color;
import java.awt.Font;

import class0401.StdDraw;
import rlgs4.Digraph;
import stdlib.In;

/**
 * @Description 4.2.2
 *          画小有向图
 * @author Leon
 * @date 2017-12-07 19:32:00
 */
public class DrawTinyDG {
    
    static double SX = 100;
    static double SY = 100;
    static double cRadius = 3.5;
    static double pRadius = .005;
    
    public static void main(String[] args) {
        StdDraw.setXscale(0, SX);
        StdDraw.setYscale(SY, 0);
        
        double[] px = {28.0, 48.0, 48.0, 66.2, 66.2, 28.0, 66.2, 28.0, 48.0, 48.0, 48.0, 28.0};
        double[] py = {10.8, 73.0, 26.4, 37.6, 66.4, 45.8, 10.8, 58.0, 58.0, 89.2, 45.8, 73.0};
        boolean[] painted = new boolean[px.length];
        
        StdDraw.setPenRadius(.003);
        StdDraw.setFont(new Font("MyriadPro", Font.BOLD, 20));
        Digraph dgh = new Digraph(new In("resource/4.2/tinyDGex2.txt"));
        for(int i=0; i<dgh.V(); i++) {
            for(Integer j: dgh.adj(i)) {
                if(!painted[j]) {
                    StdDraw.text(px[j], py[j]+.3, j+"");
                    StdDraw.circle(px[j], py[j], cRadius);
                    painted[j] = true;
                }
                
                drawEdge(px[i], py[i], px[j], py[j], StdDraw.BLACK);
                
            }
            if(!painted[i]) {
                StdDraw.text(px[i], py[i]+.3, i+"");
                StdDraw.circle(px[i], py[i], cRadius);
                painted[i] = true;
            }
        }
        
    }
    
    public static void drawEdge(double x0, double y0, double x1, double y1, Color color) {
        
        double xFrom, yFrom, xTo, yTo;
        if(x0-x1 > 0) {
            double angle = Math.atan((y0-y1)/(x0-x1));
            xTo = x1 + Math.cos(angle) * cRadius;
            yTo = y1 + Math.sin(angle) * cRadius;
            xFrom = x0 - Math.cos(angle) * cRadius;
            yFrom = y0 - Math.sin(angle) * cRadius;
        } else {
            double angle = Math.atan((y0-y1)/(x1-x0));
            xTo = x1 - Math.cos(angle) * cRadius;
            yTo = y1 + Math.sin(angle) * cRadius;
            xFrom = x0 + Math.cos(angle) * cRadius;
            yFrom = y0 - Math.sin(angle) * cRadius;
        }
        drawArrow(xFrom, yFrom, xTo, yTo, SX, SY, pRadius, color);
    }
    
    public static void drawArrow(double x0, double y0, double x1, double y1, 
            double scaleX, double scaleY, double radius, Color color) {
        
        double M1 = 3 * Math.abs(scaleX);
        double M2 = 1.5 * Math.abs(scaleY);
        
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
