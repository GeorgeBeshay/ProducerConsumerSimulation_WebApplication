package ToBeDeleted;

import java.util.*;
import java.util.concurrent.*;

public class Main {

//	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		ArrayList<Thread> myThreads = new ArrayList<Thread>();
		
		BlockingQueue<Product> tempQ1 = new ArrayBlockingQueue<Product>(10);
		BlockingQueue<Product> tempQ2 = new ArrayBlockingQueue<Product>(10);
		BlockingQueue<Product> tempQ3 = new ArrayBlockingQueue<Product>(10);
		generateProducts(6, tempQ1);
		Machine m1 = new Machine(1, 1, tempQ2, tempQ1);
		Machine m2 = new Machine(2, 2, tempQ3, tempQ2);
		
		ArrayList<Machine> systemMachines = new ArrayList<Machine>();
		systemMachines.add(m1);
		systemMachines.add(m2);
		
		
		for(Machine M : systemMachines) {
			Thread tempMachineThread = new Thread(M);
			tempMachineThread.start();
			myThreads.add(tempMachineThread);
		}
		
		while(tempQ3.size() != 6) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for(Machine M : systemMachines) {
			M.stop();
		}
		
		for(Thread tempThread : myThreads) {
			System.out.println(tempThread.getState().toString());
		}
		
//		try {
//			Thread.sleep(25000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		tempQ1.add(new Product(7));
		
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
