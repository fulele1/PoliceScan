package com.xaqb.policescan.Utils;

/**
 * Created by lenovo on 2017/3/3.
 */

public class HttpUrlUtils {
    private static HttpUrlUtils httpUrl = new HttpUrlUtils();

    public static HttpUrlUtils getHttpUrl() {
        return httpUrl;
    }

    private String getBaseUrl() {
        return "http://xawz.xaqianbai.net:8090/open.ashx";
    }

    //查询订单
    //http://xawz.xaqianbai.net:8090/open.ashx?action=policeexpressinfo&code=719677781148
    public String quer_yCode(){
        return getBaseUrl()+"?action=policeexpressinfo";
    }

    // 登录
    //http://xawz.xaqianbai.net:8090/open.ashx?action=policelogin&user=xaqianbai&pwd=D41D8CD98F00B204E9800998ECF8427E
    public String user_login() {
        return getBaseUrl()+"?action=policelogin";
    }

    //找回密码
    //http://xawz.xaqianbai.net:8090/open.ashx?action=policemodipwd&old=D41D8CD98F00B204E9800998ECF8427E&new=D41D8CD98F00B204E9800998ECF8427E

    public String back_password() {
        return getBaseUrl()+"?action=policemodipwd";
    }

    //获取验证码
    public String get_v_code() {
        return getBaseUrl() + "smscode.json?";
    }

}
