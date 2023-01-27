package SnapShotDP;

public class Memento {
	private StageAndTime currentStage;
	
	public Memento(StageAndTime currentStage) {
		super();
		this.currentStage = currentStage;
	}

	public StageAndTime getStage() {
		return currentStage;
	}
	
	
	
}
