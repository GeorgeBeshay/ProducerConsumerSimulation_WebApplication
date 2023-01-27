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
	
	public SystemAdapter(FrontSystem frontSystem) {
		this.frontSystem = frontSystem;
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
		this.backSystem = new SimulationSystem(systemMachines, systemQueues);
	}
	
	public double getRandomNumber() {
		Random random = new Random();
		return random.nextDouble(2, 7);
	}

	@Override
	public void generateSystem() {
		this.backSystem.generateSystem();
	}
	
	

}
