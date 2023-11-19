package ru.tsavaph.cargotransportationuserinfoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class CargoTransportationUserInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CargoTransportationUserInfoApplication.class, args);
    }

}
