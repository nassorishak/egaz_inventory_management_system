package com.egaz.inventory.management.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class InventoryManagementSystemApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(
				InventoryManagementSystemApplication.class, args);

		Environment env = ctx.getEnvironment();
		String port = env.getProperty("server.port", "8080");

		System.out.println("\n========================================");
		System.out.println("  ✅ Application started successfully");
		System.out.println("  🌐 URL: http://localhost:" + port);
		System.out.println("  📦 Profile: " +
				String.join(",", env.getActiveProfiles()));
		System.out.println("========================================\n");
	}
}