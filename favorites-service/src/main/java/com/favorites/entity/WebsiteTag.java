package com.favorites.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName website_tag
 */
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="website_tag")
@Data
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

    public WebsiteTag(Long wid, Long tid) {
        this.wid = wid;
        this.tid = tid;
    }
}