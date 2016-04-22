package com.interest.myapplication.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android on 2016/3/5.
 * 鏂伴椈鍏蜂綋鍐呭�?�炰綋绫�?
 */
public class News implements Serializable{
    /**
     * id : 7053854
     * recommenders : [{"avatar":"http://pic3.zhimg.com/bbb689a7a_m.jpg"}]
     * body : <div class="main-wrap content-wrap">...</div>
     * title : 閲庣敓鍔ㄧ墿浠庝笉鍒风墮锛屾湁鐐规媴蹇冧粬浠殑鐗欓娇鍑洪棶棰�
     * ga_prefix : 081710
     * share_url : http://daily.zhihu.com/story/7053854
     * js : []
     * image : http://pic1.zhimg.com/3d8395f01761c77e87b673d0806191a4.jpg
     * type : 0
     * css : ["http://news.at.zhihu.com/css/news_qa.auto.css?v=016bb"]
     * image_source : 绔欓叿娴锋礇鍒涙�?
     */

    private int id;
    private List<RecommendersEntity> recommenders;//杩欑瘒鏂囩珷鐨勬帹鑽愯�
    private String body;//HTML 鏍煎紡鐨勬柊闂�
    private String title;//鏂伴椈鏍囬
    private String ga_prefix;
    private String share_url;//渚涘湪绾挎煡鐪嬪唴�?��?�笌鍒嗕韩鑷�SNS 鐢ㄧ�? URL
    private String image;//鑾峰緱鐨勫浘鐗囧�? 鏈�柊娑堟伅 鑾峰緱鐨勫浘鐗囧垎杈ㄧ巼涓嶅悓銆傝繖閲岃幏寰楃殑鏄湪鏂囩珷娴忚鐣岄潰涓娇鐢ㄧ殑澶у浘銆�
    private int type;
    private List<String> css;//渚涙墜鏈虹鐨�WebView(UIWebView) 浣跨�?
    private String image_source;// 鍥剧墖鐨勫唴瀹规彁渚涙柟

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
        return "News{" +
                "id=" + id +
                ", recommenders=" + recommenders +
                ", body='" + body + '\'' +
                ", title='" + title + '\'' +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", share_url='" + share_url + '\'' +
                ", image='" + image + '\'' +
                ", type=" + type +
                ", css=" + css +
                ", image_source='" + image_source + '\'' +
                '}';
    }
}
