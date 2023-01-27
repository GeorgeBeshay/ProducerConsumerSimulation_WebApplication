package Programming2Assignments.PCS_WebApplication;

import org.springframework.stereotype.Service;
import FrontToBackProtocol.*;

@Service
public class ServerCore {
	
	private static ServerCore serverCore;
	private static SystemAdapter currentSystemAdapter;
	
	private ServerCore() {}
	
	public boolean initializeSimulation(FrontSystem frontSystem, int prodsCount) {
		currentSystemAdapter = new SystemAdapter(frontSystem, prodsCount); 
		currentSystemAdapter .adapt();
		currentSystemAdapter .generateSystem();
		return true;
	}
	
	public static ServerCore getServerCoreInstance() {
		if(ServerCore.serverCore == null) 
			serverCore = new ServerCore();
		return ServerCore.serverCore;
	}

}
