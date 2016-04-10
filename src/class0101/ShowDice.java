package class0101;
//1.1.35
public class ShowDice {

	public static void main(String[] args) {
		int SIDES = 6;
		int SIZE = 2*SIDES+1;
		double[] dist = new double[SIZE];
		for (int i = 1; i <= SIDES; i++) {
			for (int j = 1; j <= SIDES; j++) {
				dist[i+j] += 1.0;
			}
		}
		
		Histogram.width = 1.0/SIZE;
		for (int k = 2; k <= 2*SIDES; k++) {
			Histogram.dividing(k*1.0/SIZE, dist[k]/36);
		}
		
	}

}
