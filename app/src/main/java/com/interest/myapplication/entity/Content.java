package com.interest.myapplication.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 新闻具体内容实体类
 * @author Android
 */
public class Content implements Serializable{
	
//	{
//	    body: "<div class="main-wrap content-wrap">...</div>",
//	    image_source: "Yestone.com 版权图片库",
//	    title: "深夜惊奇 · 朋友圈错觉",
//	    image: "http://pic3.zhimg.com/2d41a1d1ebf37fb699795e78db76b5c2.jpg",
//	    share_url: "http://daily.zhihu.com/story/4772126",
//	    js: [ ],
//	    recommenders": [
//	        { "avatar": "http://pic2.zhimg.com/fcb7039c1_m.jpg" },
//	        { "avatar": "http://pic1.zhimg.com/29191527c_m.jpg" },
//			...
//	    ],
//	    ga_prefix: "050615",
//	    section": {
//	        "thumbnail": "http://pic4.zhimg.com/6a1ddebda9e8899811c4c169b92c35b3.jpg",
//	        "id": 1,
//	        "name": "深夜惊奇"
//	    },
//	    type: 0,
//	    id: 4772126,
//	    css: [
//	        "http://news.at.zhihu.com/css/news_qa.auto.css?v=1edab"
//	    ]
//	}
	
	private int id;
    private List<RecommendersEntity> recommenders;
    private String body;
    private String title;
    private String ga_prefix;
    private String share_url;
    private String image;
    private int type;
    private List<String> css;
    private String image_source;
	public Content() {
		super();
	}
	public Content(int id, List<RecommendersEntity> recommenders, String body,
			String title, String ga_prefix, String share_url, String image,
			int type, List<String> css, String image_source) {
		super();
		this.id = id;
		this.recommenders = recommenders;
		this.body = body;
		this.title = title;
		this.ga_prefix = ga_prefix;
		this.share_url = share_url;
		this.image = image;
		this.type = type;
		this.css = css;
		this.image_source = image_source;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<RecommendersEntity> getRecommenders() {
		return recommenders;
	}
	public void setRecommenders(List<RecommendersEntity> recommenders) {
		this.recommenders = recommenders;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGa_prefix() {
		return ga_prefix;
	}
	public void setGa_prefix(String ga_prefix) {
		this.ga_prefix = ga_prefix;
	}
	public String getShare_url() {
		return share_url;
	}
	public void setShare_url(String share_url) {
		this.share_url = share_url;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<String> getCss() {
		return css;
	}
	public void setCss(List<String> css) {
		this.css = css;
	}
	public String getImage_source() {
		return image_source;
	}
	public void setImage_source(String image_source) {
		this.image_source = image_source;
	}
	
	@Override
	public String toString() {
		return "Content [id=" + id + ", recommenders=" + recommenders
				+ ", body=" + body + ", title=" + title + ", ga_prefix="
				+ ga_prefix + ", share_url=" + share_url + ", image=" + image
				+ ", type=" + type + ", css=" + css + ", image_source="
				+ image_source + "]";
	}
}
