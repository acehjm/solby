package me.solby.xboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScans({
        @ComponentScan("me.solby.xconfig.*")
})
@EnableAspectJAutoProxy
public class XbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(XbootApplication.class, args);
    }
}
