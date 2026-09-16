package org.me.dibs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DibsApplication {
    public static void main(String[] args) {

        SpringApplication.run(DibsApplication.class, args);
    }

}
