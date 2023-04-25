package com.favorites.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yyh
 * @date 2023/4/22 15:02
 * @description
 */
@Data
public class WebsiteExcelDto {

    @ExcelProperty("网址")
    private String url;
    @ExcelProperty("标题")
    private String title;
}
