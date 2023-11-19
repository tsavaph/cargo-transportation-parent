package ru.tsavaph.cargotransportationauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("ru.tsavaph.cargotransportationauthservice.feign")
public class CargoTransportationAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CargoTransportationAuthServiceApplication.class, args);
    }

}
