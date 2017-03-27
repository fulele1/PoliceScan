package com.xaqb.policescan.entity;

/**
 * Created by lenovo on 2017/3/15.
 */

public class Company {
    private String com;//品牌名称
    private String comCode;

    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    private String policeCount;//场所数量
    private String perCount;//从业人员数量
    private String getCount;//收件数量
    private String postCount;//投件数量

    public String getPoliceCount() {
        return policeCount;
    }

    public void setPoliceCount(String policeCount) {
        this.policeCount = policeCount;
    }

    public String getPerCount() {
        return perCount;
    }

    public void setPerCount(String perCount) {
        this.perCount = perCount;
    }

    public String getGetCount() {
        return getCount;
    }

    public void setGetCount(String getCount) {
        this.getCount = getCount;
    }

    public String getPostCount() {
        return postCount;
    }

    public void setPostCount(String postCount) {
        this.postCount = postCount;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }
}
