package com.scienjus.smartqq.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 好友状态
 * @author ScienJus
 * @date 2015/12/24.
 */
public class FriendStatus {

	public long uin;
    public String status;

    @JSONField(name = "client_type")
    public int clientType;

}
