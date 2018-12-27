package com.example.fanyuanhua.netpower.base.qq.bean;

/**
 * Created by fanyuanhua on 18/12/27.
 */

public class GRCodeInfo {

    /**
     * qqGRCodeInfo : {"androidKey":"AYbqOCWmTxxwWQg64UxAUtRmC_s7joAz","edition":"1545356676144","iosKey":"4dba699b57fb09f1963966a5872b8a8a94ec0cd9328a0882e22e1d7857021c73","num":1,"qq":"784360818","uploadTime":"2018-12-21 09:44:36","url":"https://mindcloud.camoryapps.com/MindNet/custom-image/1/qqgroup_qr_1.png"}
     * state : 200
     */

    private QqGRCodeInfoBean qqGRCodeInfo;
    private String state;

    public QqGRCodeInfoBean getQqGRCodeInfo() {
        return qqGRCodeInfo;
    }

    public void setQqGRCodeInfo(QqGRCodeInfoBean qqGRCodeInfo) {
        this.qqGRCodeInfo = qqGRCodeInfo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static class QqGRCodeInfoBean {
        /**
         * androidKey : AYbqOCWmTxxwWQg64UxAUtRmC_s7joAz
         * edition : 1545356676144
         * iosKey : 4dba699b57fb09f1963966a5872b8a8a94ec0cd9328a0882e22e1d7857021c73
         * num : 1
         * qq : 784360818
         * uploadTime : 2018-12-21 09:44:36
         * url : https://mindcloud.camoryapps.com/MindNet/custom-image/1/qqgroup_qr_1.png
         */

        private String androidKey;
        private String edition;
        private String iosKey;
        private int num;
        private String qq;
        private String uploadTime;
        private String url;

        public String getAndroidKey() {
            return androidKey;
        }

        public void setAndroidKey(String androidKey) {
            this.androidKey = androidKey;
        }

        public String getEdition() {
            return edition;
        }

        public void setEdition(String edition) {
            this.edition = edition;
        }

        public String getIosKey() {
            return iosKey;
        }

        public void setIosKey(String iosKey) {
            this.iosKey = iosKey;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(String uploadTime) {
            this.uploadTime = uploadTime;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
