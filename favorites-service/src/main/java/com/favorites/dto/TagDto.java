package com.favorites.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yyh
 * @date 2023/4/18 19:05
 * @description
 */

@Data
public class TagDto {

    @NotNull(message = "传入的标签信息为空")
    private String tag;
}
