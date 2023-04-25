package com.commons.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.commons.domain.Role;
import com.commons.mapper.RoleMapper;
import com.commons.mapper.RolePermissionMapper;
import com.commons.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author DELL
 * @description 针对表【role】的数据库操作Service实现
 * @createDate 2023-03-27 20:09:25
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {

    @Resource
    private RolePermissionMapper rolePermissionMapper;
    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Role> listRolesByPermissionId(Long pid) {
        List<Long> roleIds = rolePermissionMapper.listAllByPermissionId(pid);
        return listByIds(roleIds);
    }


}




