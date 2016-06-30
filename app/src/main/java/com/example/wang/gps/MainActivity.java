package com.example.wang.gps;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
//import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 主模块，地图显示Activity
 */

public class MainActivity extends Activity {
    private TextView x;
    private TextView y;
    private TextView addr;
    private Button ok;
    private Switch onoffline;
    //private LocationManager locationManager;
    //public Location ll;
    /**
     * dell git test
     */
    private Calendar cc;
    RadioButton draw;
    RadioButton drag;
    RadioGroup mainradiogroup;
    //LocationListener locationListener;
    BaiduMapOptions baiduMapOptions;
    Button friend;
    //git tesee
    // ActionBar actionBar;
    //dell git test
    Switch lineonoff;
    Switch footprint;
    HashMap<String, String> hh = new HashMap<String, String>();
    Handler mhandle;
    //LocationClient locationClient;
    //BDLocationListener bdLocationListener;

    OnLine onLine = new OnLine();
    ShowFootprint shof;

    static NotificationManager mNotificationManager;

    // UiSettings uiSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); //声明使用自定义标题
        setContentView(R.layout.main);
        SysApplication.getInstance().addActivity(this);//退出功能注册
        FriendInf.getCo(getApplicationContext());
        Userinfo.gc(getApplication());
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);//自定义布局赋
        GPS.mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        GPS.mLocationClient.registerLocationListener(GPS.myListener);    //注册监听函数
        initLocation();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //      actionBar = getSupportActionBar();
        //    actionBar.setLogo(R.drawable.che);
        //  actionBar.setDisplayUseLogoEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);
        onoffline = (Switch) findViewById(R.id.switch2);
        onoffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onoffline.isChecked()) {
                    Userinfo.isonline = true;
                    //通知服务器用户上线
                    //tellserviceonling.put("userstate","1");
                    new requestData().start();
                } else {
                    Userinfo.isonline = false;
                    //通知服务器用户离线
                    // tellserviceonling.put("userstate","0");

                }

                Log.e("发送用户状态变更请求", "ok");
            }
        });
        friend = (Button) findViewById(R.id.button2);
        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(getApplication(), com.example.wang.gps.Frie.class);
                startActivity(ii);
            }
        });
        GPS.mMapView = (MapView) findViewById(R.id.bmapView);
        GPS.baiduMap = GPS.mMapView.getMap();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                .zoomBy(3);
        GPS.baiduMap.animateMapStatus(mapStatusUpdate);
        // GPS.baiduMap.setMapType();
        // map.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        x = (TextView) findViewById(R.id.x);
        y = (TextView) findViewById(R.id.textView);
        draw = (RadioButton) findViewById(R.id.radioButton3);
        drag = (RadioButton) findViewById(R.id.radioButton4);
        mainradiogroup = (RadioGroup) findViewById(R.id.mainradiogroup);
        GPS.uiSettings = GPS.baiduMap.getUiSettings();//控制地图的手势
        mainradiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == drag.getId()) {
                    drawtrace.isdrag = true;
                    GPS.uiSettings.setAllGesturesEnabled(true);
                    // drawtrace.isdraw=false;
                }
                if (checkedId == draw.getId()) {
                    drawtrace.isdrag = false;
                    GPS.uiSettings.setAllGesturesEnabled(false);
                    // drawtrace.isdraw=true;
                }
            }
        });
        addr = (TextView) findViewById(R.id.textView8);
        ok = (Button) findViewById(R.id.button);
        GPS.locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        GPS.getLocation();
        //locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        testGps();
        GPS.mLocationClient.start();//开始定位
        GPS.baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {//点击marker的点击事件

            @Override
            public boolean onMarkerClick(Marker arg0) {
                // TODO Auto-generated method stub
                String value = arg0.getTitle();//获得点击marker的title，title也就是用户的名称
                for (int i = 0; i < FriendInf.markerfri.size(); i++) {//遍markerfri确定在容器中找到点击的marker
                    if (value.equals(FriendInf.markerfri.get(i))) {//判断朋友朋友变量的marker列表中是否含有所点击的marker的名字
                        markerDig mm = new markerDig(MainActivity.this, "好友信息", (HashMap) FriendInf.firinf.get(value), value);//如果找到匹配的marker，就将这个marker的数据传入diglog
                        mm.show();
                    }
                }
                if (value.equals(Userinfo.username)) {
                    HashMap<String, Object> usin = new HashMap<String, Object>();
                    usin.put("userhead", Userinfo.userhead);
                    usin.put("username", "用户名: " + Userinfo.username);
                    usin.put("add", "地址:" + GPS.ad);
                    usin.put("stat", "状态: 移动中");//获得用户的移动状态
                    userinfmarkerdiglog markerDig = new userinfmarkerdiglog(MainActivity.this, "我的信息", usin, value);
                    markerDig.show();
//                Toast.makeText(getApplicationContext(), "Marker被点击了！", Toast.LENGTH_SHORT).show();
                }
//                else
//                    Toast.makeText(getApplicationContext(), "未知mark！", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(BitmapUtil.getMarkerBitmap(Userinfo.userhead));
                Userinfo.usermarke.setIcon(bitmap);
                GPS.lll = new LatLng(GPS.la, GPS.lo);//设置地图的中心
                GPS.u = MapStatusUpdateFactory.newLatLng(GPS.lll);
                GPS.baiduMap.animateMapStatus(GPS.u);
                GPS.mLocationClient.requestLocation();
                //   GPS.startupdatalocation=true;
                //  send(Userinfo.username,GPS.ad);
                new Thread(new updatamarkerlocation()).start();
                // new OnLine().start();
                // y.setText("经度为: " + Integer.valueOf(gg.pts.size()).toString());

            }
        });
        Button set = (Button) findViewById(R.id.button3);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoset = new Intent(getApplication(), com.example.wang.gps.systemseting.class);
                startActivity(gotoset);
            }
        });
        final Button tools = (Button) findViewById(R.id.button17);
        tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gototools = new Intent(getApplication(), com.example.wang.gps.tools.class);
                startActivity(gototools);
            }
        });
        mhandle = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        try {
                            String[] ss = (String[]) msg.obj;
                            x.setText(ss[0]);
                            y.setText(ss[1]);
                            addr.setText(ss[2]);
                        } catch (Exception e) {

                        }


                }
            }
        };
//        lineonoff = (Switch) findViewById(R.id.switch2);
//        lineonoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    GPS.onoffline = true;
//                    // GPS.startflag=false;
//                    onLine = new OnLine();
//                    onLine.start();
//                } else {
//                    GPS.onoffline = false;
//
//                    //  GPS.startflag=true;
//                }
//
//            }
//        });
        footprint = (Switch) findViewById(R.id.switch3);
        footprint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    GPS.showfootprint = true;
                    shof = new ShowFootprint();
                    shof.start();
                } else {
                    GPS.showfootprint = false;
                }
            }
        });

        iniloca();
        Intent intent = new Intent(MainActivity.this, internetreuqest.class);
        startService(intent);
        JSONUtile.getjson(sendlocation.jso, Userinfo.username);//在创建这个activity的时候对login获得的json数据进行解析


        //shof=new ShowFootprint();
    }

    public void setactionbartitle(String title) {
        //actionBar.setTitle(title);
    }

    private void testGps() {

        if (GPS.locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            GPS.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, GPS.locationListener);
            return;
        } else {
            Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 0); // 此为设置完成后返回到获取界面
            GPS.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, GPS.locationListener);
            return;
        }
    }

    public void iniloca() {//开启MainActivity的时候定位用户的位置，并显示一个标志
        LatLng point = new LatLng(GPS.la, GPS.lo);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(BitmapUtil.getMarkerBitmap(Userinfo.userhead));
        //在地图上添加Marker，并显示
        Userinfo.usermarke = (Marker) GPS.baiduMap.addOverlay(new MarkerOptions()
                .position(point)
                .icon(bitmap).title(Userinfo.username));
        GPS.lll = new LatLng(GPS.la, GPS.lo);//设置地图的中心
        GPS.u = MapStatusUpdateFactory.newLatLng(GPS.lll);
        GPS.baiduMap.animateMapStatus(GPS.u);
        GPS.mLocationClient.requestLocation();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 2000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        GPS.mLocationClient.setLocOption(option);
    }

    public class OnLine extends Thread {

        public double alt = 0;
        public double aLo = 0;
        public String[] inf = new String[3];

        public int kk = 0;

        public void run() {
            while (GPS.onoffline) {
                alt = GPS.la;
                aLo = GPS.lo;
                mhandle.sendEmptyMessage(0);
                inf[0] = Double.valueOf(alt).toString();
                inf[1] = Double.valueOf(aLo).toString();
                inf[2] = GPS.ad;
                Message msgg = new Message();
                msgg.obj = inf;
                mhandle.sendMessage(msgg);
                GPS.dd = new Date();
                hh.put("commond", "insertdata");
                hh.put("username", Userinfo.username);
                hh.put("time", Long.valueOf(GPS.dd.getTime()).toString());
                hh.put("latitude", Double.valueOf(GPS.la).toString());
                hh.put("longtitude", Double.valueOf(GPS.lo).toString());
                hh.put("addr", GPS.ad);
                try {
                    //  boolean kk = sendlocation.sendGetRequest(hh);
                    //Log.e("服务器介绍", Boolean.valueOf(kk).toString());
                } catch (Exception e) {
                    Log.e("kk", "按你要");
                }

                try {
                    Thread.sleep(2000);

                } catch (Exception e) {
                    Log.e("sleep", e.toString());
                }
            }
        }
    }

    public class ShowFootprint extends Thread {

        public double alt = 0;
        public double aLo = 0;
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(BitmapUtil.getMarkerBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.def)));
        //.fromResource(R.drawable.mal);
        //构建MarkerOption，
        // 用于在地图上添加Marker
//               Bundle info= new Bundle();
//                info.putString("markeid", );
        //在地图上添加Marker，并显示
        LatLng ll = new LatLng(GPS.la, GPS.lo);

        public List<LatLng> pts = new ArrayList<LatLng>();

        Marker footmarker = (Marker) GPS.baiduMap.addOverlay(new MarkerOptions()
                .position(ll)
                .icon(bitmap).title(Userinfo.username));//移动中的marker

        public void run() {
            pts.add(new LatLng(GPS.la, GPS.lo));
            pts.add(new LatLng(GPS.la, GPS.lo));
            Polyline p = (Polyline) GPS.baiduMap.addOverlay(new PolylineOptions()
                    .points(pts)
                    .color(0xAA00FF00));
            Userinfo.usermarke.setPosition(ll);
            while (GPS.showfootprint) {
                GPS.startupdatalocation = false;//定位设置的marker停止移动，footmarkeer移动
                if (!((alt == GPS.la) && (aLo == GPS.lo))) {
                    // Log.e("比较", "alt=" + Double.valueOf(alt).toString() + "  " + "loc1getlat=" + GPS.location1.getLatitude() + "  " + "alo=" + Double.valueOf(aLo).toString() + "  " + "loc1getlat=" + GPS.location1.getLongitude());
                    LatLng pp = new LatLng(GPS.la, GPS.lo);
                    pts.add(pp);
                    footmarker.setPosition(pp);//实时更新marker的位置
                    p.setPoints(pts);
                }
                alt = GPS.la;
                aLo = GPS.lo;

                Log.e("List长度:", Integer.valueOf(pts.size()).toString());
                try {
                    Thread.sleep(3000);

                } catch (Exception e) {
                    Log.e("sleep", e.toString());
                }
            }
            footmarker.remove();//线程关闭，这个footmarker删除
            p.remove();//线程关闭这个PolyLine删除
            GPS.startupdatalocation = true;//定位设置的marker可以移动
            new Thread(new updatamarkerlocation()).start();//启动线程更新usermarker的位置
        }
    }

    /**
     * 更新 用户marker位置的线程
     */
    public class updatamarkerlocation implements Runnable {
        LatLng markerlocation;
        LatLng premarkerlocation;
        boolean bound = false;
        ArrayList<LatLng> getbound;

        public void run() {
            markerlocation = new LatLng(GPS.la, GPS.lo);
            while (GPS.startupdatalocation) {
                premarkerlocation = markerlocation;//保存marker移动前的坐标
                markerlocation = new LatLng(GPS.la, GPS.lo);
                Userinfo.usermarke.setPosition(markerlocation);//更新usermarker的位置
                //遍历boundflag确定是否可以对边界检测
                //遍历userbound获得要检测的线,List
                //userpolyline为检测的线的引用，通过这个可以删除这个线
                try {
                    bound = drawtrace.boundflag.get(Userinfo.username);
                    getbound = (ArrayList) drawtrace.userbound.get(Userinfo.username);
                } catch (Exception e) {
                    Log.e("没有这个用户的界限", "没有信心");
                }
                if (bound) {
                    for (int i = 2; i < getbound.size() - 1; i++) {
                        double x1 = getbound.get(i).latitude;
                        double y1 = getbound.get(i).longitude;
                        double x2 = getbound.get(i + 1).latitude;
                        double y2 = getbound.get(i + 1).longitude;
                        boolean isbound = linesIntersect(premarkerlocation.latitude, premarkerlocation.longitude, markerlocation.latitude, markerlocation.longitude, x1, y1, x2, y2);
                        // Log.e("对比第"+Integer.valueOf(i).toString(),"  为"+Boolean.valueOf(isbound).toString());
                        if (isbound) {//如果相交
                            send(Userinfo.username, GPS.ad, Userinfo.userhead);
                            drawtrace.boundflag.remove(Userinfo.username);//探测完成后就删除这个flag
                            //删除这个flag
                            bound = false;
                        }
                    }

                }//设置边界监听
                try {
                    Thread.sleep(3000);
                    //Log.e("更新usrmarker的位置","ok");
                } catch (Exception e) {

                }

            }
        }
    }

    public void send(String username, String addr, Bitmap header) {
        Notification noti = new Notification.Builder(this)
                .setAutoCancel(true)
                .setTicker("用户越界消息")
                .setSmallIcon(R.drawable.che)
                .setContentTitle(username)
                .setContentText("越界位置: " + addr)
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
        mNotificationManager.notify(0, noti);
        mNotificationManager.cancel(1);
        //   Log.e("通知", "ok");

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

}
