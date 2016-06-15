package com.example.wang.gps;

import android.util.Log;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sunset on 16/6/14.
 */
public class requestData extends Thread {
    public void run(){
        //发送一条上线信息
        while (Userinfo.isonline){
        try {
            HashMap <String,String>sendinfo=new HashMap<String,String>();
            sendinfo.put("commond","datarequest");
            sendinfo.put("username",Userinfo.username);
            sendinfo.put("state",Integer.valueOf(1).toString());//i根据上线离线switchqued
            GPS.dd=new Date();
            sendinfo.put("time",Long.valueOf(GPS.dd.getTime()).toString());//
            sendinfo.put("latitude",Double.valueOf(GPS.la).toString());
            sendinfo.put("longtitude",Double.valueOf(GPS.lo).toString());
            sendinfo.put("address", GPS.ad);
            sendlocation.sendPostRequest(sendinfo);
            JSONUtile.decodejson(sendlocation.jso);//解析resonant数据并且动态更新用户的数据信息
            Log.e("动态更新数据","ok");
            Thread.sleep(3000);
        }
        catch (Exception e){
            Log.e("动态更新数据",Log.getStackTraceString(e));
        }
        }

        //发送一条下线信息
    }
}
