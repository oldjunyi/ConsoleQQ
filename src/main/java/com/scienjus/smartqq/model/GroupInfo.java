package com.scienjus.smartqq.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 群资料
 * @author ScienJus
 * @date 2015/12/24.
 */
public class GroupInfo {

	public long gid;
	public long createtime;
	public String memo;
	public String name;
	public long owner;
	public String markname;
	public List<GroupUser> users = new ArrayList<>();

    public void addUser(GroupUser user) {
        this.users.add(user);
    }

}
