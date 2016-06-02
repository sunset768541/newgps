package com.example.wang.gps;

import android.util.Log;
import android.widget.Toast;

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
    public double alt=0;
    public double aLo=0;
    public  int kk=0;
    public void run(){
        while (GPS.drawthepath){
            if (!((alt==GPS.location1.getLatitude())&&(aLo==GPS.location1.getLongitude()))){
                Log.e("比较","alt="+Double.valueOf(alt).toString()+"  "+"loc1getlat="+GPS.location1.getLatitude()+"  "+"alo="+Double.valueOf(aLo).toString()+"  "+"loc1getlat="+GPS.location1.getLongitude());
            LatLng pp=new LatLng(GPS.location1.getLatitude(),GPS.location1.getLongitude());
            pts.add(pp);}
            alt=GPS.location1.getLatitude();
            aLo=GPS.location1.getLongitude();
            Log.e("List长度:",Integer.valueOf(pts.size()).toString());

//          //  GPS.baiduMap.clear();
//            OverlayOptions polygonOption = new PolylineOptions()
//                    .points(pts)
//                    .color(0xAA00FF00);
//            GPS.baiduMap.addOverlay(polygonOption);
            try {
               // GPS.baiduMap.clear();
                OverlayOptions polygonOption = new PolylineOptions()
                        .points(pts)
                        .color(0xAA00FF00);
                GPS.baiduMap.addOverlay(polygonOption);

            }
            catch (Exception e){
            }
            try {
                Thread.sleep(1500);

            }
            catch (Exception e){

            }
        }
    }
}
