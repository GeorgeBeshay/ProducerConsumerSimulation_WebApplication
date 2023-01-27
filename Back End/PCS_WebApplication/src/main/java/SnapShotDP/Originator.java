package SnapShotDP;

public class Originator {
	private StageAndTime currentStage;
	
	public StageAndTime getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(StageAndTime currentStage) {
		this.currentStage = currentStage;
	}

	public Memento save() {
		return new Memento(currentStage);
	}
	
	public void revert(Memento newStage) {
		currentStage = newStage.getStage();
	}
	
//	private int time;
//	private String color;
//	public int getTime() {
//		return time;
//	}
//	public void setTime(int time) {
//		this.time = time;
//	}
//	public String getColor() {
//		return color;
//	}
//	public void setColor(String color) {
//		this.color = color;
//	}
//	
//	public Memento saveTimeToMemento() {
//		return new Memento(time);
//	}
//	
//	public Memento saveColorToMemento() {
//		return new Memento(color);
//	}
//	
//	public void getTimeFromMemento(Memento memento) {
//		time = memento.getTime();
//	}
//	
//	public void getColorFromMemento(Memento memento) {
//		color = memento.getColor();
//	}
//	
//	public Memento saveAllToMemento() {
//		return new Memento(time,color);
//	}
//	
//	public void getAllFromMemento(Memento memento) {
//		time = memento.getTime();
//		color = memento.getColor();
//	}
}
