package SnapShotDP;

public class StageAndTime {
	long time;
	Object Stage;
	
	public StageAndTime(long time, Object stage) {
		super();
		this.time = time;
		Stage = stage;
	}

	@Override
	public String toString() {
		return "StageAndTime [time=" + time + ", Stage=" + Stage + "]";
	}
	
}
