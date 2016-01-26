package com.scienjus.smartqq.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 分组
 * @author ScienJus
 * @date 15/12/19.
 */
public class Category {

    public int index;
    public int sort;
    public String name;
    public List<Friend> friends = new ArrayList<>();

    public void addFriend(Friend friend) {
        this.friends.add(friend);
    }

    @Override
    public String toString() {
        return "Category{" +
                "index=" + index +
                ", sort=" + sort +
                ", name='" + name + '\'' +
                ", friends=" + friends +
                '}';
    }

    public static Category defaultCategory() {
        Category category = new Category();
        category.index = 0;
        category.sort = 0;
        category.name = "我的好友";
        return category;
    }
}
