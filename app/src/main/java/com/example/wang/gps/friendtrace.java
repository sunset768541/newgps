package com.example.wang.gps;

import android.util.Log;

import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;

import java.util.NavigableMap;

/**
 * Created by sunset on 16/6/12.
 */
public class friendtrace extends Thread {
    public boolean isrun=true;
    String arg;
    Marker marker;
    public  friendtrace(String arg,Marker marker){
        this.arg=arg;
        this.marker=marker;
    }
    public void run(){
        while (isrun){
            marker.setPosition(new LatLng(GPS.la + 0.01, GPS.lo + 0.01));
            Log.e(arg,"更新marker更新线程");
            try {
                Thread.sleep(3000);
            }
            catch (Exception e){

            }
        }
    }
}
