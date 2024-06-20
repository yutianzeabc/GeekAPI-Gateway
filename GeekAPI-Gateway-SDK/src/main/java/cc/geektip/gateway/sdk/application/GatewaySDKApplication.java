package cc.geektip.gateway.sdk.application;

import cc.geektip.gateway.sdk.annotation.ApiProducerClazz;
import cc.geektip.gateway.sdk.annotation.ApiProducerMethod;
import cc.geektip.gateway.sdk.config.GatewaySDKProperties;
import cc.geektip.gateway.sdk.domain.service.GatewayCenterService;
import cc.geektip.gateway.sdk.exception.GatewayException;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

/**
 * @description: 网关SDK自动注册服务
 * @author: Fish
 * @date: 2024/6/4
 */
@Slf4j
@RequiredArgsConstructor
public class GatewaySDKApplication implements BeanPostProcessor {

    private final GatewaySDKProperties properties;

    private final GatewayCenterService gatewayCenterService;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        ApiProducerClazz apiProducerClazz = bean.getClass().getAnnotation(ApiProducerClazz.class);
        if (null == apiProducerClazz) return bean;
        // 1. 系统信息
        log.info("\n应用注册：系统信息 \nsystemId: {} \nsystemName: {} \nsystemType: {} \nsystemRegistry: {}", properties.getSystemId(), properties.getSystemName(), "RPC", properties.getSystemRegistry());
        gatewayCenterService.doRegisterApplication(
                properties.getAddress(),
                properties.getSystemId(),
                properties.getSystemName(),
                "RPC",
                properties.getSystemRegistry()
        );

        // 2. 接口信息
        Class<?>[] interfaces = bean.getClass().getInterfaces();
        if (interfaces.length != 1) {
            throw new GatewayException(bean.getClass().getName() + "实现了多个接口，SDK仅支持单接口: " + JSON.toJSONString(interfaces));
        }
        String interfaceId = interfaces[0].getName();
        log.info("\n应用注册：接口信息 \nsystemId: {} \ninterfaceId: {} \ninterfaceName: {} \ninterfaceVersion: {}", properties.getSystemId(), bean.getClass().getName(), apiProducerClazz.interfaceName(), apiProducerClazz.interfaceVersion());
        gatewayCenterService.doRegisterApplicationInterface(
                properties.getAddress(),
                properties.getSystemId(),
                interfaceId,
                apiProducerClazz.interfaceName(),
                apiProducerClazz.interfaceVersion()
        );

        // 3. 方法信息
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            ApiProducerMethod apiProducerMethod = method.getAnnotation(ApiProducerMethod.class);
            if (null == apiProducerMethod) continue;
            // 解析参数
            Class<?>[] parameterTypes = method.getParameterTypes();
            StringBuilder parameters = new StringBuilder();
            for (Class<?> clazz : parameterTypes) {
                parameters.append(clazz.getName()).append(",");
            }
            String parameterType = parameters.substring(0, parameters.toString().lastIndexOf(","));
            log.info("\n应用注册：方法信息 \nsystemId: {} \ninterfaceId: {} \nmethodId: {} \nmethodName: {} \nparameterType: {} \nuri: {} \nhttpCommandType: {} \nauth: {}",
                    properties.getSystemId(),
                    bean.getClass().getName(),
                    method.getName(),
                    apiProducerMethod.methodName(),
                    parameterType,
                    apiProducerMethod.uri(),
                    apiProducerMethod.httpCommandType(),
                    apiProducerMethod.auth()
            );
            gatewayCenterService.doRegisterApplicationInterfaceMethod(
                    properties.getAddress(),
                    properties.getSystemId(),
                    interfaceId,
                    method.getName(),
                    apiProducerMethod.methodName(),
                    parameterType,
                    apiProducerMethod.uri(),
                    apiProducerMethod.httpCommandType(),
                    apiProducerMethod.auth()
            );
        }

        // 注册完成，发布事件通知
        gatewayCenterService.doPublishRegisterEvent(properties.getAddress(), properties.getSystemId());

        return bean;
    }

}