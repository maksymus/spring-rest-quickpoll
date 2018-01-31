package org.homenet.rest.quickpoll;

import org.homenet.rest.quickpoll.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScan(basePackages = "org.homenet.rest.quickpoll")
public class SpringRestQuickpollApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringRestQuickpollApplication.class, args);
	}
}
