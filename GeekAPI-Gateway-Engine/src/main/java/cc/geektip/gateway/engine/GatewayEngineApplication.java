package cc.geektip.gateway.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GatewayEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayEngineApplication.class, args);
    }

}
