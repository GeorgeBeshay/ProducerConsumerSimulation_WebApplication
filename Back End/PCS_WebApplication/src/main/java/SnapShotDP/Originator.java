package SnapShotDP;

public class Originator {
	private int time;
	private String color;
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public Memento saveTimeToMemento() {
		return new Memento(time);
	}
	
	public Memento saveColorToMemento() {
		return new Memento(color);
	}
	
	public void getTimeFromMemento(Memento memento) {
		time = memento.getTime();
	}
	
	public void getColorFromMemento(Memento memento) {
		color = memento.getColor();
	}
}
