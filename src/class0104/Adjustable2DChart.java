package class0104;

import stdlib.StdDraw;

/**
 * @Description 可调平面图表
 * @author Leon
 * @date 2016-06-17 15:44:52
 */
public class Adjustable2DChart {
    
    // 基准偏移 & 数据缩小比例
    private static final double offSetX = .2;
    private static final double offSetY = .1;
    // X轴基准
    private Line baseLineX;
    // Y轴基准
    private Line baseLineY;
    // X轴画幅倍数
    private double multipleX;
    // Y轴画幅倍数
    private double multipleY;
    // X轴标准间隔
    private double intervalX;
    // Y轴标准间隔
    private double intervalY;
    
    
    public Adjustable2DChart() {
        multipleX = 1 - offSetX;
        multipleY = 1 - offSetY;
        baseLineX = new Line(0, 0, multipleX, 0);
        baseLineY = new Line(0, 0, 0, multipleY);
        
        baseLineX.draw();
        baseLineY.draw();
    }
    
    public Adjustable2DChart(int pixelsX, int pixelsY) {
        StdDraw.setCanvasSize(pixelsX, pixelsY);
        
        multipleX = 1 - offSetX;
        multipleY = 1 - offSetY;
        baseLineX = new Line(0, 0, multipleX, 0);
        baseLineY = new Line(0, 0, 0, multipleY);
        
        baseLineX.draw();
        baseLineY.draw();
        
    }
    
    // TODO 基准的划线
    // TODO 基准数据画图
    // TODO 基准数据解释
    // TODO 非均匀基准间隔
    // TODO 加入新点数据，根据映射表最大值做相应放大并重绘
    
    
    class Point {
        double x;
        double y;
        double baseX = offSetX;
        double baseY = offSetY;
        public Point(double x, double y) {
            this.x = x + baseX;
            this.y = y + baseY;
        }
        public void draw() {
            StdDraw.point(x, y);
        }
    }
    
    class Line {
        Point p1;
        Point p2;
        public Line(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }
        public Line(double x1, double y1, double x2, double y2) {
            this.p1 = new Point(x1, y1);
            this.p2 = new Point(x2, y2);
        }
        public void draw() {
            StdDraw.line(p1.x, p1.y, p2.x, p2.y);
        }
    }
    
    public static void main(String[] args) {
//        new Adjustable2DChart();
        new Adjustable2DChart(512, 768);
    }

}
