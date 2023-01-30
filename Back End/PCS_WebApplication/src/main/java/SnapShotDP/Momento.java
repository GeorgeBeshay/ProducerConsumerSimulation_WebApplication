package SnapShotDP;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import SimulationSystemComponents.*;

public class Momento {
	
	private ArrayList<Machine> machinesStatus;
	private HashMap<Integer, BlockingQueue<Product>> queuesStatus;
	private long momenoTime;
	
	public Momento(ArrayList<Machine> machines, HashMap<Integer, BlockingQueue<Product>> queues) {
		this.machinesStatus = new ArrayList<Machine>();
		this.queuesStatus = new HashMap<Integer, BlockingQueue<Product>>();
		for(Machine m : machines) 
			this.machinesStatus.add(m.clone());
		for(Integer i : queues.keySet()) {
			BlockingQueue<Product> tempQ = new ArrayBlockingQueue<Product>(1000);
			BlockingQueue<Product> q = queues.get(i);
			Iterator<Product> iterator = q.iterator();
			while(iterator.hasNext()) {
				try {
					tempQ.put(iterator.next().clone());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.queuesStatus.put(i, tempQ);
		}
		this.momenoTime = System.currentTimeMillis();
	}
	
	@Override
	public String toString() {
		String ans = "{\n";
		ans += "\tMachines: " ;
		for(Machine M : this.machinesStatus) { 
			ans += "M" + M.getId() + ": ";
			if(M.getProductUnderConstruction() != null) 
				ans += M.getProductUnderConstruction().toString() + " - ";
			else 
				ans +="Null - ";
		}
		ans += "\n\tQueues: ";
		Iterator<Integer> iterator = this.queuesStatus.keySet().iterator();
		while(iterator.hasNext()) {
			int temp = iterator.next();
			ans += "Q" + Integer.toString(temp) + ": " + this.queuesStatus.get(temp).toString() + " - ";
		}
		ans += "\n\tMomento Time: " + this.momenoTime;
		ans += "\n}\n";
		return ans;
	}

	public ArrayList<Machine> getMachinesStatus() {
		return machinesStatus;
	}

	public void setMachinesStatus(ArrayList<Machine> machinesStatus) {
		this.machinesStatus = machinesStatus;
	}

	public HashMap<Integer, BlockingQueue<Product>> getQueuesStatus() {
		return queuesStatus;
	}

	public void setQueuesStatus(HashMap<Integer, BlockingQueue<Product>> queuesStatus) {
		this.queuesStatus = queuesStatus;
	}

	public long getMomenoTime() {
		return momenoTime;
	}

	public void setMomenoTime(long momenoTime) {
		this.momenoTime = momenoTime;
	}
	
	

}
