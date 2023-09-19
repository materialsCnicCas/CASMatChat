package com.materials.gpt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.materials.gpt.mapper")
@SpringBootApplication(scanBasePackages = {"com.materials.gpt"})
public class MaterialsGptApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaterialsGptApplication.class, args);
    }

}