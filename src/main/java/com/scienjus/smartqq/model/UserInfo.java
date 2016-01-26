package com.scienjus.smartqq.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 用户
 * @author ScienJus
 * @date 2015/12/24.
 */
public class UserInfo {

	public Birthday birthday;
	public String phone;
	public String occupation;
	public String college;
	public String uin;
	public int blood;
	public String lnick;   //签名
	public String homepage;

    @JSONField(name = "vip_info")
    public int vipInfo;
    public String city;
    public String country;
    public String province;
    public String personal;
    public int shengxiao;
    public String nick;
    public String email;
    public String account;
    public String gender;
    public String mobile;

}
