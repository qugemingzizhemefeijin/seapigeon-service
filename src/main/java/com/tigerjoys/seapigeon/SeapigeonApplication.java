package com.tigerjoys.seapigeon;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SeapigeonApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(SeapigeonApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("启动成功");
	}

}
