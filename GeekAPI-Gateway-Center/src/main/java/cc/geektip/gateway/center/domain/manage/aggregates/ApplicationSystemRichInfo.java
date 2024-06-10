package cc.geektip.gateway.center.domain.manage.aggregates;

import cc.geektip.gateway.center.domain.manage.model.vo.ApplicationSystemVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description: 应用系统信息聚合根
 * @author: Fish
 * @date: 2024/6/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationSystemRichInfo {

    /**
     * 网关ID
     */
    private String gatewayId;
    /**
     * 系统列表
     */
    private List<ApplicationSystemVO> applicationSystemVOList;

}
