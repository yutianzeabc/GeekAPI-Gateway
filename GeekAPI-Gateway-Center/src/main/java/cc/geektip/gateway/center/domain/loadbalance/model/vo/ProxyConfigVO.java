package cc.geektip.gateway.center.domain.loadbalance.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Fish
 * @date: 2024/6/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProxyConfigVO {

    private String name;
    private List<String> servers;

}
