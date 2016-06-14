package com.example.wang.gps;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.mapapi.model.LatLng;

import java.util.HashMap;

/**
 * Created by sunset on 16/6/13.
 * 定时访问网路以保证定位持续功能模块
 */
public class internetreuqest extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    sendlocation.sendGetRequest(new HashMap<String, String>());
                    try {
                        Thread.sleep(1000);
                        Log.e("service", "请求网络");

                    }
                    catch (Exception e){

                    }
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
