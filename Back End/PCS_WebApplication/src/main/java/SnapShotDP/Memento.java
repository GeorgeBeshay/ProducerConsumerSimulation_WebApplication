package SnapShotDP;

public class Memento {
	private int time; // time taken by machine to process one product
	private String color;
	
	public Memento(int time, String color) {
		super();
		this.time = time;
		this.color = color;
	}

	public Memento(String color) {
		super();
		this.color = color;
	}

	public Memento(int time) {
		super();
		this.time = time;
	}

	public int getTime() {
		return time;
	}


	public String getColor() {
		return color;
	}
	
}