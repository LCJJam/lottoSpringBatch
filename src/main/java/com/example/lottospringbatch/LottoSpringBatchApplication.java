package com.example.lottospringbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class LottoSpringBatchApplication {


	public static void main(String[] args) {
		SpringApplication.run(LottoSpringBatchApplication.class, args);
	}

}
