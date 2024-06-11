package cc.geektip.gateway.starter.application;

import cc.geektip.gateway.core.mapping.HttpCommandType;
import cc.geektip.gateway.core.mapping.HttpStatement;
import cc.geektip.gateway.core.session.Configuration;
import cc.geektip.gateway.starter.config.GatewayServiceProperties;
import cc.geektip.gateway.starter.domain.model.aggregates.ApplicationSystemRichInfo;
import cc.geektip.gateway.starter.domain.model.vo.ApplicationInterfaceMethodVO;
import cc.geektip.gateway.starter.domain.model.vo.ApplicationInterfaceVO;
import cc.geektip.gateway.starter.domain.model.vo.ApplicationSystemVO;
import cc.geektip.gateway.starter.domain.service.GatewayCenterService;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.List;

/**
 * @description: 网关应用：与 Spring 链接，调用网关注册和接口拉取
 * @author: Fish
 * @date: 2024/5/30
 */
@Slf4j
@RequiredArgsConstructor
public class GatewayApplication implements ApplicationContextAware, ApplicationListener<ContextClosedEvent> {

    private final GatewayServiceProperties properties;

    private final GatewayCenterService gatewayCenterService;

    private final Configuration configuration;

    private final Channel gatewaySocketServerChannel;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        try {
            // 1. 注册网关服务；每一个用于转换 HTTP 协议泛化调用到 RPC 接口的网关都是一个算力，这些算力需要注册网关配置中心
            gatewayCenterService.doRegister(
                    properties.getAddress(),
                    properties.getGroupId(),
                    properties.getGatewayId(),
                    properties.getGatewayName(),
                    properties.getGatewayAddress());
            addMappers("");
        } catch (Exception e) {
            log.error("网关服务启动失败，停止服务。{}", e.getMessage(), e);
            throw e;
        }
    }

    public void addMappers(String systemId) {
        // 2. 拉取网关配置；每个网关算力都会在注册中心分配上需要映射的RPC服务信息，包括；系统、接口、方法
        ApplicationSystemRichInfo applicationSystemRichInfo = gatewayCenterService.pullApplicationSystemRichInfo(properties.getAddress(), properties.getGatewayId(), systemId);
        List<ApplicationSystemVO> applicationSystemVOList = applicationSystemRichInfo.getApplicationSystemVOList();
        if (applicationSystemVOList.isEmpty()) {
            log.warn("网关{}服务注册映射为空，请检查是否正确从注册中心拉取到此网关算力所需的配置数据！", systemId);
            return;
        }
        for (ApplicationSystemVO system : applicationSystemVOList) {
            List<ApplicationInterfaceVO> interfaceList = system.getInterfaceList();
            for (ApplicationInterfaceVO itf : interfaceList) {
                // 2.1 创建配置信息加载注册
                configuration.registryConfig(system.getSystemId(), system.getSystemRegistry(), itf.getInterfaceId(), itf.getInterfaceVersion());
                List<ApplicationInterfaceMethodVO> methodList = itf.getMethodList();
                // 2.2 注册系统服务接口信息
                for (ApplicationInterfaceMethodVO itm : methodList) {
                    HttpStatement httpStatement = new HttpStatement(
                            system.getSystemId(),
                            itf.getInterfaceId(),
                            itm.getMethodId(),
                            itm.getParameterType(),
                            itm.getUri(),
                            HttpCommandType.valueOf(itm.getHttpCommandType()),
                            itm.isAuth());
                    configuration.addMapper(httpStatement);
                    log.info("网关服务注册映射 -> 系统：{} 接口：{} 方法：{}", system.getSystemId(), itf.getInterfaceId(), itm.getMethodId());
                }
            }
        }
    }

    @Override
    public void onApplicationEvent(@NotNull ContextClosedEvent event) {
        try {
            gatewayCenterService.doUnregister(
                    properties.getAddress(),
                    properties.getGroupId(),
                    properties.getGatewayId(),
                    properties.getGatewayName(),
                    properties.getGatewayAddress()
            );
            log.info("应用容器关闭，网关服务注销成功，groupId：{} gatewayId：{}", properties.getGroupId(), properties.getGatewayId());
            if (gatewaySocketServerChannel.isActive()) {
                gatewaySocketServerChannel.close();
                log.info("应用容器关闭，API网关服务关闭。LocalAddress：{}", gatewaySocketServerChannel.localAddress());
            }
        } catch (Exception e) {
            log.error("网关服务关闭失败，停止服务。{}", e.getMessage(), e);
            throw e;
        }
    }

    public void handleMessage(Object message) {
        log.info("【事件通知】接收注册中心推送消息 message：{}", message);
        addMappers(message.toString().substring(1, message.toString().length() - 1));
    }

}
