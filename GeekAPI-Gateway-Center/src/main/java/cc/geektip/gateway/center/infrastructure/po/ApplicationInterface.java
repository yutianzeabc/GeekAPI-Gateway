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
 * @TableName application_interface
 */
@TableName(value ="application_interface")
@Data
public class ApplicationInterface implements Serializable {
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
     * 接口标识
     */
    private String interfaceId;

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 接口版本
     */
    private String interfaceVersion;

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