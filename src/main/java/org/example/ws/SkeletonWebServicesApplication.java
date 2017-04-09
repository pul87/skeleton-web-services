package org.example.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SkeletonWebServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkeletonWebServicesApplication.class, args);
	}
}
