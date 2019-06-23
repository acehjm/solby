package me.solby.xquartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * me.solby.xquartz
 *
 * @author majhdk
 * @date 2019-06-16
 */
@EnableScheduling
@SpringBootApplication
public class XquartzApplication {

    public static void main(String[] args) {
        SpringApplication.run(XquartzApplication.class, args);
    }

}
