package Programming2Assignments.PCS_WebApplication;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import FrontToBackProtocol.*;

@Service
public class ServerCore {
	
	private static ServerCore serverCore;
	private static SystemAdapter currentSystemAdapter;
	
	private ServerCore() {}
	
	public boolean initializeSimulation(FrontSystem frontSystem, int prodsCount) {
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
		return currentSystemAdapter.getBackSystem().getSimulationColors();
	}

	
	public static ServerCore getServerCoreInstance() {
		if(ServerCore.serverCore == null) 
			serverCore = new ServerCore();
		return ServerCore.serverCore;
	}
	
	public boolean finishedSimulation() {
		return !(currentSystemAdapter.getBackSystem().isSystemConditionFlag());
	}
	
	

}
