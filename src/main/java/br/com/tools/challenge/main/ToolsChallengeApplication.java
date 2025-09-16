package br.com.tools.challenge.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.com.tools.challenge")
@EntityScan(basePackages = "br.com.tools.challenge")
@ComponentScan(basePackages = {"br.com.tools.challenge"})
public class ToolsChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToolsChallengeApplication.class, args);
	}
}
