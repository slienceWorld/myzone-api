package com.commons.mapper;

import com.commons.domain.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【permission】的数据库操作Mapper
 * @createDate 2023-03-27 20:07:50
 * @Entity com.commons.domain.Permission
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> listPermissionsByUsername(String username);
}




