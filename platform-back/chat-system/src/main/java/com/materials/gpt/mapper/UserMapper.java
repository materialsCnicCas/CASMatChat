package com.materials.gpt.mapper;



import com.materials.gpt.entity.Role;
import com.materials.gpt.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKey(User user);

    User findByUsername(String userName);

    User findByEmail(String email);

    List<Role> findRolesByUserId(Long userId);
    List<Long>findRoleIdsByUserId(Long userId);

    void insertUserRoles(User user);

    List<User> getUserList(Map<String, Object> map);

    int countByUsername(String username);

    void deleteUserRoleByUserId( Map<String, Object> map);
    void insertUserRoleByUserId(Map<String, Object> map);

    User getUserById(Long userId);

	int getAdminRole(Map<String, Object> map);

}
