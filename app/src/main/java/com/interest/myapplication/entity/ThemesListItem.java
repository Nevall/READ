package com.interest.myapplication.entity;

import java.io.Serializable;

/**
 * Created by Android on 2016/3/5.
 * 涓婚鏃ユ姤鍒楄〃鏍囬瀹炰綋绫�?
 */
public class ThemesListItem implements Serializable{
    private String id;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ThemesListItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
