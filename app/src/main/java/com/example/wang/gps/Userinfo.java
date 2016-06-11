package com.example.wang.gps;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by sunset on 16/6/11.
 */
public class Userinfo {
    public static String username="def";

     static Resources res;
    public static Bitmap userhead;
    public static void gc(Context m){
        res=m.getResources();
        userhead = BitmapFactory.decodeResource(res, R.drawable.def);
    }

}
