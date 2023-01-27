package SimulationSystemComponents;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


// same as the machine class but will be implemented better.
public class Machine extends Subject implements Runnable{
	
	// Product that is currently being assembled
	private Product productUnderConstruction;
	
	// Queue from which the machine pulls its products
	private BlockingQueue<Product> outputQueue;
	
	// Queue to which the machine push its products
	private BlockingQueue<Product> inputQueue;
	
	// Machine id
	private int id;
	
	// Machine Operating Status Flag
	private boolean flag;
	
	// Machine Operating Status Flag
		private boolean finished;
	
	// Machine Time to build a unit
	private long buildingTime;
	
	public Machine(int id, double buildingTimeSeconds, BlockingQueue<Product> outputQueue, BlockingQueue<Product> inputQueue) {
		super();
		this.id = id;
		this.buildingTime = 1000 * Double.doubleToLongBits(buildingTimeSeconds);
		System.out.println(this.buildingTime);
		this.outputQueue = outputQueue;
		this.inputQueue = inputQueue;
		this.flag = true;
		this.finished = false;
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
					this.productUnderConstruction = this.inputQueue.poll(100, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	public  void operate() {
		while(this.flag) {
			this.inputProduct();
			if(this.productUnderConstruction != null) {
				System.out.println("M" + this.id + ": " + "Working On " + this.productUnderConstruction.toString());
				this.notifyObservers();
				try {Thread.sleep(buildingTime);} catch (InterruptedException e) {e.printStackTrace();}
				this.outputProduct();
				this.notifyObservers();
			}
		}
		System.out.println("M" + this.id + ": " + "Stopped Execution");
		this.finished = true;
	}
	
	public void stop() {
		this.flag = false;
		while(!this.finished) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		this.operate();
	}

	public int getId() {
		return id;
	}
	
	public String getCurrentStatus() {
		if(this.productUnderConstruction != null)
			return "[M" + this.getId() + ": " + this.productUnderConstruction.toString() + "]\n";
		return "[M" + this.getId() + ":  Null]\n"; 
	}
	
	

}
