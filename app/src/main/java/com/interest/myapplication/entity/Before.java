package com.interest.myapplication.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android on 2016/3/5.
 * 过往新闻消息实体�?
 */
public class Before implements Serializable{
    /**
     * stories : [{"id":1747159,"title":"娣卞椋熷爞 �? 鎴戠殑寮犳浖濡�,"ga_prefix":"111822","images":["http://p4.zhimg.com/7b/c8/7bc8ef5947b069513c51e4b9521b5c82.jpg"],"type":0},{"id":1858551,"title":"娓呮湞鐨囧笣涓婃湞鐨勬椂鍊欒鐨勬槸婊¤杩樻槸姹夎锛�,"ga_prefix":"111822","images":["http://p3.zhimg.com/21/0c/210c7b63b931932fa7a1e62bf0113e7b.jpg"],"type":0},{"id":1848791,"title":"娣樺疂涓婇偅浜涢�鍚堥�鐖稿鐨勪笢瑗�,"ga_prefix":"111821","images":["http://p4.zhimg.com/7c/d1/7cd1496541c7964b2cf8614b9fa664b0.jpg"],"type":0},{"id":1849914,"title":"鎭嬬埍閲�?殑鐢峰瓙姹夛紝杩庡ご涓撳績鍒锋鎵嶆槸姝ｇ粡浜�,"ga_prefix":"111820","images":["http://p2.zhimg.com/11/05/1105cfa3d12f3539ef35fa603614ed92.jpg"],"type":0},{"id":1854693,"title":"楦¤泲榛勫拰铔嬫竻�?挎垚浜嗙殑璇濆垎鍒槸楦＄殑浠�箞閮ㄤ綅锛�,"ga_prefix":"111819","images":["http://p4.zhimg.com/cf/1f/cf1fd58f22d3c5fd2fd2a5543d70f81d.jpg"],"type":0},{"id":1861205,"title":"椴滄煔娓告垙鍛ㄦ姤\r\n鍥為【涓�懆 iOS 绮惧搧娓告垙","ga_prefix":"111818","images":["http://p2.zhimg.com/e3/d1/e3d15e98b3db498d53d9ed1b85d2fab5.jpg"],"type":0},{"id":1858917,"multipic":true,"title":"鍚冨緢閲嶈 �? 绗竴鍙ｅ氨寮��? high 浜嗭紙澶氬浘锛�,"ga_prefix":"111818","images":["http://p2.zhimg.com/14/3b/143bd74ec7a0299b76d17e6b095799aa.jpg"],"type":0},{"id":1856401,"title":"杩藉コ�?�╂�?缁冧紶鎺堬細濡瑰瓙鐜╂墜鏈虹殑璇濓紝浣犲氨涔熺帺鎵嬫�?锛屾尯濂界殑","ga_prefix":"111818","images":["http://p2.zhimg.com/51/32/51324fa89e1aba7a337e20e98c9664f1.jpg"],"type":0},{"id":1854400,"title":"鏈�編搴旂敤 �? 缁欎綘涓�鏂伴偖绠盡olto","ga_prefix":"111817","images":["http://p3.zhimg.com/f0/97/f0973d30830eed315d46b531f38719cf.jpg"],"type":0},{"id":1848590,"title":"璋佸湪缁存姢姣旂壒甯佺殑鏍稿績绠楁硶锛�,"ga_prefix":"111817","images":["http://p1.zhimg.com/d6/11/d611dd7d57d144621779ec36c8df42fb.jpg"],"type":0},{"id":1847175,"title":"绂诲哺閲戣�?�锛氫竴绉嶅厜鏄庢澶х殑閽荤┖�?�愯涓�?,"ga_prefix":"111816","images":["http://p3.zhimg.com/f8/70/f870fac8fea14e56d2cddf926a4800f2.jpg"],"type":0},{"id":1846706,"title":"閾舵嘲鍏ㄩ潰�?寔�?粯瀹濋挶鍖呬粯娆撅紝涓や釜鍒濆鑰呯殑绗竴娆�?,"ga_prefix":"111815","images":["http://p1.zhimg.com/d4/30/d430ba0d8d9e51482b6a0bd8ff5ef6ee.jpg"],"type":0},{"id":1846781,"title":"浠呭�? 179 缇庡厓锛孧oto G 涓轰粈涔堝畾浠疯繖涔堜綆","ga_prefix":"111814","images":["http://p1.zhimg.com/4b/8c/4b8c8f9c40f08fa9a1a830095131c67c.jpg"],"type":0},{"id":1844934,"title":"瀵兼紨寮犱竴鐧斤細闈掓槬灏忚鍒伴潚鏄ョ數褰憋紝涓棿鏈夊眰闈㈢�?","ga_prefix":"111813","images":["http://p3.zhimg.com/2c/ce/2cce90676f6841e01ab683384f4daaf0.jpg"],"type":0},{"id":1838196,"title":"鐭ュぉ涓�路 鍦嗛《浜嬪疄涓婃槸�?�楁�?寤虹瓚涓父鐢ㄧ殑涓�閫犲�?","ga_prefix":"111812","images":["http://p4.zhimg.com/39/ff/39ff45effc9f6083bb8da5a6f768eaa2.jpg"],"type":0},{"id":1844302,"title":"PrimeSense锛氳嫻鏋滄鍦ㄨ瘯鍥炬敹璐繖涓潻鍛芥�浣撴劅鎺у埗璁惧�?","ga_prefix":"111811","images":["http://p1.zhimg.com/80/26/802617acf921694c7a2e732008e6c2cf.jpg"],"type":0},{"id":1844263,"title":"瀹跺涵鐢�?100M 鍏夌氦浣跨敤浠�箞鏃犵嚎璺敱鍣ㄦ墠鑳藉彂鎸ユ渶澶х綉閫燂�?","ga_prefix":"111810","images":["http://p2.zhimg.com/a6/42/a6423122d959de347cc8a8c61d150c21.jpg"],"type":0},{"id":1843578,"title":"閲戣瀺浜у搧涔熸湁鐗╂祦锛屽苟涓斿ソ澶勫澶�,"ga_prefix":"111809","images":["http://p2.zhimg.com/52/94/52941a00e16bffffe480e19c387d07d9.jpg"],"type":0},{"id":1839454,"title":"鍙洖鍏崄澶氫竾杈嗚溅锛屽ぇ浼楃户缁劍澶寸儌棰濆鐞嗗彉閫熺闂�?�?","ga_prefix":"111807","images":["http://p1.zhimg.com/86/79/86799f8608bf39171b78456675a9f4f0.jpg"],"type":0},{"id":1843290,"title":"鍏ㄦ柟浣嶅啲鏃ユ櫒璺戞敞鎰忎簨椤瑰凡渚涗笂锛屽亣璁句綘宸茶捣搴�,"ga_prefix":"111807","images":["http://p4.zhimg.com/3a/dd/3adda8a964695f3d0c84944fbb676cda.jpg"],"type":0},{"id":1838920,"title":"鐙鏃舵墠鏄簡瑙ｈ嚜宸辩殑鏈�ソ鏈轰細锛屼綘涓婂畬鍘曟墍浼氬啿鍚楋紵","ga_prefix":"111807","images":["http://p2.zhimg.com/a8/a6/a8a677d04d27a96cdb457e6c1a430d68.jpg"],"type":0},{"id":1843557,"title":"鏉庡畻鐩涳細鏃㈢劧闈掓槬鐣欎笉浣忥紝涓嶅鍚�?ぇ鍙旇鏁呬�?","ga_prefix":"111807","images":["http://p4.zhimg.com/c5/7d/c57d1d0ee1ba83df700982a4f8e5ac26.jpg"],"type":0},{"id":1839693,"title":"鍒涗笟鍏�?徃璐㈠姟鎬庝箞鍋氾紝缁濆ぇ澶氭暟鍒涗笟鍒濇湡骞磋交浜轰笉鐭ラ亾杩欎釜","ga_prefix":"111807","images":["http://p3.zhimg.com/b7/b2/b7b223eaa3a6daaf680f266973803c75.jpg"],"type":0},{"id":1841395,"title":"鐬庢�? �? 濡備綍姝ｇ�?�鍦板悙妲�,"ga_prefix":"111806","images":["http://p3.zhimg.com/21/32/21328ba459bee7961dc71de398002638.jpg"],"type":0}]
     * date : 20131118
     */
    private List<StoriesEntity> stories;
    private String date;

    public List<StoriesEntity> getStories() {
        return stories;
    }

    public void setStories(List<StoriesEntity> stories) {
        this.stories = stories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Before{" +
                "stories=" + stories +
                ", date='" + date + '\'' +
                '}';
    }
}
