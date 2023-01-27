package FrontToBackProtocol;

import org.springframework.stereotype.Service;

@Service
public class ServerCore {
	
	private static ServerCore serverCore;
	
	private ServerCore() {}
	
	public static ServerCore getServerCoreInstance() {
		if(ServerCore.serverCore == null) 
			serverCore = new ServerCore();
		return ServerCore.serverCore;
	}

}
