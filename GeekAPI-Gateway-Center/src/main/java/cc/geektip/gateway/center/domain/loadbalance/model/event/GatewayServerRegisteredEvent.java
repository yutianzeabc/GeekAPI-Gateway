package cc.geektip.gateway.center.domain.loadbalance.model.event;

import cc.geektip.gateway.center.domain.manage.model.vo.GatewayServerDetailVO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @description: 网关算力节点注册事件
 * @author: Fish
 * @date: 2024/6/9
 */
@Getter
public class GatewayServerRegisteredEvent extends ApplicationEvent {

    private final List<GatewayServerDetailVO> gatewayServerDetailVOList;

    public GatewayServerRegisteredEvent(Object source, List<GatewayServerDetailVO> serverDetails) {
        super(source);
        this.gatewayServerDetailVOList = serverDetails;
    }

}
