package benchmark;

import java.awt.Color;

import class0104.Adjustable2DChart;
import rlgs4.Stopwatch;
import stdlib.StdRandom;

/**
 * 性能对比
 * 二项式拟合三角函数优化地理距离计算
 * @author chenguang.lin
 * @date 2020-11-02
 */
public class GeoClacComparsion {

    private static double data[] = new double[10000000];

    public static void main(String[] args) {
        double[] fit = { 0.9972739367451966, 0.000432385212626, -0.0001752266754157, 0.000000496619535 };

        System.out.println(distHaversineRAD(24.035, 114.015, 23.96667, 114.065));
        System.out.println(distHaversineRAD(30.297218, 120.171115, 28.679587, 121.426731));

        System.out.println(distanceSimplifyMore(24.035, 114.015, 23.96667, 114.065, fit));
        System.out.println(distanceSimplifyMore(30.297218, 120.171115, 28.679587, 121.426731, fit));

        System.out.println();

        Adjustable2DChart a2d = new Adjustable2DChart(0.1, 0.1, 0, 0);

        a2d.setAxisDescDistanceChart(-.3);
        a2d.setAxisDescDistanceY(.07);
        a2d.setChartDesc("Haversine VS Simplify");
        a2d.setAxisXDesc("calc size N W");
        a2d.setAxisYDesc("running time T(N) ms");
        a2d.setColorForChar(Color.RED);
        a2d.setLinked(true);

        // data prepare
        for (int i = 0; i < 10000000; i++)
            data[i] = StdRandom.uniform();
        /*
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 10000000; i++) {
            distanceSimplifyMore(data[i], 114.015, 23.96667, 114.065, fit);
        }
        StdOut.println(sw.elapsedTime());
        */

        int N = 800000;
        for (int i = N; i < 10000001; i *= 2) {
            double haversine = loopHaversine(i);
            a2d.addChartData((double) i, haversine);
        }

        for (int i = N; i < 10000001; i *= 2) {
            double simplify = loopSimplify(i);
            a2d.addAxisDataX((double) i, i / 10000 + "W");
            a2d.addChartData(false, true, (double) i, simplify);
        }
        a2d.reDraw();
    }

    public static double loopHaversine(int times) {
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < times; i++) {
            // distHaversineRAD(24.035,114.015,23.96667,114.065);
            distHaversineRAD(data[i], 114.015, 23.96667, 114.065);
        }
        return sw.elapsedTime();
    }

    public static double loopSimplify(int times) {
        double[] fit = { 0.9972739367451966, 0.000432385212626, -0.0001752266754157, 0.000000496619535 };
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < times; i++) {
            distanceSimplifyMore(data[i], 114.015, 23.96667, 114.065, fit);
        }
        return sw.elapsedTime();
    }

    public static double distHaversineRAD(double lat1, double lon1, double lat2, double lon2) {
        lat1 = toRadians(lat1);
        lat2 = toRadians(lat2);
        lon1 = toRadians(lon1);
        lon2 = toRadians(lon2);
        double hsinX = Math.sin((lon1 - lon2) * 0.5);
        double hsinY = Math.sin((lat1 - lat2) * 0.5);
        double h = hsinY * hsinY + (Math.cos(lat1) * Math.cos(lat2) * hsinX * hsinX);
        return 2 * Math.atan2(Math.sqrt(h), Math.sqrt(1 - h)) * 6367000;
    }

    public static double distanceSimplifyMore(double lat1, double lng1, double lat2, double lng2, double[] fit) {
        // 1) 计算三个参数
        // 经度差值
        double dx = lng1 - lng2;
        // 纬度差值
        double dy = lat1 - lat2;
        // 平均纬度
        double b = (lat1 + lat2) / 2.0;
        // 2) 计算东西方向距离和南北方向距离(单位：米)，东西距离采用三阶多项式
        double Lx = (fit[3] * b * b * b + fit[2] * b * b + fit[1] * b + fit[0]) * toRadians(dx) * 6367000.0; // 东西距离
        // 南北距离
        double Ly = 6367000.0 * toRadians(dy);

        StdRandom.uniform();
        // 3) 用平面的矩形对角距离公式计算总距离
        return Math.sqrt(Lx * Lx + Ly * Ly);
    }

    // 把经纬度转为度（°）
    public static double toRadians(double d) {
        return d * Math.PI / 180.0;
    }

}
