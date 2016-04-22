package com.interest.myapplication.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android on 2016/3/5.
 * 主题日报内容查看实体�?
 */
public class Theme implements Serializable{
//	响应实例
//	{
//	    stories: [
//	        {
//	            images: [
//	                "http://pic1.zhimg.com/84dadf360399e0de406c133153fc4ab8_t.jpg"
//	            ],
//	            type: 0,
//	            id: 4239728,
//	            title: "前苏联监狱纹身百科图�?"
//	        },
//	        ...
//	    ],
//	    description: "为你发现�?有趣的新鲜事，建议在 WiFi 下查�?",
//	    background: "http://pic1.zhimg.com/a5128188ed788005ad50840a42079c41.jpg",
//	    color: 8307764,
//	    name: "不许无聊",
//	    image: "http://pic3.zhimg.com/da1fcaf6a02d1223d130d5b106e828b9.jpg",
//	    editors: [
//	        {
//	            url: "http://www.zhihu.com/people/wezeit",
//	            bio: "微在 Wezeit 主编",
//	            id: 70,
//	            avatar: "http://pic4.zhimg.com/068311926_m.jpg",
//	            name: "益康糯米"
//	        },
//	        ...
//	    ],
//	    image_source: ""
//	}

    private List<StoriesEntity> stories;
    private int color;
    private String description;
    private String name;
    private String background;
    private String image;
    private List<EditorsEntity> editors;
    private String image_source;

    public List<StoriesEntity> getStories() {
        return stories;
    }

    public void setStories(List<StoriesEntity> stories) {
        this.stories = stories;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<EditorsEntity> getEditors() {
        return editors;
    }

    public void setEditors(List<EditorsEntity> editors) {
        this.editors = editors;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    @Override
    public String toString() {
        return "Theme{" +
                "stories=" + stories +
                ", color=" + color +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", background='" + background + '\'' +
                ", image='" + image + '\'' +
                ", editors=" + editors +
                ", image_source='" + image_source + '\'' +
                '}';
    }
}
