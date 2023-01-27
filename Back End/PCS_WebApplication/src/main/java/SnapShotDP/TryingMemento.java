package SnapShotDP;

public class TryingMemento {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Originator org = new Originator();
		CareTaker care = new CareTaker();
		long time = 10000;
		String Stage = "Red, blue, green";
		StageAndTime st = new StageAndTime(time, Stage);
		org.setCurrentStage(st);
		care.add(org.save());
		
		
		org.revert(care.get(0));
		System.out.println("first saved Stage: " + (org.getCurrentStage()).toString());
	}

}
