package org.example.bewerbungs_buddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class BewerbungsBuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BewerbungsBuddyApplication.class, args);
	}

	@GetMapping("/")
	public String index() {
		return "INDEX";
	}
}
