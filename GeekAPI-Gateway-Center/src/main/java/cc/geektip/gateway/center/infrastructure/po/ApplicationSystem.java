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
 * @TableName application_system
 */
@TableName(value ="application_system")
@Data
public class ApplicationSystem implements Serializable {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 系统标识
     */
    private String systemId;

    /**
     * 系统名称
     */
    private String systemName;

    /**
     * 系统类型：RPC、HTTP
     */
    private String systemType;

    /**
     * 注册中心
     */
    private String systemRegistry;

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