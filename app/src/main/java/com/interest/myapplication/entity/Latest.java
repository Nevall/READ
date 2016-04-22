package com.interest.myapplication.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android on 2016/3/5.
 * 鐭ヤ箮鏃ユ姤鑾峰彇鏈�柊娑堟伅鐨勫疄浣撶被锛岃缃�鑾峰彇List<TopStoriesEntity>,List<StoriesEntity>闆嗗�?
 */
public class Latest implements Serializable{
    /**
     * date: "20140523",
     stories: [{title: "涓浗鍙や唬瀹跺叿鍙戝睍鍒颁粖澶╂湁涓や釜楂樺嘲锛屼竴涓袱瀹嬩竴涓槑鏈紙澶氬浘锛�,ga_prefix: "052321",
                images: ["http://p1.zhimg.com/45/b9/45b9f057fc1957ed2c946814342c0f02.jpg"],type: 0,id: 3930445},...],
     top_stories: [{title: "鍟嗗�?鍜屽緢澶氫汉瀹堕噷锛�?鍒跺鍏疯秺鏉ヨ秺澶氾紙澶氬浘锛�,image: "http://p2.zhimg.com/9a/15/9a1570bb9e5fa53ae9fb9269a56ee019.jpg",
                ga_prefix: "052315",type: 0,id: 3930883},...]}
     */

    private List<TopStoriesEntity> top_stories;
    private List<StoriesEntity> stories;
    private String date;

    public List<TopStoriesEntity> getTop_stories() {
        return top_stories;
    }

    public List<StoriesEntity> getStories() {
        return stories;
    }

    public String getDate() {
        return date;
    }

    public void setTop_stories(List<TopStoriesEntity> top_stories) {
        this.top_stories = top_stories;
    }

    public void setStories(List<StoriesEntity> stories) {
        this.stories = stories;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Latest{" +
                "top_stories=" + top_stories +
                ", stories=" + stories +
                ", date='" + date + '\'' +
                '}';
    }
}
