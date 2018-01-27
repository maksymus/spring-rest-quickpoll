package org.homenet.rest.quickpoll;

import org.homenet.rest.quickpoll.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringRestQuickpollApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringRestQuickpollApplication.class, args);
	}
}
