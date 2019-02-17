package ch.fhnw.edu.eaf.usermgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UsermgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsermgmtApplication.class, args);
	}
}
