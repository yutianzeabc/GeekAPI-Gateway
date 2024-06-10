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
public class RouteObjectVO {

    @JsonProperty("@id")
    private String id;
    private String group;
    private List<HandleVO> handle;
    private List<MatchVO> match;

}

