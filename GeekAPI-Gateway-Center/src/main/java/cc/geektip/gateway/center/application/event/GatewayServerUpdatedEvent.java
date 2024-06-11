package cc.geektip.gateway.center.application.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @description: 网关算力节点更新事件
 * @author: Fish
 * @date: 2024/6/9
 */
@Getter
public class GatewayServerUpdatedEvent extends ApplicationEvent {

    public GatewayServerUpdatedEvent(Object source) {
        super(source);
    }

}
