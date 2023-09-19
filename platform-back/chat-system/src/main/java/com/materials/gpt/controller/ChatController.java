package com.materials.gpt.controller;

import com.materials.gpt.controller.req.AddChatRecordReq;
import com.materials.gpt.entity.User;
import com.materials.gpt.service.ChatService;
import com.materials.gpt.utils.ResponseInfo;
import com.materials.gpt.utils.ResponseInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final Logger logger = LoggerFactory.getLogger(ChatController.class);

    /*@Autowired
    private IAuthService authService;*/

    @Autowired
    private ChatService chatService;

    /**
     * 新增会话记录
     *
     * @param request
     * @return
     * @throws AuthenticationException
     */
    @PostMapping(value = "/addChatRecord")
    public ResponseInfo addChatRecord(HttpServletRequest request, @RequestBody AddChatRecordReq addChatRecordReq) {
        try {
            /*User userInfo = authService.getUserInfo(request);
            if(userInfo==null){
                return ResponseInfoUtil.createResponseInfo(HttpCodeEnum.TOKEN_FAILED,"认证失败");
            }*/
            User userInfo=new User();
            userInfo.setUserId(12345L);
            return chatService.addChatRecord(userInfo,addChatRecordReq);
        }catch (Exception e){
            logger.error("新增会话失败{}", e.getMessage(), e);
            return ResponseInfoUtil.createResult(e);
        }
    }


    /**
     * 查询会话列表
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/chatList")
    public ResponseInfo chatList(HttpServletRequest request) {
        try {
            /*User userInfo = authService.getUserInfo(request);
            if(userInfo==null){
                return ResponseInfoUtil.createResponseInfo(HttpCodeEnum.TOKEN_FAILED,"认证失败");
            }*/
            User userInfo=new User();
            userInfo.setUserId(12345L);
            return chatService.getChatList(userInfo);
        }catch (Exception e){
            logger.error("查询会话列表失败{}", e.getMessage(), e);
            return ResponseInfoUtil.createResult(e);
        }
    }

    /**
     * 查询会话记录列表
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/chatRecordList")
    public ResponseInfo chatRecordList(HttpServletRequest request,@RequestParam("chatId") Integer chatId) {
        try {
            //User userInfo = authService.getUserInfo(request);
            //if(userInfo==null){
               // return ResponseInfoUtil.createResponseInfo(HttpCodeEnum.TOKEN_FAILED,"认证失败");
            //}
            User userInfo=new User();
            userInfo.setUserId(12345L);
            return chatService.getChatRecordList(chatId);
        }catch (Exception e){
            logger.error("查询会话记录列表s失败{}", e.getMessage(), e);
            return ResponseInfoUtil.createResult(e);
        }
    }


    /**
     * 删除会话记录
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/deleteChat")
    public ResponseInfo deleteChat(HttpServletRequest request,@RequestParam("chatId") Integer chatId) {
        try {
            //User userInfo = authService.getUserInfo(request);
            //if(userInfo==null){
                //return ResponseInfoUtil.createResponseInfo(HttpCodeEnum.TOKEN_FAILED,"认证失败");
            //}
            User userInfo=new User();
            userInfo.setUserId(12345L);
            return chatService.deleteChat(chatId);
        }catch (Exception e){
            logger.error("删除会话记录失败{}", e.getMessage(), e);
            return ResponseInfoUtil.createResult(e);
        }
    }
}
