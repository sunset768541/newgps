package com.example.wang.gps;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.Polyline;

/**
 * Created by sunset on 16/6/11.
 * 本机用户信息管理模块
 */
public class Userinfo {
    public static String username="def";
    public static Marker usermarke;
    public static Marker mymarker;
    public static Polyline myPoloyline;
    public static boolean isonline=false;
    public static boolean isLogin=false;
    static Resources res;
    public static Bitmap userhead;
    //public static Bitmap userhead1=
    public static void gc(Context m){
        res=m.getResources();
        userhead = BitmapFactory.decodeResource(res, R.drawable.def);
    }

}
