package com.favorites.service;

import javax.validation.groups.Default;

/**
 * @author yyh
 * @date 2023/4/18 19:07
 * @description 表单验证分组接口
 */

public interface ValidateGroup {
    /**
     * 新增分组
     */
    interface Add extends Default{
    }

    /**
     * 删除分组
     */
    interface Delete extends Default{
    }

    /**
     * 查询分组
     */
    interface Select extends Default {
    }

    /**
     * 更新分组
     */
    interface Update extends Default {
    }
}
