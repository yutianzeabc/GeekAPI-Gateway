package cc.geektip.gateway.provider.demo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: 服务提供者Demo启动类
 * @author: Fish
 * @date: 2024/6/10
 */
@SpringBootApplication
@EnableDubbo
public class RpcProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpcProviderApplication.class, args);
    }

}
