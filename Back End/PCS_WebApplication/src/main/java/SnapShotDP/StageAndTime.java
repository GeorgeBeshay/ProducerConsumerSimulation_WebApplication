package SnapShotDP;

public class StageAndTime {
	long time;
	Object Stage;
	
	public StageAndTime(Object stage) {
		super();
		this.time = System.currentTimeMillis();
		Stage = stage;
	}

	@Override
	public String toString() {
		return "StageAndTime [time=" + time + ", Stage=" + Stage + "]";
	}
	
}
