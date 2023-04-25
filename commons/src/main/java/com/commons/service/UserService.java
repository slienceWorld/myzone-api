package com.commons.service;

import com.commons.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.commons.vo.Result;

/**
 * @author DELL
 * @description 针对表【user】的数据库操作Service
 * @createDate 2023-02-26 17:06:43
 */
public interface UserService extends IService<User> {

    User getUserByName(String username);

    String checkUsernameUnique(User user);

    Result registerUser(User user);


}
