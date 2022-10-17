package pl.edu.pwr.solarmonitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SolarMonitoringApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolarMonitoringApplication.class, args);
	}
}