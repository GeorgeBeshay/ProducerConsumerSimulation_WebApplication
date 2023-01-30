package SnapShotDP;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CareTaker {
	private ArrayList<Momento> momentos = new ArrayList<Momento>();
	
	public void add(Momento currentStage){
		momentos.add(currentStage);
	}
	
	public Momento get(int Index) {
		return momentos.get(Index);	
	}
	
	public int size() {
		return momentos.size();
	}

	public void showStages() {
		for(Momento M : momentos) {
			System.out.print(M.toString() + "\n");
		}
		System.out.println();
	}

	public List<Momento> getStageList() {
		return momentos;
	}
	
	public void replay() {
		for(int i = 0 ; i < this.momentos.size()-1 ; i++) {
			System.out.println(this.momentos.get(i).toString());
			try {
				Thread.sleep(this.momentos.get(i+1).getMomenoTime() - this.momentos.get(i).getMomenoTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(this.momentos.get(this.momentos.size()-1).toString());
	}

	public ArrayList<Momento> getMomentos() {
		return momentos;
	}

	public void setMomentos(ArrayList<Momento> momentos) {
		this.momentos = momentos;
	}
	
	public void showLastMomento() {
		System.out.println(this.momentos.get(this.momentos.size()-1).toString());
	}
	
	
	
}
