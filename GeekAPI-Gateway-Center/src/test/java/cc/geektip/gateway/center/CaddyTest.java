package cc.geektip.gateway.center;

import cc.geektip.gateway.center.application.service.ILoadBalanceService;
import cc.geektip.gateway.center.domain.loadbalance.model.aggregates.CaddyConfig;
import cc.geektip.gateway.center.domain.loadbalance.model.vo.ProxyConfigVO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Fish
 * @date: 2024/6/9
 */
@SpringBootTest
public class CaddyTest {
    @Resource
    private ILoadBalanceService loadBalanceService;
    @Test
    public void test() throws Exception {
        CaddyConfig caddyConfig = new CaddyConfig();
        ProxyConfigVO proxyConfigVO = new ProxyConfigVO();
        proxyConfigVO.setName("10001");
        proxyConfigVO.setServers(List.of("192.168.0.1:8099", "192.168.0.2:8099"));
        caddyConfig.setProxyConfigs(new ArrayList<>());
        loadBalanceService.updateCaddyConfig(caddyConfig);
    }
}
