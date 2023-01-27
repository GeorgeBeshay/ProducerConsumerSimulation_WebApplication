package Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import FrontToBackProtocol.*;
import Programming2Assignments.PCS_WebApplication.ServerCore;

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
	
	@PostMapping(value= {"initialize/{prodsCount}"})
	public boolean initializeSimulation(@RequestBody FrontSystem frontSystem, @PathVariable int prodsCount) {
		return this.myServerCore.initializeSimulation(frontSystem, prodsCount);
	}
	
	

}
