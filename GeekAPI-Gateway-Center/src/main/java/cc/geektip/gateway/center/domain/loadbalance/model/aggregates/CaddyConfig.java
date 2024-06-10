package cc.geektip.gateway.center.domain.loadbalance.model.aggregates;

import cc.geektip.gateway.center.domain.loadbalance.model.vo.ProxyConfigVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description: Caddy配置信息聚合根
 * @author: Fish
 * @date: 2024/6/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaddyConfig {

    List<ProxyConfigVO> proxyConfigs;

}
