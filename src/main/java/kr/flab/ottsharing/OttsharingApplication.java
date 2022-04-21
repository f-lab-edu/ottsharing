package kr.flab.ottsharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OttsharingApplication {

    public static void main(String[] args) {
        SpringApplication.run(OttsharingApplication.class, args);
    }

}
