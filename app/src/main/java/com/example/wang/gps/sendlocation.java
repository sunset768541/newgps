package com.example.wang.gps;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wang on 2016/6/4.
 * 网络服务模块，提供了get方式和post方式提交数据
 */
public class sendlocation {
    static final String urlPath = "http://sunset16405.xyz/androidask/aa";//使用内网用8080端口，80端口为外网
    static URL url;
    static boolean ppp;
    static HttpURLConnection conn;
    static int recode;
    public static byte[] nn;
    public static String response = "2";
    static Context mycontex;
    public static String  jso;
    public static boolean canoffline=true;
    public static int rsponsecode;
    public static void getContext(Context context) {
        mycontex = context;
    }

    public static boolean sendGetRequest(Map<String, String> params) {
        try {


            StringBuilder sb = new StringBuilder(urlPath);
            sb.append('?');
            // ?method=save&title=435435435&timelength=89&
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey()).append('=')
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8")).append('&');//URLEncoder.encode(内容, 字符编码)
            }
            sb.deleteCharAt(sb.length() - 1);

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(2 * 1000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outStream = conn.getOutputStream();
            byte[] entitydata = sb.toString().getBytes();//得到实体的二进制数据
            Log.e("url", sb.toString());
            outStream.write(entitydata);
            outStream.flush();
            recode = conn.getResponseCode();
            outStream.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line = null;
//            while ((line = reader.readLine()) != null) {
//                Log.e("f服务器",line);
//            }
//            Log.e("服务器响应",br.readLine()+"ll");
            response = reader.readLine();
            //    Log.e("f服务器",reader.readLine());
            Log.e("网络连接", "发送数据完成");
            //          br.close();
            if (conn.getResponseCode() == 200) {
                ppp = true;
            } else {
                ppp = false;
            }
        } catch (Exception e) {
            Looper.prepare();
           // Toast.makeText(mycontex, "连接服务器超时", Toast.LENGTH_SHORT).show();
            Looper.loop();
            Log.e("网络连接异常", Log.getStackTraceString(e));
        }

        return ppp;
    }

    public static void sendPostRequest(Map<String, String> params) {//post方式传递数据
        try {
            rsponsecode=0;
            URL url = new URL(urlPath);
            StringBuilder sb = new StringBuilder();
            //sb.append('?');
            // ?method=save&title=435435435&timelength=89&
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey()).append('=')
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8")).append('&');//URLEncoder.encode(内容, 字符编码)
            }
             sb.deleteCharAt(sb.length() - 1);
            //1.得到HttpURLConnection实例化对象
            conn = (HttpURLConnection) url.openConnection();
            //2.设置请求方式
            conn.setRequestMethod("POST");
            //3.设置post提交内容的类型和长度
		/*
		 * 只有设置contentType为application/x-www-form-urlencoded，
		 * servlet就可以直接使用request.getParameter("username");直接得到所需要信息
		 */
            conn.setRequestProperty("contentType","application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(sb.toString().getBytes().length));
            //默认为false
            conn.setDoOutput(true);
            //4.向服务器写入数据
            conn.getOutputStream().write(sb.toString().getBytes());
            //5.得到服务器相应
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line = null;
//            while ((line = reader.readLine()) != null) {
//                Log.e("f服务器",line);
//            }
//            Log.e("服务器响应",br.readLine()+"ll");
            String ll = reader.readLine();

            Log.e("post1", ll);
            jso=ll;
            JSONUtile.getdatafromjson(ll,params.get("username"));

           // Userinfo.userhead=JSONUtile.base64ToBitmap((String)LL.get(params.get("username")));
          // Log.e("post", aa);
            rsponsecode=conn.getResponseCode();
            if (conn.getResponseCode() == 200) {
                System.out.println("服务器已经收到表单数据！");
            } else {
                System.out.println("请求失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //6.释放资源
            if (conn != null) {
                //关闭连接 即设置 http.keepAlive = false;
                conn.disconnect();
            }
            //canoffline=true;
        }
    }
//    public static String snn(Map<String,String> pa){//测试post产生表单
//        StringBuilder sb = new StringBuilder();
//        try {
//            //sb.append('?');
//            // ?method=save&title=435435435&timelength=89&
//            for (Map.Entry<String, String> entry : pa.entrySet()) {
//                sb.append(entry.getKey()).append('=')
//                        .append(URLEncoder.encode(entry.getValue(), "UTF-8")).append('&');//URLEncoder.encode(内容, 字符编码)
//            }
//            sb.deleteCharAt(sb.length() - 1);
//        }
//        catch (Exception e){
//
//        }
//
//        return sb.toString();
//    }

}
