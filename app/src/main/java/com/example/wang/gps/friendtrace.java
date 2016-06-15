package com.example.wang.gps;

import android.util.Log;

import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;

/**
 * Created by sunset on 16/6/12.
 * 好友位置更新线程
 */
public class friendtrace extends Thread {
    public boolean isrun=true;
    String arg;//为用户的名字
    Marker marker;
    public  friendtrace(String arg,Marker marker){
        this.arg=arg;
        this.marker=marker;
    }
    public void run(){
        while (isrun){
            //通过arg获取该marker所对应的位置信息，去JSONUtile.onlinefriendobj获得该用户的最新信息

            Iterator iter = JSONUtile.onlinefriendobj.entrySet().iterator();
            HashMap showdata=new HashMap();//获取用户对应的信息
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                if (((String)key).equals(arg)){
                    showdata =(HashMap) entry.getValue();
                    break;
                }
            }
            marker.setPosition(new LatLng( Double.parseDouble((String)showdata.get("latitude")),  Double.parseDouble((String)showdata.get("longtitude"))));
            Log.e(arg,"更新marker更新线程");
            try {
                Thread.sleep(3000);//设置更新位置时间，收requestData中sleep时间影响
            }
            catch (Exception e){

            }
        }
    }
}
