package com.example.wang.gps;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;

/**
 * Created by sunset on 16/6/12.
 * 好友位置更新线程
 */
public class friendtrace extends Thread {
    public boolean isrun = true;
    String arg;//为用户的名字
    Marker marker;
    HashMap prdata;
    HashMap showdata = new HashMap();//获取用户对应的信息
    Bitmap header;
    String ad;
    ArrayList<LatLng> getbound = new ArrayList<>();
    boolean bound = false;
    Context context;
    public friendtrace(String arg, Marker marker, Bitmap header, Context context) {
        this.arg = arg;
        this.marker = marker;
        this.header = header;
        this.context=context;
    }

    public void run() {
        Iterator iter = JSONUtile.onlinefriendobj.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            if (((String) key).equals(arg)) {
                showdata = (HashMap) entry.getValue();
                break;
            }
        }
        while (isrun) {
            prdata = showdata;//保存上一个数据
            // Iterator iter = JSONUtile.onlinefriendobj.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                if (((String) key).equals(arg)) {
                    showdata = (HashMap) entry.getValue();
                    break;
                }
            }

            try {
                bound = drawtrace.boundflag.get(arg);//获取标志
                getbound = (ArrayList) drawtrace.userbound.get(arg);//获取所画的线的经纬度
            } catch (Exception e) {
                Log.e("没有这个用户的界限", "没有信心");
            }
            if (bound) {
                for (int i = 2; i < getbound.size() - 1; i++) {//确定
                    double x1 = getbound.get(i).latitude;
                    double y1 = getbound.get(i).longitude;
                    double x2 = getbound.get(i + 1).latitude;
                    double y2 = getbound.get(i + 1).longitude;
                    boolean isbound = linesIntersect(Double.parseDouble((String) prdata.get("latitude")), Double.parseDouble((String) prdata.get("longtitude")), Double.parseDouble((String) showdata.get("latitude")), Double.parseDouble((String) showdata.get("longtitude")), x1, y1, x2, y2);
                    // Log.e("对比第"+Integer.valueOf(i).toString(),"  为"+Boolean.valueOf(isbound).toString());
                    if (isbound) {//如果相交
                        // MainActivity.send(,arg,showdata.get("address"),header);//通知
                        send();
                        bound = false;
                        drawtrace.boundflag.remove(arg);//探测完成后就删除这个flag
                    }
                }

            }//设置边界监听
            marker.setPosition(new LatLng(Double.parseDouble((String) showdata.get("latitude")), Double.parseDouble((String) showdata.get("longtitude"))));
            Log.e(arg, "marker更新线程");
            try {
                Thread.sleep(3000);//设置更新位置时间，收requestData中sleep时间影响
            } catch (Exception e) {

            }
        }
    }

    int relativeCCW(double x1, double y1,
                    double x2, double y2,
                    double px, double py) {
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

    boolean linesIntersect(double x1, double y1,
                           double x2, double y2,
                           double x3, double y3,
                           double x4, double y4) {
        return ((relativeCCW(x1, y1, x2, y2, x3, y3) *
                relativeCCW(x1, y1, x2, y2, x4, y4) <= 0)
                && (relativeCCW(x3, y3, x4, y4, x1, y1) *
                relativeCCW(x3, y3, x4, y4, x2, y2) <= 0));
    }

    void send() {
        Notification noti = new Notification.Builder(context)
                .setAutoCancel(true)
                .setTicker("用户越界消息")
                .setSmallIcon(R.drawable.che)
                .setContentTitle(arg)
                .setContentText("越界位置: " + ad)
                .setLargeIcon(header)
                .getNotification();
        noti.ledARGB = 0XFFFF00;
        noti.ledOnMS = 500;
        noti.ledOffMS = 500;
        noti.flags |= Notification.FLAG_SHOW_LIGHTS;
        noti.defaults |= Notification.DEFAULT_VIBRATE;
        long[] vibrate = {0, 100, 200, 300};
        noti.vibrate = vibrate;
        noti.defaults |= Notification.DEFAULT_SOUND;
        noti.sound = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "6");
        MainActivity.mNotificationManager.notify(0, noti);
       MainActivity.mNotificationManager.cancel(1);
    }
}
