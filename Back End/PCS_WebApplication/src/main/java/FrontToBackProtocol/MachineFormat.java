package FrontToBackProtocol;


/*
 * This class implements the format in which the machine is 
 * expected to be sent from the client side.
 */
public class MachineFormat {
	
	private int machineID;
	private int previousQueueID;
	private int nextQueueID;
	
	public MachineFormat() {
	}

	public int getMachineID() {
		return machineID;
	}

	public void setMachineID(int machineID) {
		this.machineID = machineID;
	}

	public int getPreviousQueueID() {
		return previousQueueID;
	}

	public void setPreviousQueueID(int previousQueueID) {
		this.previousQueueID = previousQueueID;
	}

	public int getNextQueueID() {
		return nextQueueID;
	}

	public void setNextQueueID(int nextQueueID) {
		this.nextQueueID = nextQueueID;
	}
	
	

}
