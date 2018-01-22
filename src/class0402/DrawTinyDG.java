package class0402;

import java.awt.Color;
import java.awt.Font;

import class0401.StdDraw;
import rlgs4.Digraph;
import rlgs4.Queue;
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
                
                drawDirectedEdgeBetweenTwoCircle(
                        px[i], py[i], px[j], py[j], SX, SY, cRadius, pRadius, StdDraw.BLACK);
                
            }
            if(!painted[i]) {
                StdDraw.text(px[i], py[i]+.3, i+"");
                StdDraw.circle(px[i], py[i], cRadius);
                painted[i] = true;
            }
        }
        
    }
    
    /**
     * 两圆间的无向连线
     * @param x0        第一个节点圆心x轴
     * @param y0        第一个节点圆心y轴
     * @param x1        第二个节点圆心x轴
     * @param y1        第二个节点圆心y轴
     * @param cRadius   节点半径
     * @param pRadius   画笔半径
     * @param color     线-箭头颜色
     */
    public static void drawNodirectedEdgeBetweenTwoCircle(double x0, double y0, double x1, double y1, 
            double cRadius, double pRadius, Color color) {
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
        
        Color oldCol = StdDraw.getPenColor();
        double oldRad = StdDraw.getPenRadius();
        StdDraw.setPenColor(color);
        StdDraw.setPenRadius(pRadius);
        
        StdDraw.line(xFrom, yFrom, xTo, yTo);
        
        StdDraw.setPenColor(oldCol);
        StdDraw.setPenRadius(oldRad);
    }
    
    /**
     * 两节点间的偏移线-箭头
     * @param x0        第一个节点圆心x轴
     * @param y0        第一个节点圆心y轴
     * @param x1        第二个节点圆心x轴
     * @param y1        第二个节点圆心y轴
     * @param offset    偏移量
     * @param dash      是否是虚线
     * @param scaleX    x轴放大倍率
     * @param scaleY    y轴放大倍率
     * @param cRadius   节点半径
     * @param pRadius   画笔半径
     * @param color     线-箭头颜色
     */
    public static void drawDirectedEdgeBetweenTwoOffsetCircle(double x0, double y0, double x1, double y1, 
            double offset, boolean dash, double scaleX, double scaleY, double cRadius,  double pRadius, Color color) {
        double angle = Math.atan(Math.abs((y1-y0)/(x1-x0)));
        int signX = x1>x0? -1: 1;
        int signY = y1>y0? -1: 1;
        if (x0 == x1)
            signX = y1>y0? -1: 1;
        if (y0 == y1)
            signY = x1>x0? -1: 1;
        double offsetX = signX * offset * Math.sin(angle);
        double offsetY = signY * offset * Math.cos(angle);
        if (dash)
            drawDirectedDashEdgeBetweenTwoCircle(x0 + offsetX, y0 + offsetY, x1 + offsetX, y1 + offsetY, 
                    scaleX, scaleY, cRadius, pRadius, color);
        else
            drawDirectedEdgeBetweenTwoCircle(x0 + offsetX, y0 + offsetY, x1 + offsetX, y1 + offsetY, 
                    scaleX, scaleY, cRadius, pRadius, color);
    }
    
    /**
     * 两节点间的线-箭头
     * @param x0        第一个节点圆心x轴
     * @param y0        第一个节点圆心y轴
     * @param x1        第二个节点圆心x轴
     * @param y1        第二个节点圆心y轴
     * @param scaleX    x轴放大倍率
     * @param scaleY    y轴放大倍率
     * @param cRadius   节点半径
     * @param pRadius   画笔半径
     * @param color     线-箭头颜色
     */
    public static void drawDirectedEdgeBetweenTwoCircle(double x0, double y0, double x1, double y1, 
            double scaleX, double scaleY, double cRadius,  double pRadius, Color color) {
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
        drawArrow(xFrom, yFrom, xTo, yTo, scaleX, scaleY, pRadius, color);
    }
    
    public static void drawDirectedDashEdgeBetweenTwoCircle(double x0, double y0, double x1, double y1, 
            double scaleX, double scaleY, double cRadius,  double pRadius, Color color) {
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
        drawDashArrow(xFrom, yFrom, xTo, yTo, scaleX, scaleY, pRadius, color);
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
    
    public static void drawDashArrow(double x0, double y0, double x1, double y1, 
            double scaleX, double scaleY, double radius, Color color) {
        
        double M1 = 3 * Math.abs(scaleX);
        double M2 = 1.5 * Math.abs(scaleY);
        double M3 = (Math.abs(scaleX) + Math.abs(scaleY)) /100;
        
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
        
        StdDraw.filledPolygon(triangleX, triangleY);
        
        
        Double x[], y[];
        x = y = null;
        /*
        double dashLength = (x0 < p1x? 1:-1) * Math.sqrt(Math.pow(p1x - x0, 2) + Math.pow(p1y - y0, 2));
        double dashLengthP = (x0 < p1x? 1:-1) * (Math.sqrt(Math.pow(p1x - x0, 2) + Math.pow(p1y - y0, 2))-radius);
        if (Math.abs(dashLength) < M3) {
            x = new Double[6];
            y = new Double[6];
            for (int i=0; i<6; i+=2) {
                
                x[i] = p1x - Math.sin(angle) * i/5.0 * dashLength;
                y[i] = p1x - Math.cos(angle) * i/5.0 * dashLength;
                
                if (i == 4) {
                    x[i+1] = p1x - Math.sin(angle) * (i+1)/5.0 * dashLength;
                    y[i+1] = p1x - Math.cos(angle) * (i+1)/5.0 * dashLength;
                } else {
                    x[i+1] = p1x - Math.sin(angle) * (i+1)/5.0 * dashLengthP;
                    y[i+1] = p1x - Math.cos(angle) * (i+1)/5.0 * dashLengthP;
                }
                    
                
                x[i] = x0 + i/5.0 * (x0-p1x);
                y[i] = x0 + i/5.0 * (x0-p1x);
                
                x[i+1] = x0 + (i+1)/5.0 * (x0-p1x);
                y[i+1] = y0 + (i+1)/5.0 * (y0-p1y);
                
            }
        } else {
            
        }
        */
        Queue<Double> qX = new Queue<Double>();
        Queue<Double> qY = new Queue<Double>();
        int signX = x1>x0? -1: 1;
        int signY = y1>y0? -1: 1;
        angle = Math.atan(Math.abs((x1-x0)/ (y1-y0)));
        qX.enqueue(p1x);
        qY.enqueue(p1y);
        int count = 1;
        double xthreshold = Math.abs(x1 - x0);
        double ythreshold = Math.abs(y1 - y0);
        while(true) {
            double xt = signX * Math.sin(angle) * M3 * count;
            double yt = signY * Math.cos(angle) * M3 * count;
            if(Math.abs(xt) < xthreshold || Math.abs(yt) < ythreshold) {
                qX.enqueue(p1x + xt);
                qY.enqueue(p1y + yt);
            } else {
                break;
            }
            count++;
        }
        
        x = new Double[qX.size()];
        y = new Double[qX.size()];
        for (int i=0; qX.size() != 0; i++) {
            x[i] = qX.dequeue();
            y[i] = qY.dequeue();
        }
        
        for (int i=0; i<x.length; i+=2)
            if(i+1 != x.length)
                StdDraw.line(x[i], y[i], x[i+1], y[i+1]);
        
        StdDraw.setPenColor(oldCol);
        StdDraw.setPenRadius(oldRad);
    }
    
    public static void dash() {
        
        DrawTinyDG.drawDirectedDashEdgeBetweenTwoCircle(.5, .5, .1, .7, 1, 1, .005, .005, StdDraw.RED);
        DrawTinyDG.drawDirectedDashEdgeBetweenTwoCircle(.5, .5, .7, .7, 1, 1, .005, .005, StdDraw.RED);
        DrawTinyDG.drawDirectedDashEdgeBetweenTwoCircle(.5, .5, .1, .2, 1, 1, .005, .005, StdDraw.RED);
        DrawTinyDG.drawDirectedDashEdgeBetweenTwoCircle(.5, .5, .7, .1, 1, 1, .005, .005, StdDraw.RED);
        
        DrawTinyDG.drawDirectedDashEdgeBetweenTwoCircle(.5, .5, .5, .1, 1, 1, .005, .005, StdDraw.RED);
        DrawTinyDG.drawDirectedDashEdgeBetweenTwoCircle(.5, .5, .5, .7, 1, 1, .005, .005, StdDraw.RED);
        DrawTinyDG.drawDirectedDashEdgeBetweenTwoCircle(.5, .5, .1, .5, 1, 1, .005, .005, StdDraw.RED);
        DrawTinyDG.drawDirectedDashEdgeBetweenTwoCircle(.5, .5, .7, .5, 1, 1, .005, .005, StdDraw.RED);
        
    }
    
}
