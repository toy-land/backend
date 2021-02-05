package com.openhack.toyland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ToyLandApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToyLandApplication.class, args);
    }
}
