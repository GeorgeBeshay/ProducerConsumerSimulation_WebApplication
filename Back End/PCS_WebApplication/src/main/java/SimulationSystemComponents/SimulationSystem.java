package SimulationSystemComponents;

import java.util.*;
import java.util.concurrent.*;

public class SimulationSystem implements Observer, SimulationSystemIF {
	
	private ArrayList<Machine> systemMachines;
	private HashMap<Integer, BlockingQueue<Product>> systemQueues;
	private int prodsCount; // Products count at initialization
	private boolean systemConditionFlag;
	private boolean ready;
	private boolean simulationUpdated;
	private ArrayList<String> simulationColors;
	
	
	public SimulationSystem(ArrayList<Machine> systemMachines, HashMap<Integer, BlockingQueue<Product>> systemQueues, int prodsCount) {
		this.systemMachines = systemMachines;
		this.systemQueues = systemQueues;
		this.prodsCount = this.systemQueues.get(0).size();
		this.systemConditionFlag = true;
		this.prepareSystem();
		this.ready = true;
		this.prodsCount = prodsCount;
		this.simulationUpdated = false;
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
		// ------------------- Separator -------------------
		String systemStatus = "-----------------------------------------------\nCurrent System Status:\n";
		systemStatus += "{\n";
		this.simulationColors = new ArrayList<String>(systemMachines.size());
		for(Machine M : this.systemMachines) {
			systemStatus += M.getCurrentStatus();
			if(M.getProductUnderConstruction() != null)
				this.simulationColors.add(M.getProductUnderConstruction().getProdColor());
			else
				this.simulationColors.add("#808080");
		}
		for(int key : this.systemQueues.keySet()) {
			systemStatus += "Q" + Integer.toString(key) + ": " + this.systemQueues.get(key).toString() + "\n";
		}
		systemStatus += "}";
		// ------------------- Separator -------------------
		System.out.println(systemStatus);
		this.ready = true;
		notifyAll();
		this.simulationUpdated = true;
		// ------------------- Separator -------------------
	}

	@Override
	public boolean isReady() {
		return this.ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public ArrayList<Machine> getSystemMachines() {
		return systemMachines;
	}

	public HashMap<Integer, BlockingQueue<Product>> getSystemQueues() {
		return systemQueues;
	}

	public int getProdsCount() {
		return prodsCount;
	}

	public boolean isSystemConditionFlag() {
		return systemConditionFlag;
	}

	public boolean isSimulationUpdated() {
		return simulationUpdated;
	}

	public ArrayList<String> getSimulationColors() {
		return simulationColors;
	}

	public void setSystemConditionFlag(boolean systemConditionFlag) {
		this.systemConditionFlag = systemConditionFlag;
	}
	
	
	
	
	

}
