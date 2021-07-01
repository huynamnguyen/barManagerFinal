package bm.barManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"bm.webserver"})
public class BarManagerApplication {
	
	public static void main(String[] args) {
		// Responsible for the web server: bm.webserver.RESTController
		// Responsible for the room's management: bm.rooms.RoomManager (see RoomManagerInfra for functionality)
		SpringApplication.run(BarManagerApplication.class, args);
	}

}
