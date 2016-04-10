package class0105;

import stdlib.StdDraw;
import class0105.RandomGrid.Connection;

/**
 * RandomGrid client
 * use Stdraw draw grid
 * @author Chen
 */
public class Animation {

	public static void main(String[] args) {
		int N = 6;
		
		// 画布俩轴缩放方向、倍数
		StdDraw.setXscale(0, 27.0);
		StdDraw.setYscale(-27.0, 0);
		
		// 坐标放大倍数
		double offsetX = 27/N;
		double offsetY = 27/N;
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				StdDraw.filledCircle(i*offsetX, -j*offsetY, 0.1);
			}
		}
		Connection[] con = new Connection[N*N*N*N];
		for(int i=0, k=0; i<N*N; i++) {
			for(int j=0; j<N*N; j++,k++) {
				con[k] = new Connection(i,j);
			}
		}
		
		// 生成一联通连接
		//Connection[] con = RandomGrid.generate(N*N);
		for(int i=0; i<con.length; i++) {
			double x1 = con[i].p/N;
			double y1 = con[i].p%N;
			double x2 = con[i].q/N;
			double y2 = con[i].q%N;
			StdDraw.line(x1*offsetX, -y1*offsetY, x2*offsetX, -y2*offsetY);
		}
	}

}
