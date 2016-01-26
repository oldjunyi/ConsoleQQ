package com.scienjus.smartqq.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 讨论组消息
 * @author ScienJus
 * @date 15/12/19.
 */
public class DiscussMessage {

	public long discussId;

	public long time;

	public String content;

	public long userId;

	public Font font;

    public DiscussMessage(JSONObject json) {
        JSONArray content = json.getJSONArray("content");
        this.font = content.getJSONArray(0).getObject(1, Font.class);
        this.content = content.getString(1);
        this.time = json.getLongValue("time");
        this.discussId = json.getLongValue("did");
        this.userId = json.getLongValue("send_uin");
    }

}
