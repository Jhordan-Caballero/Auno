package com.auno.sistema_recordatorios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SistemaRecordatoriosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaRecordatoriosApplication.class, args);
	}

}
