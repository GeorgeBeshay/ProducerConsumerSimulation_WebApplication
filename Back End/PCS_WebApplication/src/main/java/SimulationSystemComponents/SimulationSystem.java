package SimulationSystemComponents;

import java.util.*;
import java.util.concurrent.*;

public class SimulationSystem implements Observer {
	
	private ArrayList<Machine> systemMachines;
	private HashMap<Integer, BlockingQueue<Product>> systemQueues;
	private int prodsCount; // Products count at initialization
	private boolean systemConditionFlag;
	private boolean ready;
	
	
	public SimulationSystem(ArrayList<Machine> systemMachines, HashMap<Integer, BlockingQueue<Product>> systemQueues) {
		this.systemMachines = systemMachines;
		this.systemQueues = systemQueues;
		this.prodsCount = this.systemQueues.get(0).size();
		this.systemConditionFlag = true;
		this.prepareSystem();
		this.ready = true;
	}
	
	public void prepareSystem() {
		for(Machine M : this.systemMachines) {
			M.attach(this);
		}
	}
	
	public void generateSystem() {
		// ----------------------- Separator -----------------------
		for(Machine tempMach: systemMachines) {
			Thread tempMachThread = new Thread(tempMach);
			tempMachThread.start();
			System.out.println("M" + tempMach.getId() + ": Started Operating");
		}
		// ----------------------- Separator -----------------------
		BlockingQueue<Product> lastQueue = this.systemQueues.get(this.systemQueues.size() - 1);
		while(this.systemConditionFlag) {
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
			this.checkSystemConditionFlag(lastQueue);
		}
		// ----------------------- Separator -----------------------
		System.out.println("Turning Off The Machines");
		this.turnOffMachines();
		// ----------------------- Separator -----------------------
		System.out.println("Simulation System has been Discarded Successfully.");
		// ----------------------- Separator -----------------------
	}
	
	public void turnOffMachines() {
		for(Machine tempMach: systemMachines) {
			tempMach.stop();
		}
	}
	
	public void checkSystemConditionFlag(BlockingQueue<Product> lastQueue) {
		if(lastQueue.size() == this.prodsCount) 
			this.systemConditionFlag = false;
	}

	@Override
	public synchronized void update() {
		this.ready = false;
		String systemStatus = "-----------------------------------------------\nCurrent System Status:\n";
		systemStatus += "{\n";
		for(Machine M : this.systemMachines) {
			systemStatus += M.getCurrentStatus(); 
		}
		for(int key : this.systemQueues.keySet()) {
			systemStatus += "Q" + Integer.toString(key) + ": " + this.systemQueues.get(key).toString() + "\n";
		}
		systemStatus += "}";
		System.out.println(systemStatus);
		this.ready = true;
		notifyAll();
	}

	@Override
	public boolean isReady() {
		return this.ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}
	

}
