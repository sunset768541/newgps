package com.example.wang.gps;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sunset on 16/6/1.
 */
public class GPS {
    public static Location location1;
    public static LocationManager locationManager;
    public static LocationListener locationListener;
    //public static List<LatLng> pts;
    public static boolean drawthepath =false;
    public static BaiduMap baiduMap;
    public static boolean startflag=true;
    public static double lo;
    public static double la;
    public static boolean isFirstLocation=true;
    public static String ad;
    public static boolean showme=false;
    public static String username="王宝辉";
    public static String time;
    public static Date dd;
   // public static String
   // public static OverlayOptions overlayOptions;
    public static void getLocation(){

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider
                location1 = location;
//                try {
//                    if (drawthepath){
//                        LatLng pp=new LatLng(location.getLatitude(),location.getLongitude());
//                  //      pts.add(pp);
//                        overlayOptions=new PolylineOptions().points(pts).color(0xaaff0000);
//                        baiduMap.addOverlay(overlayOptions);
//                }
//
//                }
//                catch(Exception e){
//                    Log.e("JJ","JJ");
//                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
                Log.d("pp", "proe");
            }

            public void onProviderDisabled(String provider) {
                Log.d("prod", "prodigy");
            }
        };
    }
}
