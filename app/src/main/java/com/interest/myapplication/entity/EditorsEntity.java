package com.interest.myapplication.entity;

import java.io.Serializable;

/**
 * Created by Android on 2016/3/5.
 * 主题日报的编辑实体类
 */
public class EditorsEntity implements Serializable {
	/**	
	 * url: "http://www.zhihu.com/people/wezeit",
	 *  bio: "微在 Wezeit 主编",
	 *  id: 70,
	 *  avatar: "http://pic4.zhimg.com/068311926_m.jpg",
	 *  name: "益康糯米"
     **/
	
    private int id;
    private String bio;
    private String name;
    private String avatar;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "EditorsEntity{" +
                "id=" + id +
                ", bio='" + bio + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
