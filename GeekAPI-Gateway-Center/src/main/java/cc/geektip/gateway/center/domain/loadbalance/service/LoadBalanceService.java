package cc.geektip.gateway.center.domain.loadbalance.service;

import cc.geektip.gateway.center.application.ILoadBalanceService;
import cc.geektip.gateway.center.domain.loadbalance.model.aggregates.CaddyConfig;
import cc.geektip.gateway.center.domain.loadbalance.model.event.GatewayServerRegisteredEvent;
import cc.geektip.gateway.center.domain.loadbalance.model.vo.*;
import cc.geektip.gateway.center.domain.manage.model.vo.GatewayServerDetailVO;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Fish
 * @date: 2024/6/6
 */
@Slf4j
@Service
public class LoadBalanceService implements ILoadBalanceService {

    @Value("${caddy.api}")
    private String caddyApi;

    @Override
    public void updateCaddyConfig(CaddyConfig caddyConfig) throws Exception {
        List<ProxyConfigVO> configs = caddyConfig.getProxyConfigs();
        List<RouteObjectVO> routes = configs.stream().map(this::buildRouteObject).toList();
        // PATCH请求更新Caddy配置
        HttpRequest request = HttpRequest.patch(caddyApi + "/config/apps/http/servers/srv0/routes");
        request.body(JSON.toJSONString(routes));
        request.contentType("application/json");
        try (var response = request.execute()) {
            if (response.getStatus() != 200) {
                throw new Exception("更新Caddy配置失败, HTTP状态码: " + response.getStatus() + ", 响应内容: " + response.body());
            }
        } catch (Exception e) {
            throw new Exception("更新Caddy配置失败", e);
        }
    }

    public RouteObjectVO buildRouteObject(ProxyConfigVO config) {
        RouteObjectVO routeObjectVO = new RouteObjectVO();
        routeObjectVO.setId(config.getName());
        routeObjectVO.setGroup("default");
        HandleVO handleRewrite = new HandleVO();
        handleRewrite.setHandler("rewrite");
        handleRewrite.setStripPathPrefix("/" + config.getName());
        HandleVO handleProxy = new HandleVO();
        handleProxy.setHandler("reverse_proxy");
        handleProxy.setLoadBalancing(new LoadBalancingVO("least_conn"));
        List<UpstreamVO> upstreamVOList = config.getServers().stream().map(UpstreamVO::new).toList();
        handleProxy.setUpstreams(upstreamVOList);
        routeObjectVO.setHandle(List.of(handleRewrite, handleProxy));
        MatchVO matchVO = new MatchVO("/" + config.getName() + "/*");
        routeObjectVO.setMatch(List.of(matchVO));
        return routeObjectVO;
    }

    @Async
    @EventListener(GatewayServerRegisteredEvent.class)
    public void onGatewayServerNodeRegisteredEvent(GatewayServerRegisteredEvent event) {
        // 1. 读取最新网关算力数据
        List<GatewayServerDetailVO> gatewayServerDetailVOList = event.getGatewayServerDetailVOList();
        // 2. 组建Caddy反向代理配置
        List<ProxyConfigVO> proxyConfigVOList = gatewayServerDetailVOList.stream()
                .collect(Collectors.groupingBy(GatewayServerDetailVO::getGroupId))
                .entrySet().stream()
                .map(entry -> new ProxyConfigVO(entry.getKey(), entry.getValue().stream()
                        .map(GatewayServerDetailVO::getGatewayAddress)
                        .toList()))
                .toList();
        CaddyConfig caddyConfig = new CaddyConfig(proxyConfigVOList);
        // 3. 刷新Caddy配置
        try {
            updateCaddyConfig(caddyConfig);
            log.info("刷新Caddy配置完成");
        } catch (Exception e) {
            log.error("刷新Caddy配置异常：", e);
        }
    }

}
