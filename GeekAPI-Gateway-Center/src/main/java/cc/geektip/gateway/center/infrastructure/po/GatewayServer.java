package cc.geektip.gateway.center.infrastructure.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName gateway_server
 */
@TableName(value ="gateway_server")
@Data
public class GatewayServer implements Serializable {
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
     * 分组名称
     */
    private String groupName;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}