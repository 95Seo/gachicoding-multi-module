package org.deco.gachicoding.infrastructure.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.deco.gachicoding.infrastructure.web.config")
public class GachicodingWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GachicodingWebApplication.class);
    }
}
