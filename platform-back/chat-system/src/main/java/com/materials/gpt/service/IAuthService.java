package com.materials.gpt.service;


import javax.servlet.http.HttpServletRequest;

import com.materials.gpt.entity.Role;
import com.materials.gpt.entity.User;
import com.materials.gpt.entity.UserQueryConditions;

import java.util.List;

public interface IAuthService {

    User register(User userToAdd);

    User getUserInfo(HttpServletRequest request) ;
    User selectByPrimaryKey(Long userId);
    String loginToken(String userId);

    List<User> getUserList(UserQueryConditions conditions);

    int countByUsername(String username);

    List<Role>  getAllRole();

    void updateUserRole(Long userId,List<Long> roleIds);

    User getUserById(Long userId);

    int getAdminRole(String userId);

    void updateByPrimaryKey(User user);
}
