package com.futebol.gestao_time;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.futebol.gestao_time.repository")
@EntityScan(basePackages = "com.futebol.gestao_time.model")
public class GestaoDoGrupoVfcApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoDoGrupoVfcApplication.class, args);
	}

}
