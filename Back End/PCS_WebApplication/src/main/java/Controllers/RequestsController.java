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
import java.util.*;
import SnapShotDP.*;

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
	public void initializeSimulation(@RequestBody FrontSystem frontSystem, @PathVariable int prodsCount) {
		this.myServerCore.initializeSimulation(frontSystem, prodsCount);
	}
	
	@PostMapping(value= {"update"})
	public ArrayList<String> updateSimulation() {
		return this.myServerCore.updateSimulation();
	}
	
	@PostMapping(value= {"finished"})
	public boolean finishedSimuation() {
		return this.myServerCore.finishedSimulation();
	}
	
	@PostMapping(value= {"stop"})
	public void stopSimulation() {
		this.myServerCore.stopSimulation();
	}
	
	@PostMapping(value= {"saveStage"})
	public void saveStage(@RequestBody Object konvaStage) {
		this.myServerCore.saveStage(konvaStage);
	}
	
	@PostMapping(value = {"replay"})
	public List<Memento> replay(){
		return this.myServerCore.replay();
	}
	

}
