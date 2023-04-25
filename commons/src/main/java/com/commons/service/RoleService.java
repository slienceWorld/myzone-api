package com.commons.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.commons.domain.Role;

import java.util.List;

/**
* @author DELL
* @description 针对表【role】的数据库操作Service
* @createDate 2023-03-27 20:09:25
*/
public interface RoleService extends IService<Role> {
    List<Role> listRolesByPermissionId(Long pid);

}
