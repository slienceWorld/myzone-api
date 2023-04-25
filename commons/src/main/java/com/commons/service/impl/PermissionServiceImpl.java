package com.commons.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.commons.domain.Permission;
import com.commons.mapper.PermissionMapper;
import com.commons.service.PermissionService;
import com.commons.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author DELL
 * @description 针对表【permission】的数据库操作Service实现
 * @createDate 2023-03-27 20:07:50
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper,Permission>
        implements PermissionService {



}




