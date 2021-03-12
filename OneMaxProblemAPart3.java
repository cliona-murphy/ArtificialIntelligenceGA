import java.util.ArrayList;
import java.util.Random;

public class OneMax {

	public static String[][] genotypes = new String[200][20];
	public static String[][] newPopulation = new String[200][20];
	public static String[][] tournamentGenotypes = new String[200][20];
	public static ArrayList<String[]> matingPool = new ArrayList<String[]>();
	static int generation = 0;
	
	public static boolean allOnes = false;
	
	public OneMax() {}
	
	public static String[] generateRandom() {
		
	   String[] bitString = new String[20];
	   for(int i=0;i<20;i++){
	      if(Math.random()>0.5){
	    	  bitString[i] = "1";
	      } else {
	    	  bitString[i] = "0";
	      }
	   }
	   return bitString;
	}
	
	public static ArrayList<String[]> generateMatingPool() {
		int[] fitness = calculateFitness();
		ArrayList<String[]> pool = new ArrayList<String[]>();
		generation++;
		calculateAverageFitnessForGeneration(fitness);
		//adding the genotype to the mating pool as many times as what their fitness score is
		//i.e. if genotype has fitness of 7, it will be added 7 times
		for(int i = 0; i < 200; i++) {
			for(int m = 0; m < fitness[i]; m++) {
				pool.add(genotypes[i]);
			}
		}
		return pool;
	}
	
	 public static void calculateAverageFitnessForGeneration(int[] fitness) {
		 double average = 0.0;
		 int total = 0;
		 for(int i=0; i < fitness.length; i++) {
			 total += fitness[i];
		 }
		 average = total/fitness.length;
		 System.out.println("Average fitness of generation "+generation+" = "+average);
	 }
	public static void performReproduction(ArrayList<String[]> pool) {
		
		for(int i = 0; i < genotypes.length; i++) {
			
			String[][] parents = new String[2][20];
			String[] child = new String[20];
			Random random1 = new Random();	
			int a = Math.abs(random1.nextInt(pool.size()));
			Random random2 = new Random();	
			int b =  Math.abs(random2.nextInt(pool.size()));
			
			parents[0] = pool.get(a);
			parents[1] = pool.get(b);
			
			//crossover
			child = crossover(parents);
			//mutate
			child = mutate(child);
			//add child to population		
			genotypes[i] = child;
		}
	}
		
	public static int[] calculateFitness() {
		
		int[] fitness = new int[200];
		
		for(int i = 0; i < 200; i++) {
			for(int j = 0; j < 20; j++) {
				fitness[i] += Integer.parseInt(genotypes[i][j]);
			}
		}
		
		for(int j = 0; j < 200; j++) {
			if (fitness[j] == 20) {
				System.out.println("Fitess of 20 for bitString ");
				for(int m =0; m< 20; m++){
					System.out.print(genotypes[j][m]);
				}
				
				allOnes = true;
			}
			
			if(fitness[j] == 0) {
				System.out.println("Fitness 0 found");
				fitness[j] = 2*(genotypes[j].length);
			}
		}
		
		return fitness;
	}
	
	//only one child is a result of crossover
	public static String[] crossover(String[][] parents) {

		String[] child = new String[20];
		Random rand = new Random();
		int midpoint = rand.nextInt(20);

		for(int i = 0; i < 20; i++) {
			if (midpoint > i) {
				child[i] = parents[0][i];
			} else {
				child[i] = parents[1][i];
			}
		}
		//add children to new population
		return child;
	}
	
	public static String[] mutate(String[] string) {

		int mutationRateInt = 10;
		Random r = new Random();
		int randNum = r.nextInt(100);
		for(int i = 0; i < string.length; i++) {
			if (randNum < mutationRateInt) {
				if(string[i].equals("1")) {
		      		string[i] = "0";
		      	} else {
		      		string[i] = "1";
		      	}
			}
		}
		return string;
	}
	
	public static void main (String[] args) {
		for (int i = 0; i < 200; i++) {
			genotypes[i] = generateRandom();
		}
		
		while(!allOnes) {
			matingPool = generateMatingPool();
			performReproduction(matingPool);
			matingPool.removeAll(matingPool);
		}
		System.out.print("Bitstring with all 1s generated!");
		
	}	
}
