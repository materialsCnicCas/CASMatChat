package com.materials.gpt.mapper;




import com.materials.gpt.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface RoleMapper {

    int deleteByPrimaryKey(Long roleId);

    int insert(Role record);

    Role selectByPrimaryKey(Long roleId);

    int updateByPrimaryKey(Role record);

    List<Role>  getAllRole();

}