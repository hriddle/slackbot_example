package com.antheajung.xpbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class XpBotApplication {
    public static void main(String args[]) {
        SpringApplication.run(XpBotApplication.class, args);
    }
}