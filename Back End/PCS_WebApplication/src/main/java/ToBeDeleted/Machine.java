package ToBeDeleted;

import java.util.concurrent.*;

public class Machine implements Runnable{
	
	private Product productUnderConstruction;
	private long buildingTime;
	private long remainingTime;
	private BlockingQueue<Product> outputQueue;
	private BlockingQueue<Product> inputQueue;
	private int id;
	
	public Machine(int id, long buildingTimeSeconds, BlockingQueue<Product> outputQueue, BlockingQueue<Product> inputQueue) {
		this.id = id;
		this.buildingTime = 1000 * buildingTimeSeconds;
		this.outputQueue = outputQueue;
		this.inputQueue = inputQueue;
	}
	
	public synchronized void operate() {
		while(!this.inputQueue.isEmpty()) {
//			System.out.println(this.inputQueue.toString());
			this.inputProduct();
			while(this.remainingTime > 0) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.remainingTime -= 1000;
				System.out.println("M" + this.id + ": " + "Remaining Time: " + this.remainingTime + " On " + this.productUnderConstruction.toString());
				System.out.println("M" + this.id + ": " + "Input Queue: " + this.inputQueue.toString());
				System.out.println("M" + this.id + ": " + "Output Queue: "+ this.outputQueue.toString());
			}
			this.outputProduct();
		}
			System.out.println("M" + this.id + ": " + "Stopped Execution");
			System.out.println(this.inputQueue.toString());
			System.out.println(this.outputQueue.toString());
	}
	
	public void outputProduct() {
		try {
			this.outputQueue.put(productUnderConstruction);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.productUnderConstruction = null;
	}
	
	public void inputProduct() {
		try {
			this.productUnderConstruction = this.inputQueue.take();
			this.remainingTime = this.buildingTime;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		this.operate();
	}
	
	
	
	

	
	
	

}
