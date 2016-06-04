package com.example.wang.gps;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by wang on 2016/6/4.
 */
public class JSONUtile {
   public static JSONObject jsonObject;
    public static JSONObject getjson(){
        try {
             jsonObject=new JSONObject();
            jsonObject.put("username",GPS.username);
            jsonObject.put("time",GPS.time);
            jsonObject.put("locatidis",GPS.ad);
            jsonObject.put("lati",GPS.la);
            jsonObject.put("lonti",GPS.lo);
        }
        catch (Exception e){
            Log.e("getjson",e.toString());
        }


        return jsonObject;
    }
}
