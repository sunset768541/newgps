package com.example.wang.gps;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by wang on 2016/6/4.
 */
public class sendlocation {
   static final String urlPath="http://192.168.191.1:8080/androidask/aa";
    static URL url;
   static boolean ppp;
   static HttpURLConnection conn;
    static int recode;
    public static byte [] nn;
    public static  String response="2";
    public static boolean sendGetRequest(Map<String, String> params) {
        try {


        StringBuilder sb = new StringBuilder(urlPath);
        sb.append('?');
        // ?method=save&title=435435435&timelength=89&
        for(Map.Entry<String, String> entry : params.entrySet()){
            sb.append(entry.getKey()).append('=')
                    .append(URLEncoder.encode(entry.getValue(), "UTF-8")).append('&');
        }
        sb.deleteCharAt(sb.length()-1);

        URL url = new URL(sb.toString());
         conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
        OutputStream outStream = conn.getOutputStream();
        byte[] entitydata = sb.toString().getBytes();//得到实体的二进制数据
            Log.e("url", sb.toString());
        outStream.write(entitydata);
        outStream.flush();
            recode=conn.getResponseCode();
        outStream.close();
            BufferedReader  reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line = null;
//            while ((line = reader.readLine()) != null) {
//                Log.e("f服务器",line);
//            }
//            Log.e("服务器响应",br.readLine()+"ll");
            response=reader.readLine();
        //    Log.e("f服务器",reader.readLine());
            Log.e("网络连接", "发送数据完成");
  //          br.close();
        if(conn.getResponseCode()==200){
            ppp=true;
        }
            else {
        ppp= false;
        }
        }
        catch (Exception e){
            Log.e("网络连接异常",Log.getStackTraceString(e));
        }

        return ppp;
    }

}
