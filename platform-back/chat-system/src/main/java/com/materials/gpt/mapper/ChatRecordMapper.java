package com.materials.gpt.mapper;

import com.materials.gpt.entity.Chat;
import com.materials.gpt.entity.ChatRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatRecordMapper {

    int insert(ChatRecord chatRecord);

    List<ChatRecord> selectList(@Param("chatId") Integer chatId);

    int delete(@Param("chatId") Integer chatId);
}
