
public class Driver {

	public static void main(String[] args) {
		double[] weights = {78, 35, 89, 36, 94, 75, 74, 79, 80, 16};
		double[] values = {18, 9, 23, 20, 59, 61, 70, 75, 76, 30};
		
		KnapSackGA knapsack = new KnapSackGA(10, weights, values, 156, 100, 200, 0.8, 0.01);
	}
}
