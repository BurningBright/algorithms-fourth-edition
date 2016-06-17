package class0201;

import java.awt.Color;
import java.lang.reflect.Method;

import rlgs4.Stopwatch;
import stdlib.StdDraw;
import stdlib.StdRandom;

/**
 * 
 * describe: 
 * 		show methods' performance
 * 		use reflect reference method,and show result,
 * 		in StdDraw windows.
 * date:	 20150324 14:25:26
 * @author:	 Chen
 *
 */
public class TimePlot {
	
	private static final double inLen = .2;
	private static final int signNum = 4;
	private static final double arrowLen = .05;
	private static final double arrowAngle = 15.0;
	private static final double tOffset = -.025;
	private static final double bOffset = .975;
	
	private static final double timeNum = .2;
	private static final int multiNum = 100;
	
	private static void generate(int N, String alg, String met) {
		
		int part = N/multiNum;
		
		// the multiple
		for(int i=1; i<multiNum+1; i++) {
			
			//test times
			Stopwatch watch = new Stopwatch(); 
			try {
				Method src = Class.forName(alg)
						.getMethod(met, Comparable[].class);
				src.invoke(null, (Object)dataPrepare(part*i));
			} catch(Exception e) {
				e.printStackTrace();
			}
			double y = watch.elapsedTime()/(timeNum*signNum)/(inLen*signNum);
			double x = (inLen*signNum)*i/multiNum;
//			System.out.println(x+" "+y);
			drawData(x, y);
		}
		
	}
	
	private static void initialDraw(int N) {
		StdDraw.line(0, 0, 1, 0);
		StdDraw.line(0, -.03, 0, 1);
		
		double point1X = 0 - arrowLen * Math.sin(arrowAngle/360.0 * 2 * Math.PI);
		double point1Y = 1 - arrowLen * Math.cos(arrowAngle/360.0 * 2 * Math.PI);
		double point2X = 0 + arrowLen * Math.sin(arrowAngle/360.0 * 2 * Math.PI);
		StdDraw.line(0, 1, point1X, point1Y);
		StdDraw.line(0, 1, point2X, point1Y);
		
		double point3X = 1 - arrowLen * Math.cos(arrowAngle/360.0 * 2 * Math.PI);
		double point3Y = 0 + arrowLen * Math.sin(arrowAngle/360.0 * 2 * Math.PI);
		double point4Y = 0 - arrowLen * Math.sin(arrowAngle/360.0 * 2 * Math.PI);
		StdDraw.line(1, 0, point3X, point3Y);
		StdDraw.line(1, 0, point3X, point4Y);
		
		StdDraw.setPenColor(Color.RED);
		StdDraw.text(tOffset, bOffset, "Times(s)", 90.0);
		StdDraw.text(bOffset, tOffset, "Sample(t)");
		
		for(int i=1; i<signNum+1; i++) {
			StdDraw.text(inLen * i, tOffset, Math.ceil(N/(signNum+1-i))+"");
		}
		
		for(int i=1; i<signNum+1; i++) {
			StdDraw.text(tOffset, inLen * i, Math.ceil(timeNum*i * 100)/100.0 + "", 90);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static Comparable[] dataPrepare(int N) {
		
		Comparable[] previous = new Double[N];
		for(int i=0; i<N; i++) {
			previous[i] = StdRandom.uniform();
		}
		
		return previous;
	}
	
	private static void drawData(double x, double y) {
		StdDraw.filledCircle(x, y, .005);
	}
	
	public static void main(String[] args) {
		initialDraw(10000);
		generate(10000, "class0201.ShellSort", "sort");
		generate(10000, "class0201.InsertionSort", "optimizedThreeSort");
		generate(10000, "class0201.InsertionSort", "sort");
		generate(10000, "class0201.SelectionSort", "sort");
	}

}
