package ToBeDeleted;

import java.util.*;
import java.util.concurrent.*;

public class SimulationSystem {
	
	ArrayList<Machine> systemMachines;
	ArrayList<BlockingQueue<Product>> systemQueues;
	int prodsCount; // Products count at initialization
	boolean systemConditionFlag;
	
	public SimulationSystem(ArrayList<Machine> systemMachines, ArrayList<BlockingQueue<Product>> systemQueues) {
		this.systemMachines = systemMachines;
		this.systemQueues = systemQueues;
		this.prodsCount = this.systemQueues.get(0).size();
		this.systemConditionFlag = true;
	}
	
	public void generateSystem() {
		// ----------------------- Separator -----------------------
		for(Machine tempMach: systemMachines) {
			Thread tempMachThread = new Thread(tempMach);
			tempMachThread.start();
			System.out.println("Mach " + tempMach.getId() + "Started Operating");
		}
		// ----------------------- Separator -----------------------
		BlockingQueue<Product> lastQueue = this.systemQueues.get(this.systemQueues.size() - 1);
		while(this.systemConditionFlag) {
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
			this.checkSystemConditionFlag(lastQueue);
		}
		// ----------------------- Separator -----------------------
		System.out.println("Turning Off The Machines");
		// ----------------------- Separator -----------------------
		for(Machine tempMach: systemMachines) {
			tempMach.stop();
		}
		// ----------------------- Separator -----------------------
		System.out.println("Simulation System has been Discarded Successfully.");
		// ----------------------- Separator -----------------------
	}
	
	public void checkSystemConditionFlag(BlockingQueue<Product> lastQueue) {
		if(lastQueue.size() == this.prodsCount) 
			this.systemConditionFlag = false;
	}
	
	
	public void update() {
		
	}
	

}
