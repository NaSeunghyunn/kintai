package com.kintai.kintai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KintaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KintaiApplication.class, args);
	}

}
