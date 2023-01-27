package SnapShotDP;

public class StageAndTime {

	long time;
	Object Stage;
	
	public StageAndTime(long time,Object Stage) {
		this.time=time;
		this.Stage=Stage;
	}
	
	public StageAndTime(Object stage) {
		super();
		this.time = System.currentTimeMillis();
		Stage = stage;
	}

	@Override
	public String toString() {
		return "StageAndTime [time=" + time + ", Stage=" + Stage + "]";
	}
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public Object getStage() {
		return Stage;
	}

	public void setStage(Object stage) {
		Stage = stage;
	}
	
}
