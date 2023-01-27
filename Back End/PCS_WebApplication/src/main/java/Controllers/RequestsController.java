package Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import FrontToBackProtocol.*;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/server/")
@RestController
public class RequestsController {
	
	@Autowired
	private ServerCore myServerCore = ServerCore.getServerCoreInstance();
	
	@PostMapping(value = {"check"})
	public boolean checkServerStatus() {
		return true;
	}
	
	

}
