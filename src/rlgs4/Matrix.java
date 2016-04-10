package rlgs4;

import java.util.Arrays;
/**
 * matrix api
 * multiplication in matrix
 * transpore in matrix
 * vector multiplication
 * @author soft01
 */
public class Matrix {
	      
	//vector dot product
	public static double dot(double[] x, double[] y) {
		double target = 0;
		if (x.length != y.length) {
			throw new IllegalArgumentException();
		}
		for(int i=0; i<x.length; i++) {
			target += x[i]*y[i];
		}
		
		return target;
	}
	
	//matrix-matrix product
	public static double[][] mult(double[][] a, double[][] b){

		//the input matrix's rows must equals to self matrix's column
		
		int aRow = a.length;
		int bRow = b.length;
		int aCol = a[0].length;
		int bCol = b[0].length;
		
		System.out.println(aRow+" "+aCol+" "+bRow+" "+bCol);
		//judge the validity
		if(aCol != bRow) {
			throw new IllegalArgumentException();
		}
		
		//result set
		double target[][] = new double[aRow][bCol];
		//change matrix b's pose
		double tmpB[][] =  transpose(b);
		
		for(int i=0; i<aRow; i++) {
			for(int j=0; j<bCol; j++) {
				//get result
				target[i][j] = dot(a[i], tmpB[j]);
			}
		}
		
		return target;
	}
	
	//transpose
	public static double[][] transpose(double[][] a) {
		double[][] target = new double[a[0].length][a.length];

		for(int col=0; col<a.length; col++) {
			for(int row=0; row<a[col].length; row++) {
				target[row][col] = a[col][row];
			}
		}
		
		return target;
	}
	
	//matrix-vector product
	public static double[][] mult(double[][] a, double[] x) {
		int aCol = a[0].length;
		int aRow = a.length;
		System.out.println(aRow+" "+aCol);
		
		if(aCol != 1) {
			throw new IllegalArgumentException();
		}
		
		double[][] target = new double[aRow][x.length];
		
		for(int i=0; i<aRow; i++) {
			for(int j=0; j<x.length; j++) {
				target[i][j] = a[i][0] * x[j];
			}
		}
		
		return target;
	}
	
	//vector-matrix product
	static double[] mult(double[] y, double[][] a) {
		int aRow = a.length;
		int aCol = a[0].length;
		
		if(y.length != aRow) {
			throw new IllegalArgumentException();
		}
		
		double[] target = new double[aCol];
		double tmpA[][] =  transpose(a);
		
		for(int i=0; i<aCol; i++) {
			target[i] = dot(y, tmpA[i]);
		}
		
		return target;
	}
	
	public static void main(String[] args) {
		double[] test1_x = {1.0, 2.0, 3.0};
		double[] test1_y = {3.0, 4.0, 5.0};
		System.out.println("Test1:"+dot(test1_x, test1_y));
		
		double[][] test2_a = {{1.0, 3.0},{2.0, 4.0},{3.0, 5.0}};
		double[][] test2_b = {{1.0, 2.0, 3.0, 4.0},{2.0, 3.0, 4.0, 5.0}};
		System.out.println("Test2:"+Arrays.deepToString(mult(test2_a, test2_b)));
		
		double[][] test3 = {{1.0, 2.0, 3.0},{3.0, 4.0, 5.0}};
		System.out.println("Test3:"+Arrays.deepToString(transpose(test3)));

		double[][] test4_a = {{1.0},{2.0},{3.0},{4.0}};	//as row
		double[] test4_x = {4.0, 3.0, 2.0};					//as column
		System.out.println("Test4:"+Arrays.deepToString(mult(test4_a, test4_x)));
		
		double[] test5_y = {1.0, 2.0};
		double[][] test5_a = {{1.0, 3.0, 5.0, 7.0},{2.0, 4.0, 6.0, 8.0}};
		System.out.println("Test5:"+Arrays.toString(mult(test5_y, test5_a)));
		
	}

}
