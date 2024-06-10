package cc.geektip.gateway.center.infrastructure.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName gateway_server_detail
 */
@TableName(value ="gateway_server_detail")
@Data
public class GatewayServerDetail implements Serializable {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 分组标识
     */
    private String groupId;

    /**
     * 网关标识
     */
    private String gatewayId;

    /**
     * 网关名称
     */
    private String gatewayName;

    /**
     * 网关地址：127.0.0.1
     */
    private String gatewayAddress;

    /**
     * 服务状态：0不可用、1可使用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}