package cc.geektip.gateway.center.domain.loadbalance.model.vo;

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
public class MatchVO {

    private List<String> path;

    public MatchVO(String path) {
        this.path = List.of(path);
    }

}
