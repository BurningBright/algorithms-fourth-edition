package class0104;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rlgs4.DoublingTest;
import stdlib.StdDraw;

/**
 * @Description 可调平面图表
 * @author Leon
 * @date 2016-06-17 15:44:52
 */
public class Adjustable2DChart {
    
    // X轴画幅
    private double baseX;
    // Y轴画幅
    private double baseY;
    
    // 图表解释
    private String chartDesc = "";
    private String axisXDesc = "";
    private String axisYDesc = "";
    
    // 解释文字&轴的距离
    private double axisDescDistanceX = .07;
    private double axisDescDistanceY = .12;
    private double axisDescDistanceChart = .2;
    
    // 数据相对0点缩放量[0.3][0.1]
    private double relativeX;
    private double relativeY;
    
    // 数据整体偏移量
    private double offsetX;
    private double offsetY;
    
    // 坐标划线长度
    private double axisLineLength = .013;
    
    // 横纵坐标数据
    private Map<Double, String> axisX = new HashMap<Double, String>();
    private Map<Double, String> axisY = new HashMap<Double, String>();
    // 横纵坐标输入数据
    private Map<Double, String> axisInX = new HashMap<Double, String>();
    private Map<Double, String> axisInY = new HashMap<Double, String>();
    
    // 轴最大值
    private double axisMaxInX = -10000;
    private double axisMaxInY = -10000;
    
    private boolean isLinked = false;
    private boolean isCurves = false;
    
    // 坐标数据
    private List<Circle> coordinate = new LinkedList<Circle>();
    private double radius = .007;
    
    /**
     * @param relativeX 画面整体X偏移
     * @param relativeY 画面整体Y偏移
     * @param offsetX	X方向数据、轴、描述偏移
     * @param offsetY	Y方向数据、轴、描述偏移
     */
    public Adjustable2DChart(double relativeX, double relativeY, double offsetX, double offsetY) {
        this.relativeX = relativeX;
        this.relativeY = relativeY;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        
        baseX = 1 - relativeX;
        baseY = 1 - relativeY;
//        baseLineX = new Line(0, 0, baseX, 0);
//        baseLineY = new Line(0, 0, 0, baseY);
        
    }
    
    public Adjustable2DChart(int pixelsX, int pixelsY, double relativeX, double relativeY, double offsetX, double offsetY) {
        StdDraw.setCanvasSize(pixelsX, pixelsY);
        StdDraw.setFont(new Font("lucidasans", Font.BOLD, 17));
        
        this.relativeX = relativeX;
        this.relativeY = relativeY;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        
        baseX = 1 - relativeX;
        baseY = 1 - relativeY;
//        baseLineX = new Line(0, 0, baseX, 0);
//        baseLineY = new Line(0, 0, 0, baseY);
        
    }
    
    // 基准划线
    public void addAxisDataX(Double position, String desc) {
        axisInX.put(position, desc);
        
        if(position > axisMaxInX) {
            double tmpAxisMaxInX = axisMaxInX;
            axisMaxInX = position;
            
            // 重排X轴
            Set<Double> inKey = axisInX.keySet();
            Map<Double, String> tmpMap = new HashMap<Double, String>();
            for (Double key : inKey) {
                tmpMap.put(baseX * key / axisMaxInX, axisInX.get(key));
            }
            axisX = tmpMap;
            
            // 重排数据
            List<Circle> tmpList = new LinkedList<Circle>();
            for(Circle c: coordinate) {
                // 按原最大/现最大  比例缩小坐标
                tmpList.add(new Circle(tmpAxisMaxInX * c.x / axisMaxInX, c.y, radius));
            }
            coordinate = tmpList;
        } else {
            axisX.put(baseX * position / axisMaxInX, desc);
        }
        
//        reDraw();
    }
    
    public void addAxisDataY(Double position, String desc) {
        axisInY.put(position, desc);
        
        if(position > axisMaxInY) {
            double tmpAxisInY = axisMaxInY;
            axisMaxInY = position;
            
            // 重排Y轴
            Set<Double> inKey = axisInY.keySet();
            Map<Double, String> tmpMap = new HashMap<Double, String>();
            for (Double key : inKey) {
                tmpMap.put(baseY * key / axisMaxInY, axisInY.get(key));
            }
            axisY = tmpMap;
            
            // 重排数据
            List<Circle> tmpList = new LinkedList<Circle>();
            for(Circle c: coordinate) {
                // 按原最大/现最大  比例放大坐标
                tmpList.add(new Circle(c.x , tmpAxisInY * c.y / axisMaxInY, radius));
            }
            coordinate = tmpList;
        } else {
            axisY.put(baseY * position / axisMaxInY, desc);
        }
        
//        reDraw();
    }
    
    // 基准划线
    public void addAxisDataX(Double position, String desc, boolean show) {
        if (show)
            axisInX.put(position, desc);
        
        if(position > axisMaxInX) {
            double tmpAxisMaxInX = axisMaxInX;
            axisMaxInX = position;
            
            // 重排X轴
            Set<Double> inKey = axisInX.keySet();
            Map<Double, String> tmpMap = new HashMap<Double, String>();
            for (Double key : inKey) {
                tmpMap.put(baseX * key / axisMaxInX, axisInX.get(key));
            }
            axisX = tmpMap;
            
            // 重排数据
            List<Circle> tmpList = new LinkedList<Circle>();
            for(Circle c: coordinate) {
                // 按原最大/现最大  比例缩小坐标
                tmpList.add(new Circle(tmpAxisMaxInX * c.x / axisMaxInX, c.y, radius));
            }
            coordinate = tmpList;
        } else if(show) {
            axisX.put(baseX * position / axisMaxInX, desc);
        }
        
//        reDraw();
    }
    
    public void addAxisDataY(Double position, String desc, boolean show) {
        if (show)
            axisInY.put(position, desc);
        
        if(position > axisMaxInY) {
            double tmpAxisInY = axisMaxInY;
            axisMaxInY = position;
            
            // 重排Y轴
            Set<Double> inKey = axisInY.keySet();
            Map<Double, String> tmpMap = new HashMap<Double, String>();
            for (Double key : inKey) {
                tmpMap.put(baseY * key / axisMaxInY, axisInY.get(key));
            }
            axisY = tmpMap;
            
            // 重排数据
            List<Circle> tmpList = new LinkedList<Circle>();
            for(Circle c: coordinate) {
                // 按原最大/现最大  比例放大坐标
                tmpList.add(new Circle(c.x , tmpAxisInY * c.y / axisMaxInY, radius));
            }
            coordinate = tmpList;
        } else if (show) {
            axisY.put(baseY * position / axisMaxInY, desc);
        }
        
//        reDraw();
    }
    
    public void addChartData(double x, double y) {
        /*
        if(x > axisMaxInX) {
            addAxisDataX(x, String.format("%.2f", x));
        }
        if(y > axisMaxInY) {
            addAxisDataY(y, String.format("%.2f", y));
        }
        */
        addAxisDataX(x, String.format("%.2f", x));
        addAxisDataY(y, String.format("%.2f", y));
        coordinate.add(new Circle(baseX * x / axisMaxInX, baseY * y / axisMaxInY, radius));
//        reDraw();
    }
    
    public void addChartData(boolean showX, boolean showY,double x, double y) {
        addAxisDataX(x, String.format("%.2f", x), showX);
        addAxisDataY(y, String.format("%.2f", y), showY);
        coordinate.add(new Circle(baseX * x / axisMaxInX, baseY * y / axisMaxInY, radius));
//        reDraw();
    }
    
    // 非均匀基准间隔[通过手动输入形式解决]
    // 加入新点数据，根据映射表最大值做相应放大并重绘,同上
    // 基准数据画图、解释
    public void reDraw() {
        
        StdDraw.clear();
        
        // X轴重绘
        Set<Double> axKeys = axisX.keySet();
        for(Double key: axKeys) {
          (new Line(key, 0, key, 0 - axisLineLength, 'x')).draw();;
          if(axisX.get(key) != null) 
              (new InnerStr(key, 0 - axisLineLength*3, axisX.get(key), 'x')).draw();
        }
        // X轴基准
        (new Line(0, 0, baseX, 0, 'x')).draw();
        
        // Y轴重绘
        Set<Double> ayKeys = axisY.keySet();
        for(Double key: ayKeys) {
          (new Line(0, key, 0 - axisLineLength, key, 'y')).draw();
          if(axisY.get(key) != null) 
              (new InnerStr(0 - axisLineLength*5, key, axisY.get(key), 'y')).draw();
        }
        // Y轴基准
        (new Line(0, 0, 0, baseY, 'y')).draw();
        
        // 数据重绘
        if(isLinked) {
            Circle previous = null;
            for(Circle current: coordinate) {
                current.draw();
                if(previous != null) {
                    (new Line(previous.p, current.p)).draw();
                }
                previous = current;
            }
            /*
            for(int i=0; i<coordinate.size(); i++) {
                coordinate.get(i).draw();
                if(i!=0)
                    (new Line(coordinate.get(i).p, coordinate.get(i-1).p)).draw();
                if(i==coordinate.size()-1)
                    (new Line(coordinate.get(i).p, coordinate.get(0).p)).draw();
            }
            */
        } 
        else if(isCurves && coordinate.size()>1) {
            double[] x = new double[coordinate.size()];
            double[] y = new double[coordinate.size()];
            for(int i=0; i<coordinate.size(); i++) {
                coordinate.get(i).draw();
                x[i] = coordinate.get(i).p.x;
                y[i] = coordinate.get(i).p.y;
            }
            StdDraw.curves(x, y);
        }
        else {
            for(Circle current: coordinate) 
                current.draw();
        }
        
        
        // 重绘描述
        (new InnerStr(0.5, -axisDescDistanceX, axisXDesc, 'x')).draw();
        (new InnerStr(-axisDescDistanceY, 0.5, axisYDesc, 'y')).draw(90);
        (new InnerStr(-axisDescDistanceChart, 0.77, chartDesc, 'T')).draw();
    }
    
    public void setColorForChar(Color c) {
        StdDraw.setPenColor(c);
    }
    
    class Point {
        double x;
        double y;
        public Point(double x, double y) {
            this.x = x + relativeX + offsetX;
            this.y = y + relativeY + offsetY;
        }
        public Point(double x, double y, char flag) {
            this.x = x + relativeX + ('x'==flag? offsetX: 0);
            this.y = y + relativeY + ('y'==flag? offsetY: 0);
        }
        public void draw() {
            StdDraw.point(x, y);
        }
    }
    
    class Circle {
        double x;
        double y;
        Point p;
        double radius;
        public Circle(double x, double y, double radius) {
            this.x = x;
            this.y = y;
            this.p = new Point(x, y);
            this.radius = radius;
        }
        public void draw() {
            StdDraw.filledCircle(p.x, p.y, radius);
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
        public Line(double x1, double y1, double x2, double y2, char flag) {
            this.p1 = new Point(x1, y1, flag);
            this.p2 = new Point(x2, y2, flag);
        }
        public void draw() {
            StdDraw.line(p1.x, p1.y, p2.x, p2.y);
        }
    }
    
    class InnerStr {
        double x;
        double y;
        String src;
        public InnerStr(double x, double y, String src) {
            this.x = x + relativeX;
            this.y = y + relativeY;
            this.src = src;
        }
        public InnerStr(double x, double y, String src, char flag) {
            this.x = x + relativeX + ('x'==flag? offsetX: 0);
            this.y = y + relativeY + ('y'==flag? offsetY: 0);
            this.src = src;
        }
        public void draw() {
            StdDraw.text(x, y, src);
        }
        public void draw(double degree) {
            StdDraw.text(x, y, src, degree);
        }
    }
    
    public String getChartDesc() {
        return chartDesc;
    }

    public void setChartDesc(String chartDesc) {
        this.chartDesc = chartDesc;
    }

    public String getAxisXDesc() {
        return axisXDesc;
    }

    public void setAxisXDesc(String axisXDesc) {
        this.axisXDesc = axisXDesc;
    }

    public String getAxisYDesc() {
        return axisYDesc;
    }

    public void setAxisYDesc(String axisYDesc) {
        this.axisYDesc = axisYDesc;
    }

    public double getAxisDescDistanceX() {
        return axisDescDistanceX;
    }

    public void setAxisDescDistanceX(double axisDescDistanceX) {
        this.axisDescDistanceX = axisDescDistanceX;
    }

    public double getAxisDescDistanceY() {
        return axisDescDistanceY;
    }

    public void setAxisDescDistanceY(double axisDescDistanceY) {
        this.axisDescDistanceY = axisDescDistanceY;
    }

    public double getAxisDescDistanceChart() {
        return axisDescDistanceChart;
    }

    public void setAxisDescDistanceChart(double axisDescDistanceChart) {
        this.axisDescDistanceChart = axisDescDistanceChart;
    }

    public double getRelativeX() {
        return relativeX;
    }

    public void setRelativeX(double relativeX) {
        this.relativeX = relativeX;
    }

    public double getRelativeY() {
        return relativeY;
    }

    public void setRelativeY(double relativeY) {
        this.relativeY = relativeY;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public boolean isLinked() {
        return isLinked;
    }

    public void setLinked(boolean isLinked) {
        this.isLinked = isLinked;
    }

    public boolean isCurves() {
        return isCurves;
    }

    public void setCurves(boolean isCurves) {
        this.isCurves = isCurves;
    }

    public static void main(String[] args) {
        // test1 标准分布
        /*
        Adjustable2DChart a2d = new Adjustable2DChart(0.3, 0.1, 0, 0);
        
        a2d.setChartDesc("standard plot");
        a2d.setAxisXDesc("problem size N");
        a2d.setAxisYDesc("running time T(N)");
        
        a2d.addAxisDataX(1000.0, "1k");
        a2d.addAxisDataX(2000.0, "2k");
        a2d.addAxisDataX(3000.0, null);
        a2d.addAxisDataX(4000.0, "4k");
        a2d.addAxisDataX(5000.0, null);
        a2d.addAxisDataX(6000.0, null);
        a2d.addAxisDataX(7000.0, null);
        a2d.addAxisDataX(8000.0, "8k");
        
        a2d.addAxisDataY(5.0, null);
        a2d.addAxisDataY(10.0, "10");
        a2d.addAxisDataY(15.0, null);
        a2d.addAxisDataY(20.0, "20");
        a2d.addAxisDataY(25.0, null);
        a2d.addAxisDataY(30.0, "30");
        
        for (int N = 500; N<9000; N += 500) {
            double time = DoublingTest.timeTrial(N);
            a2d.addChartData(N, time);
            a2d.reDraw();
        }
        */
        
        // test2 测试超范围
        /*
        a2d.addChartData(8000.0, 15);
        a2d.addChartData(7000.0, 35);
        a2d.addChartData(8200.0, 5);
        a2d.reDraw();
        */
        
        // test3 测试交替添加
        /*
        a2d.addAxisDataY(1.0, "1");
        a2d.addChartData(10.0, 1.0);
        a2d.addAxisDataY(2.0, "2");
        a2d.addChartData(20.0, 2.0);
        a2d.addAxisDataY(3.0, "3");
        a2d.addChartData(30.0, 3.0);
        a2d.addAxisDataY(4.0, "4");
        a2d.addChartData(40.0, 4.0);
        a2d.addAxisDataY(3.5, "3.5");
        a2d.addChartData(50.0, 3.0);
        
        a2d.reDraw();
        */
        
        // test4 测试对数分布
        
        Adjustable2DChart a2d1 = new Adjustable2DChart(0.4, 0.1, -0.3, -0.1);
        
        a2d1.setChartDesc("log-log plot");
        a2d1.setAxisXDesc("problem size lgN");
        a2d1.setAxisYDesc("running time lg(T(N))");
        
        a2d1.addAxisDataX(Math.log10(1000.0), "1k");
        a2d1.addAxisDataX(Math.log10(2000.0), "2k");
        a2d1.addAxisDataX(Math.log10(4000.0), "4k");
        a2d1.addAxisDataX(Math.log10(8000.0), "8k");
//        a2d1.addAxisDataX(Math.log10(16000.0), "16k");
//        a2d1.addAxisDataY(1.28, "0.3");
//        a2d1.addAxisDataY(1.56, "0.5");
        a2d1.setCurves(true);
        a2d1.setColorForChar(Color.RED);
        for (int N = 1000; N<9000; N += N) {
            double time = DoublingTest.timeTrial(N);
            System.out.println(time + "--------" + Math.log10(time) + "-------"+ Math.log10(N*1.0));
            
            double fake = Double.parseDouble(String.format("%.2f", Math.log10(time*100)));
            System.out.println(N+"---"+fake);
            a2d1.addAxisDataY(fake, String.format("%.2f", time));
            a2d1.addChartData(Double.parseDouble(String.format("%.2f", Math.log10(N*1.0))), fake);
            a2d1.reDraw();
        }
        
    }

}
