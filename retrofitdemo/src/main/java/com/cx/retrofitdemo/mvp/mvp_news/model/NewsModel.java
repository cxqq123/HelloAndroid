package com.cx.retrofitdemo.mvp.mvp_news.model;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */

public class NewsModel {

    /**
     * code : 10000
     * charge : false
     * msg : 查询成功
     * result : {"status":"0","msg":"ok","result":{"channel":"头条","num":"1","list":[{"title":"收评：权重拉动沪指反弹涨0.17% 煤飞色舞行情再现","time":"2017-07-06 15:02","src":"新浪财经","category":"finance","pic":"http://api.jisuapi.com/news/upload/201707/06160007_47862.jpg","content":"7月6日消息，沪指早盘低开后保持震荡，盘中一度反弹翻红，上证50指数[股评]临近午盘跳水，沪指在权重股回调带动下午前亦跳水，锂电池板块急跌，多只个股炸板；午后开盘，两市企稳，锂电池板块反弹，沪指在走出V型探底后翻红。","url":"http://finance.sina.cn/stock/dpps/2017-07-06/detail-ifyhwefp0163259.d.html?vt=4&pos=108","weburl":"http://finance.sina.com.cn/stock/jsy/2017-07-06/doc-ifyhwefp0163259.shtml"}]}}
     */

    private String code;
    private boolean charge;
    private String msg;
    private ResultBeanX result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isCharge() {
        return charge;
    }

    public void setCharge(boolean charge) {
        this.charge = charge;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBeanX getResult() {
        return result;
    }

    public void setResult(ResultBeanX result) {
        this.result = result;
    }

    public static class ResultBeanX {
        /**
         * status : 0
         * msg : ok
         * result : {"channel":"头条","num":"1","list":[{"title":"收评：权重拉动沪指反弹涨0.17% 煤飞色舞行情再现","time":"2017-07-06 15:02","src":"新浪财经","category":"finance","pic":"http://api.jisuapi.com/news/upload/201707/06160007_47862.jpg","content":"7月6日消息，沪指早盘低开后保持震荡，盘中一度反弹翻红，上证50指数[股评]临近午盘跳水，沪指在权重股回调带动下午前亦跳水，锂电池板块急跌，多只个股炸板；午后开盘，两市企稳，锂电池板块反弹，沪指在走出V型探底后翻红。","url":"http://finance.sina.cn/stock/dpps/2017-07-06/detail-ifyhwefp0163259.d.html?vt=4&pos=108","weburl":"http://finance.sina.com.cn/stock/jsy/2017-07-06/doc-ifyhwefp0163259.shtml"}]}
         */

        private String status;
        private String msg;
        private ResultBean result;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * channel : 头条
             * num : 1
             * list : [{"title":"收评：权重拉动沪指反弹涨0.17% 煤飞色舞行情再现","time":"2017-07-06 15:02","src":"新浪财经","category":"finance","pic":"http://api.jisuapi.com/news/upload/201707/06160007_47862.jpg","content":"7月6日消息，沪指早盘低开后保持震荡，盘中一度反弹翻红，上证50指数[股评]临近午盘跳水，沪指在权重股回调带动下午前亦跳水，锂电池板块急跌，多只个股炸板；午后开盘，两市企稳，锂电池板块反弹，沪指在走出V型探底后翻红。","url":"http://finance.sina.cn/stock/dpps/2017-07-06/detail-ifyhwefp0163259.d.html?vt=4&pos=108","weburl":"http://finance.sina.com.cn/stock/jsy/2017-07-06/doc-ifyhwefp0163259.shtml"}]
             */

            private String channel;
            private String num;
            private List<ListBean> list;

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * title : 收评：权重拉动沪指反弹涨0.17% 煤飞色舞行情再现
                 * time : 2017-07-06 15:02
                 * src : 新浪财经
                 * category : finance
                 * pic : http://api.jisuapi.com/news/upload/201707/06160007_47862.jpg
                 * content : 7月6日消息，沪指早盘低开后保持震荡，盘中一度反弹翻红，上证50指数[股评]临近午盘跳水，沪指在权重股回调带动下午前亦跳水，锂电池板块急跌，多只个股炸板；午后开盘，两市企稳，锂电池板块反弹，沪指在走出V型探底后翻红。
                 * url : http://finance.sina.cn/stock/dpps/2017-07-06/detail-ifyhwefp0163259.d.html?vt=4&pos=108
                 * weburl : http://finance.sina.com.cn/stock/jsy/2017-07-06/doc-ifyhwefp0163259.shtml
                 */

                private String title;
                private String time;
                private String src;
                private String category;
                private String pic;
                private String content;
                private String url;
                private String weburl;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public String getSrc() {
                    return src;
                }

                public void setSrc(String src) {
                    this.src = src;
                }

                public String getCategory() {
                    return category;
                }

                public void setCategory(String category) {
                    this.category = category;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getWeburl() {
                    return weburl;
                }

                public void setWeburl(String weburl) {
                    this.weburl = weburl;
                }
            }
        }
    }
}
