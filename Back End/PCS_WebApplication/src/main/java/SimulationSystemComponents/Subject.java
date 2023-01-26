package SimulationSystemComponents;

import java.util.ArrayList;

public abstract class Subject {
	
	ArrayList<Observer> observers;
	
	public Subject() {
		this.observers = new ArrayList<Observer>();
	}
	
	public void attach(Observer observer) {
		this.observers.add(observer);
	}

	public void detach(Observer observer) {
		this.observers.remove(observer);
	}
	
	public void notifyObservers() {
		for(Observer obs : this.observers) {
			if(obs.isReady()) 	// Balking DP
				obs.update();
			
		}
	}

}
