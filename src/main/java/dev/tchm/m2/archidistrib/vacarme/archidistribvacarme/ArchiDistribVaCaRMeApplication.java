package dev.tchm.m2.archidistrib.vacarme.archidistribvacarme;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ArchiDistribVaCaRMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArchiDistribVaCaRMeApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
