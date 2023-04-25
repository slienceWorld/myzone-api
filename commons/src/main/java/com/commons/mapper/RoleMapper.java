package com.commons.mapper;

import com.commons.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author DELL
* @description 针对表【role】的数据库操作Mapper
* @createDate 2023-03-27 20:09:25
* @Entity com.commons.domain.Role
*/
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> listRolesByUsername(String name);
}




