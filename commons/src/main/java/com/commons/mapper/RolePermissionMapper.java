package com.commons.mapper;

import com.commons.domain.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author DELL
* @description 针对表【role_permission】的数据库操作Mapper
* @createDate 2023-03-27 20:57:48
* @Entity com.commons.domain.RolePermission
*/
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    List<Long> listAllByPermissionId(Long pid);
}




