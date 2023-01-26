package ToBeDeleted;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


// same as the machine class but will be implemented better.
public class systemMachine {
	
	// Product that is currently being assembled
	private Product productUnderConstruction;
	
	// Queue from which the machine pulls its products
	private BlockingQueue<Product> outputQueue;
	
	// Queue to which the machine push its products
	private BlockingQueue<Product> inputQueue;
	
	// Machine id
	private int id;
	
	// Machine Operating Status Flag
	private boolean flag = true;
	
	// Machine Time to build a unit
	private long buildingTime;
	
	// Remaining time to finish the current product
	private long remainingTime;
	
	public systemMachine(int id, long buildingTimeSeconds, BlockingQueue<Product> outputQueue, BlockingQueue<Product> inputQueue) {
		this.id = id;
		this.buildingTime = 1000 * buildingTimeSeconds;
		this.outputQueue = outputQueue;
		this.inputQueue = inputQueue;
	}
	
	public void outputProduct() {
		try {
			this.outputQueue.put(productUnderConstruction);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.productUnderConstruction = null;
	}
	
	public void inputProduct() {
			try {
				while(this.flag && this.productUnderConstruction == null) 
					this.productUnderConstruction = this.inputQueue.poll(1000, TimeUnit.MILLISECONDS);
				if(this.productUnderConstruction != null)
					this.remainingTime = this.buildingTime;
				else
					this.remainingTime = 0;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	public  void operate() {
		while(this.flag) {
			this.inputProduct();
			while(this.remainingTime > 0) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.remainingTime -= 1000;
				System.out.println("M" + this.id + ": " + "Remaining Time: " + this.remainingTime + " On " + this.productUnderConstruction.toString());
			}
			if(this.productUnderConstruction != null) {
				this.outputProduct();
				System.out.println(this.inputQueue.toString());
				System.out.println(this.outputQueue.toString());
				System.out.println(this.flag);
			}
		}
			System.out.println("M" + this.id + ": " + "Stopped Execution");
	}
	
	public void stop() {
		this.flag = false;
	}

}
