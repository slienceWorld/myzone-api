package com.favorites.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName website_tag
 */
@TableName(value ="website_tag")
@Data
@Accessors(chain = true)
public class WebsiteTag implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 网站id
     */
    @TableField(value = "wid")
    private Long wid;

    /**
     * 标签id
     */
    @TableField(value = "tid")
    private Long tid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}