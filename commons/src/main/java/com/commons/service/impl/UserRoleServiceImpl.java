package com.commons.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.commons.domain.UserRole;
import com.commons.mapper.UserRoleMapper;
import com.commons.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * @author DELL
 * @description 针对表【user_role】的数据库操作Service实现
 * @createDate 2023-03-27 22:54:43
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
        implements UserRoleService {

}




