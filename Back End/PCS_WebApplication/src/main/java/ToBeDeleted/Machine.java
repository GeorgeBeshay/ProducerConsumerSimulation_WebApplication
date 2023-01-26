package ToBeDeleted;

import java.util.concurrent.*;

public class Machine implements Runnable{
	
	private Product productUnderConstruction;
	private long buildingTime;
	private long remainingTime;
	private BlockingQueue<Product> outputQueue;
	private BlockingQueue<Product> inputQueue;
	private int id;
	private boolean flag = true;
	
	
	public Machine(int id, long buildingTimeSeconds, BlockingQueue<Product> outputQueue, BlockingQueue<Product> inputQueue) {
		this.id = id;
		this.buildingTime = 1000 * buildingTimeSeconds;
		this.outputQueue = outputQueue;
		this.inputQueue = inputQueue;
	}
	
	public void stop() {
		this.flag = false;
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
	//			System.out.println("M" + this.id + ": " + "Stopped Execution");
				System.out.println(this.inputQueue.toString());
				System.out.println(this.outputQueue.toString());
				System.out.println(this.flag);
			}
		}
			System.out.println("M" + this.id + ": " + "Stopped Execution");
//			System.out.println(this.inputQueue.toString());
//			System.out.println(this.outputQueue.toString());
	}
	
	public Product getProductUnderConstruction() {
		return productUnderConstruction;
	}

	public void setProductUnderConstruction(Product productUnderConstruction) {
		this.productUnderConstruction = productUnderConstruction;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		
//		while(this.inputQueue.size() == 0) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
			try {
				while(this.flag && this.productUnderConstruction == null) 
					this.productUnderConstruction = this.inputQueue.poll(1000, TimeUnit.MILLISECONDS);
				if(this.productUnderConstruction != null)
					this.remainingTime = this.buildingTime;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	@Override
	public void run() {
		this.operate();
	}
	
	
	
	

	
	
	

}
