package com.example.kiroku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//DemoApplication은 Spring Boot 애플리케이션의 시작점이 되는 클래스이며,
// main 메소드를 통해 스프링 애플리케이션을 실행하는 역할
@SpringBootApplication
@EnableJpaAuditing
public class KirokuApplication {
	public static void main(String[] args) {
		SpringApplication.run(KirokuApplication.class, args);
	}
}
