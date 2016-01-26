package com.scienjus.smartqq.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 缇�
 * @author ScienJus
 * @date 2015/12/18.
 */
public class Group {

    @JSONField(name = "gid")
    public long id;
    public String name;
    public long flag;
    public long code;

}
