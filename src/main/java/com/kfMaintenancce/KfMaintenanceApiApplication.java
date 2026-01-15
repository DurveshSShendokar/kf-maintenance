package com.kfMaintenancce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@SpringBootConfiguration
@EnableScheduling
public class KfMaintenanceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KfMaintenanceApiApplication.class, args);
	}

}
