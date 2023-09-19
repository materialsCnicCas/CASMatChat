package com.materials.gpt.mapper;




import com.materials.gpt.entity.Chat;
import com.materials.gpt.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ChatMapper {

    Integer insert(Chat chat);

    List<Chat> selectList(@Param("userId")Long userId);

    int delete(@Param("id")Integer id);


}