package FrontToBackProtocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.*;
import java.util.Random;

import SimulationSystemComponents.*;

public class SystemAdapter implements SimulationSystemIF{
	
	private FrontSystem frontSystem;
	private SimulationSystem backSystem;
	private int prodsCount;
	
	public SystemAdapter(FrontSystem frontSystem, int prodsCount) {
		this.frontSystem = frontSystem;
		this.prodsCount = prodsCount;
	}
	
	public void adapt() {
		ArrayList<Machine> systemMachines = new ArrayList<Machine>();
		HashMap<Integer, BlockingQueue<Product>> systemQueues = new HashMap<Integer, BlockingQueue<Product>>();
		// ----------------------- Separator -----------------------
		for(MachineFormat frontMachine : frontSystem.getMachines()) {
			BlockingQueue<Product> outputQueue;
			BlockingQueue<Product> inputQueue;
			// ----------------------- Separator -----------------------
			if(systemQueues.get(frontMachine.getPreviousQueueID()) != null)
				inputQueue = systemQueues.get(frontMachine.getPreviousQueueID());
			else
				inputQueue = new ArrayBlockingQueue<Product>(1000);
			if(systemQueues.get(frontMachine.getNextQueueID()) != null)
				outputQueue = systemQueues.get(frontMachine.getNextQueueID());
			else
				outputQueue = new ArrayBlockingQueue<Product>(1000);
			// ----------------------- Separator -----------------------
			Machine backMachine = new Machine(frontMachine.getMachineID(), this.getRandomNumber(), outputQueue, inputQueue);
			// ----------------------- Separator -----------------------
			systemMachines.add(backMachine);
			systemQueues.put(frontMachine.getPreviousQueueID(), inputQueue);
			systemQueues.put(frontMachine.getNextQueueID(), outputQueue);
		}
		this.generateProducts(this.prodsCount, systemQueues.get(0));
		this.backSystem = new SimulationSystem(systemMachines, systemQueues, this.prodsCount);
	}
	
	public double getRandomNumber() {
		Random random = new Random();
		return random.nextDouble(2, 7);
	}

	@Override
	public void generateSystem() {
		this.backSystem.generateSystem();
	}
	
	public void generateProducts(int count, BlockingQueue<Product> Q) {
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
