package com.nageoffer.shortlink.admin.dto.req.user;

import lombok.Data;

@Data
public class UserMessageReqDTO {
    /**
     * 用户ID
     */
    Long UserId;

    /**
     * 用户输入信息
     */
    String InputMessage;

}
