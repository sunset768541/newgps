package com.example.wang.gps;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by wang on 2016/6/4.
 */
public class sendlocation {
   static final String urlPath="http://sunset.6655.la/abc/a.jsp";
    static URL url;
   static boolean ppp;
    public static boolean sendGetRequest(Map<String, String> params, String enc) {
        try {


        StringBuilder sb = new StringBuilder(urlPath);
        sb.append('?');
        // ?method=save&title=435435435&timelength=89&
        for(Map.Entry<String, String> entry : params.entrySet()){
            sb.append(entry.getKey()).append('=')
                    .append(URLEncoder.encode(entry.getValue(), enc)).append('&');
        }
        sb.deleteCharAt(sb.length()-1);

        URL url = new URL(sb.toString());
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
        OutputStream outStream = conn.getOutputStream();
        byte[] entitydata = sb.toString().getBytes();//得到实体的二进制数据
        outStream.write(entitydata);
        outStream.flush();
        outStream.close();
        if(conn.getResponseCode()==5000){
            ppp=true;
        }
            else {
        ppp= false;}
        }
        catch (Exception e){

        }
        return ppp;
    }


}
