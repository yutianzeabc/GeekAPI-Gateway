package cc.geektip.gateway.center;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: API 网关中心启动类
 * @author: Fish
 * @date: 2024/5/28
 */
@SpringBootApplication
@MapperScan("cc.geektip.gateway.center.infrastructure.dao")
public class GatewayCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayCenterApplication.class, args);
    }
}
