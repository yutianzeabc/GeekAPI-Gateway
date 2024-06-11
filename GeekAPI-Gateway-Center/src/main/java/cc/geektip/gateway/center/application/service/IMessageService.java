package cc.geektip.gateway.center.application.service;

import java.util.Map;

/**
 * @description: 接口消息服务
 * @author: Fish
 * @date: 2024/6/4
 */
public interface IMessageService {

    Map<String, String> queryRedisConfig();

    void pushMessage(String gatewayId, Object message);

}
