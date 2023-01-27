package SnapShotDP;

public class replay {
	long time1;
	long time2;
	long timeDifference;
	public void iterate(CareTaker care, Originator org) {
		for(int i = 0; i < care.size(); i++) {
			Memento currentMemento = care.get(i);
			org.revert(currentMemento);
			time1 = org.getCurrentStage().time;
			if(i < care.size() - 1) {
				Memento nextMemento = care.get(i+1);
				time2 = nextMemento.getStage().time;
				timeDifference= time2 - time1;	
				try {
					Thread.sleep(timeDifference);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else {
				
			}
			
			// send stage to front
		}
	}
}
