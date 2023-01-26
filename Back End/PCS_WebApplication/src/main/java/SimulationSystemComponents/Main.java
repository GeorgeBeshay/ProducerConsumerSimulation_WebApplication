package SimulationSystemComponents;

import java.util.*;
import java.util.concurrent.*;

public class Main {

	public static void main(String[] args) {
		
		BlockingQueue<Product> tempQ1 = new ArrayBlockingQueue<Product>(10);
		BlockingQueue<Product> tempQ2 = new ArrayBlockingQueue<Product>(10);
		BlockingQueue<Product> tempQ3 = new ArrayBlockingQueue<Product>(10);
		generateProducts(6, tempQ1);
		
		HashMap<Integer, BlockingQueue<Product>> systemQueues = new HashMap<Integer, BlockingQueue<Product>>();
		systemQueues.put(0, tempQ1);
		systemQueues.put(1, tempQ2);
		systemQueues.put(2, tempQ3);
		
		Machine m1 = new Machine(1, 3, tempQ2, tempQ1);
		Machine m2 = new Machine(2, 4, tempQ3, tempQ2);
		ArrayList<Machine> systemMachines = new ArrayList<Machine>();
		systemMachines.add(m1);
		systemMachines.add(m2);
		
		SimulationSystem S = new SimulationSystem(systemMachines, systemQueues);
		S.generateSystem();
		
	}
	
	public static void generateProducts(int count, BlockingQueue<Product> Q) {
		while(count > 0) {
			try {
				Q.put(new Product(count));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count--;
		}
	}

}
