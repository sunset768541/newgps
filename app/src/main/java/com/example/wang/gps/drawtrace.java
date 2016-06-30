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
import java.util.HashMap;
import java.util.List;

/**
 * Created by sunset on 16/6/21.
 * static boolean	linesIntersect(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4)
 * 测试从 (x1,y1) 到 (x2,y2) 的线段是否与从 (x3,y3) 到 (x4,y4) 的线段相交。
 */
public class drawtrace {
    static LatLng l=new LatLng(0,0);
    static Point pp;
    static Point prepoint;
    static boolean boundwatch;
    static List<LatLng> getbound=new ArrayList<>();
   // static List<Polyline> userpolyline=new ArrayList<>();
    public static HashMap<String,List<LatLng>>userbound=new HashMap<>();//外部可以访问这个获取针对某个用户的边界监听的边界
    public static HashMap<String,Boolean> boundflag=new HashMap<>();//外部可以访问这个获取特定用户是否进行边界检测
    public static HashMap<String,Polyline> userpolyline=new HashMap<>();//存储用户绘制的线
    public static boolean isdraw;
    public static boolean isdrag;
    public static void drawpat(final String username){

        GPS.baiduMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
             List<LatLng> pts = new ArrayList<LatLng>();
              Polyline p;
            @Override
            public void onTouch(MotionEvent motionEvent) {
//
                float x = motionEvent.getX();//获得触摸点的横坐标
                float y = motionEvent.getY();//获得触摸点的纵坐标
                if (isdrag) {
                    Log.e("拖动地图","啥也不做");
                }else {
                    if (isdraw) {
                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_DOWN://绘制线开始
                                try {
                                    userpolyline.get(username).remove();
                                } catch (Exception e) {
                                    Log.e("第一次绘制", "清楚无效");
                                }
                                pts.clear();
                                pp = new Point((int) x, (int) y);
                                lp();//确保setOnMapLoadedCallback回调完成，可以使用projection
                                pts.add(GPS.baiduMap.getProjection().fromScreenLocation(pp));//因为创建Polyline至少需要两个点
                                pts.add(GPS.baiduMap.getProjection().fromScreenLocation(pp));//
                                p = (Polyline) GPS.baiduMap.addOverlay(new PolylineOptions()//创建Polyline
                                        .points(pts)
                                        .color(0xAAFF0000));//设置线的颜色
                                userpolyline.put(username, p);
                            case MotionEvent.ACTION_MOVE://绘制线进行中
                                prepoint = pp;
                                pp = new Point((int) x, (int) y);
                                if (!pp.equals(prepoint)) {//移动时相同的点不会再一次加入到List中
                                    pts.add(GPS.baiduMap.getProjection().fromScreenLocation(pp));
                                }
                                p.setPoints(pts);
                                break;
                            case MotionEvent.ACTION_UP://绘制线结束，存储绘制的line,获得这个line的LatLng List
                                p.setPoints(pts);
                                //pts.remove(0);
                                // pts.remove(1);
                                // getbound=pts;
                                //  isdraw=false;
                                userbound.put(username, pts);
                                boundflag.put(username, true);

                                //boundwatch=true;
                                //Log.e("pts.size: ",Integer.valueOf(pts.size()).toString()+"总长度为： "+Double.valueOf(polygonUtil.computealldistabce(pts)).toString());
                                //Log.e("pts.size: ",Integer.valueOf(pts.size()).toString()+"面积为： "+Double.valueOf(polygonUtil.computearea(pts)).toString());


                        }
                    }

                }

            }

        });
}

public static  LatLng lp(){
    GPS.baiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
        @Override
        public void onMapLoaded() {
            l = GPS.baiduMap.getProjection().fromScreenLocation(pp);
        }
    });
    return l;
}

    public static int relativeCCW(double x1, double y1,
                                  double x2, double y2,
                                  double px, double py)
    {
        x2 -= x1;
        y2 -= y1;
        px -= x1;
        py -= y1;
        double ccw = px * y2 - py * x2;
        if (ccw == 0.0) {
            // The point is colinear, classify based on which side of
            // the segment the point falls on.  We can calculate a
            // relative value using the projection of px,py onto the
            // segment - a negative value indicates the point projects
            // outside of the segment in the direction of the particular
            // endpoint used as the origin for the projection.
            ccw = px * x2 + py * y2;
            if (ccw > 0.0) {
                // Reverse the projection to be relative to the original x2,y2
                // x2 and y2 are simply negated.
                // px and py need to have (x2 - x1) or (y2 - y1) subtracted
                //    from them (based on the original values)
                // Since we really want to get a positive answer when the
                //    point is "beyond (x2,y2)", then we want to calculate
                //    the inverse anyway - thus we leave x2 & y2 negated.
                px -= x2;
                py -= y2;
                ccw = px * x2 + py * y2;
                if (ccw < 0.0) {
                    ccw = 0.0;
                }
            }
        }
        return (ccw < 0.0) ? -1 : ((ccw > 0.0) ? 1 : 0);
    }
    public static boolean linesIntersect(double x1, double y1,
                                         double x2, double y2,
                                         double x3, double y3,
                                         double x4, double y4)
    {
        return ((relativeCCW(x1, y1, x2, y2, x3, y3) *
                relativeCCW(x1, y1, x2, y2, x4, y4) <= 0)
                && (relativeCCW(x3, y3, x4, y4, x1, y1) *
                relativeCCW(x3, y3, x4, y4, x2, y2) <= 0));
    }
}
