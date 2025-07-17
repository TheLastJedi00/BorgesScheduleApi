package com.borges.Scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@SpringBootApplication
public class BorgesSchedulerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BorgesSchedulerApiApplication.class, args);
		System.out.println("Borges Schedule API is started");
	}


}
