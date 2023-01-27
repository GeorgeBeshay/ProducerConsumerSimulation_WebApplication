package Programming2Assignments.PCS_WebApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "Controllers", "Programming2Assignments.PCS_WebApplication", "FrontToBackProtocol"} )
public class PcsWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(PcsWebApplication.class, args);
	}

}
