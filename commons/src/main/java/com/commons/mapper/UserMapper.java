package com.commons.mapper;

import com.commons.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author DELL
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-02-26 17:06:43
* @Entity com.commons.domain.User
*/
public interface UserMapper extends BaseMapper<User> {

    User checkUsernameUnique(String username);
}




