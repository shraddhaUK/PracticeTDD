package com.exchangeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan
@Configuration
@EnableFeignClients
public class    BitcoinExchangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BitcoinExchangeApplication.class, args);
    }

}