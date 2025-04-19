package com.zust.shareWise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ShareWiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShareWiseApplication.class, args);
	}

}
