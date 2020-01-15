package com.shipment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = { "com.shipment" })
@SpringBootApplication
public class ShipmentApp {
    public static void main(String[] args) {
        SpringApplication.run(ShipmentApp.class, args);
    }
}

