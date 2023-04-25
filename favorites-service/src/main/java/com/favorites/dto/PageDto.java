package com.favorites.dto;

import lombok.Data;

import javax.validation.constraints.Min;

/**
 * @author yyh
 * @date 2023/4/18 16:03
 * @description
 */

@Data
public class PageDto {

    @Min(message = "页数不能小于1", value = 1)
    private int pageNum;

    @Min(message = "页码大小不能小于1", value = 1)
    private int pageSize;

    public int getPageNum() {
        return pageNum - 1;
    }
}
