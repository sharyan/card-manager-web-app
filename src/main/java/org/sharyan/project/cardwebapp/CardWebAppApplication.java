package org.sharyan.project.cardwebapp;

import org.sharyan.project.cardwebapp.config.properties.PersistenceProperties;
import org.sharyan.project.cardwebapp.config.properties.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {PersistenceProperties.class, SecurityProperties.class})
public class CardWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardWebAppApplication.class, args);
	}
}
