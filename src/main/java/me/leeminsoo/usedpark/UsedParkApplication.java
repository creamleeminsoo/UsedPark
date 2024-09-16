package me.leeminsoo.usedpark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class UsedParkApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsedParkApplication.class,args);
    }
}
