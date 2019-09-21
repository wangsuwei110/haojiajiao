package com.education.hjj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
@EnableScheduling
public class HaojiajiaoApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(HaojiajiaoApplication.class, args);
	}

}
