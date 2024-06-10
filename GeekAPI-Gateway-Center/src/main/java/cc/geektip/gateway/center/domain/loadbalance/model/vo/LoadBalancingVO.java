package cc.geektip.gateway.center.domain.loadbalance.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Fish
 * @date: 2024/6/9
 */
@Data
@NoArgsConstructor
public class LoadBalancingVO {

    @JsonProperty("selection_policy")
    private SelectionPolicyVO selectionPolicy;

    public LoadBalancingVO(String policy) {
        this.selectionPolicy = new SelectionPolicyVO(policy);
    }

}
