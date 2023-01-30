package Programming2Assignments.PCS_WebApplication;

import java.util.ArrayList;
import java.util.*;

import org.springframework.stereotype.Service;
import FrontToBackProtocol.*;
import SnapShotDP.*;

@Service
public class ServerCore {
	
	private static ServerCore serverCore;
	private static SystemAdapter currentSystemAdapter;
	private static CareTaker careTaker;
	
	
	private ServerCore() {}
	
	public boolean initializeSimulation(FrontSystem frontSystem, int prodsCount) {
		careTaker = new CareTaker();
		currentSystemAdapter = new SystemAdapter(frontSystem, prodsCount); 
		currentSystemAdapter.adapt();
		Thread initializationThread = new Thread(currentSystemAdapter);
		initializationThread.start();
		return true;
	}
	
	public ArrayList<String> updateSimulation(){
		while(!currentSystemAdapter.getBackSystem().isReady() || !currentSystemAdapter.getBackSystem().isSimulationUpdated()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		currentSystemAdapter.getBackSystem().setSimulationUpdated(false);
		return currentSystemAdapter.getBackSystem().getSimulationColors();
	}
	
	public ArrayList<Integer> queuesCount(){
		return currentSystemAdapter.getBackSystem().getQueuesCount();
	}

	
	public static ServerCore getServerCoreInstance() {
		if(ServerCore.serverCore == null) 
			serverCore = new ServerCore();
		return ServerCore.serverCore;
	}
	
	public boolean finishedSimulation() {
		boolean flag = !(currentSystemAdapter.getBackSystem().isSystemConditionFlag());
		System.out.println("stages:");
		careTaker.showStages();
		return flag;
	}
	
	public void stopSimulation() {
		currentSystemAdapter.getBackSystem().setSystemConditionFlag(false);
	}
	
}
