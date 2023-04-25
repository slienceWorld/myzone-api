package com.favorites.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName website
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName(value = "website")
@Data
public class Website implements Serializable {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 网址
     */
    @TableField(value = "url")
    private String url;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     *
     */
    @TableField(value = "type")
    private String type;

    @TableField(value = "create_time")
    private Date createTime;

    /**
     *
     */
    @TableField(value = "update_time")
    private Date updateTime;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}