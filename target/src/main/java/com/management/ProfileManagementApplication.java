package com.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.management.repo.AdminRepo;
import com.management.repo.LandlordRepo;
import com.management.repo.TenantsRepo;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {TenantsRepo.class, LandlordRepo.class, AdminRepo.class})
@ComponentScan(basePackages = {"com.management.services"})
public class ProfileManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfileManagementApplication.class, args);
	}

}
