package SnapShotDP;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {
	private List<Memento> StageList = new ArrayList<Memento>(); 
	
	public void add(Memento currentStage){
		StageList.add(currentStage);
	}
	
	public Memento get(int Index) {
		return StageList.get(Index);	
	}
	
	public int size() {
		return StageList.size();
	}

	public void showStages() {
		for(Memento M : StageList) {
			System.out.print(M.getStage().toString() + ", ");
		}
		System.out.println();
	}
	
	
}
