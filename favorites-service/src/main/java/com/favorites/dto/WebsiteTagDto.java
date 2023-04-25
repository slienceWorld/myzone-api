package com.favorites.dto;

import lombok.Data;

import java.util.List;

/**
 * @author yyh
 * @date 2023/4/20 17:07
 * @description
 */
@Data
public class WebsiteTagDto {
    private Long wid;
    private List<Long> tid;
}
