package com.example.wang.gps;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Projection;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunset on 16/6/21.
 */
public class drawtrace {
    static LatLng l=new LatLng(0,0);
    static Point pp;
    public static void drawpat(){
        final UiSettings uiSettings=GPS.baiduMap.getUiSettings();//控制地图的手势
        GPS.baiduMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
             List<LatLng> pts = new ArrayList<LatLng>();
              Polyline p;
            @Override
            public void onTouch(MotionEvent motionEvent) {
                float x=motionEvent.getX();//获得触摸点的横坐标
                float y=motionEvent.getY();//获得触摸点的纵坐标
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN://绘制线开始
                        uiSettings.setAllGesturesEnabled(false);
                        pts.clear();
                         pp=new Point((int)x,(int)y);
                        lp();//确保setOnMapLoadedCallback回调完成，可以使用projection
                        pts.add(GPS.baiduMap.getProjection().fromScreenLocation(pp));//因为创建Polyline至少需要两个点
                        pts.add(GPS.baiduMap.getProjection().fromScreenLocation(pp));//
                         p =(Polyline)GPS.baiduMap.addOverlay(new PolylineOptions()//创建Polyline
                                .points(pts)
                                .color(0xAAFF0000));//设置线的颜色
                    case MotionEvent.ACTION_MOVE://绘制线进行中
                        pp=new Point((int)x,(int)y);
                        pts.add(GPS.baiduMap.getProjection().fromScreenLocation(pp));
                        p.setPoints(pts);
                    case MotionEvent.ACTION_UP://绘制线结束，存储绘制的line,获得这个line的LatLng List
                        p.setPoints(pts);
                        uiSettings.setAllGesturesEnabled(false);



                }
            }
        });
}

public static  LatLng lp(){
    GPS.baiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
        @Override
        public void onMapLoaded() {
            l=GPS.baiduMap.getProjection().fromScreenLocation(pp);
        }
    });
    return l;
}
}
