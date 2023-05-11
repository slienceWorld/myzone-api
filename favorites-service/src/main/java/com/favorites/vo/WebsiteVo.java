package com.favorites.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.favorites.config.jackson.StringToLongDeserializer;
import lombok.Data;

import java.util.Date;

/**
 * @author yyh
 * @date 2023/5/10 20:20
 * @description
 */
@Data
public class WebsiteVo {


    private Long id;

    private String url;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


}
