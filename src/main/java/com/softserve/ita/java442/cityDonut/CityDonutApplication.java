package com.softserve.ita.java442.cityDonut;

import com.softserve.ita.java442.cityDonut.dto.media.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
public class CityDonutApplication {
    public static void main(String[] args) {
        SpringApplication.run(CityDonutApplication.class, args);
    }
}
