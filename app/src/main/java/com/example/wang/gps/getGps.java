package com.example.wang.gps;

import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunset on 16/6/1.
 */
public class getGps extends Thread{
    public List<LatLng> pts=new ArrayList<LatLng>();
    OverlayOptions polyline=new PolylineOptions();

    public void run(){
        while (GPS.drawthepath){
            LatLng pp=new LatLng(GPS.location1.getLatitude(),GPS.location1.getLongitude());
            pts.add(pp);
        }
    }
}
