package ToBeDeleted;

import java.util.*;
import java.util.concurrent.*;

public class Main {

	public static void main(String[] args) {
		
		BlockingQueue<Product> tempQ1 = new ArrayBlockingQueue<Product>(10);
		BlockingQueue<Product> tempQ2 = new ArrayBlockingQueue<Product>(10);
		generateProducts(6, tempQ1);
		Machine m1 = new Machine(1, 4, tempQ2, tempQ1);
		Machine m2 = new Machine(2, 2, tempQ2, tempQ1);
		
		ArrayList<Machine> systemMachines = new ArrayList<Machine>();
		systemMachines.add(m1);
		systemMachines.add(m2);
		
		
		for(Machine M : systemMachines) {
			Thread tempMachineThread = new Thread(M);
			tempMachineThread.start();
		}
		
		
		
	}
	
	public static void generateProducts(int count, BlockingQueue<Product> Q) {
		while(count > 0) {
			try {
				Q.put(new Product(count));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count--;
		}
	}

}
