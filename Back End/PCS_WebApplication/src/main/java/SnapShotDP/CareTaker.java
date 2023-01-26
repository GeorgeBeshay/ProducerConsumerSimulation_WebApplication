package SnapShotDP;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {
	private List<Memento> TimeList = new ArrayList<Memento>();
	private List<Memento> ColorList = new ArrayList<Memento>();
	private List<Memento> AllMementoList = new ArrayList<Memento>();
	
	public void addTime(Memento time) {
		TimeList.add(time);
	}
	
	public void addColor(Memento color) {
		ColorList.add(color);
	}
	
	public void addAll(Memento memento) {
		AllMementoList.add(memento);
	}
	
	public Memento getTime(int index) {
		return TimeList.get(index);
	}
	
	public Memento getColor(int index) {
		return ColorList.get(index);
	}
	
	public Memento getAll(int index) {
		return AllMementoList.get(index);
	}
}
