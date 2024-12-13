package com.example.jpaloadingstrategies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JpaLoadingStrategiesApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaLoadingStrategiesApplication.class, args);
    }

}
