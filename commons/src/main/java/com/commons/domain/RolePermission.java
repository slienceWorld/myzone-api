package com.commons.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName role_permission
 */
@TableName(value ="role_permission")
@Data
public class RolePermission implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色Id
     */
    @TableField(value = "rid")
    private Long rid;

    /**
     * 权限Id
     */
    @TableField(value = "pid")
    private Long pid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}