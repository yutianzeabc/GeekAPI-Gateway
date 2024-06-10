package cc.geektip.gateway.center.domain.loadbalance.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HandleVO {

    private String handler;
    private List<RouteVO> routes;
    @JsonProperty("strip_path_prefix")
    private String stripPathPrefix;
    @JsonProperty("load_balancing")
    private LoadBalancingVO loadBalancing;
    private List<UpstreamVO> upstreams;

}
