package com.interest.myapplication.entity;

import java.io.Serializable;

/**
 * Created by Android on 2016/3/5.
 */
/**
 * 灏佽鐭ヤ箮鏃ユ姤鏈�柊娑堟伅鐨則op_stories鏁版嵁�?�炰綋绫�?
 */
public class TopStoriesEntity implements Serializable{
    /*  top_stories:
        title: "鍟嗗�?鍜屽緢澶氫汉瀹堕噷锛�?鍒跺鍏疯秺鏉ヨ秺澶氾紙澶氬浘锛�,
        image: "http://p2.zhimg.com/9a/15/9a1570bb9e5fa53ae9fb9269a56ee019.jpg",
        ga_prefix: "052315",
        type: 0,
        id: 3930883
    */
    private int id;
    private String title;//鏂伴椈鏍囬
    //        private String ga_prefix;
    private String image;//鍥剧墖鍦板潃
    private int type;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


    public String getImage() {
        return image;
    }

    public int getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TopStoriesEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", type=" + type +
                '}';
    }
}
