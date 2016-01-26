package com.scienjus.smartqq.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 讨论组
 * @author ScienJus
 * @date 2015/12/23.
 */
public class Discuss {

    @JSONField(name = "did")
    public long id;
    public String name;
}
