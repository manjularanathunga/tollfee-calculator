package com.smr;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@Slf4j
@SpringBootApplication
public class TollfeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TollfeeApplication.class, args);
        log.info("Toll fee calculation REST web service has been started at "+ new Date());
    }
}
