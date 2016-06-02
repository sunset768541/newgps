package com.example.wang.gps;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
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
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView x;
    private TextView y;
    private TextView addr;
    private Button ok;
    //private LocationManager locationManager;
    //public Location ll;
    /**
     * dell git test
     */
    private Calendar cc;
    MapView mMapView;
    LatLng lll;
    MapStatusUpdate u;
    //LocationListener locationListener;
    BaiduMapOptions baiduMapOptions;
    Button friend;
    //git tesee

    //dell git test


    Handler mhandle;
    //LocationClient locationClient;
    //BDLocationListener bdLocationListener;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    getGps gg=new getGps();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );    //注册监听函数
        initLocation();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.che);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("定位");
        friend = (Button) findViewById(R.id.button2);
        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(getApplication(), com.example.wang.gps.friend.class);
                startActivity(ii);
            }
        });
        mMapView = (MapView) findViewById(R.id.bmapView);
        GPS.baiduMap = mMapView.getMap();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                .zoomBy(3);
        GPS.baiduMap.animateMapStatus(mapStatusUpdate);

        // map.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        x = (TextView) findViewById(R.id.x);
        y = (TextView) findViewById(R.id.textView);
        addr=(TextView)findViewById(R.id.textView8);
        ok = (Button) findViewById(R.id.button);
        GPS.locationManager=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        GPS.getLocation();
        //locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        testGps();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //定义Maker坐标点
                if (GPS.isFirstLocation){
                mLocationClient.start();}

                LatLng point = new LatLng(GPS.la, GPS.lo);
                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.mal);
                //构建MarkerOption，
                // 用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                GPS.baiduMap.addOverlay(option);
                lll = new LatLng(GPS.la, GPS.lo);
                u = MapStatusUpdateFactory.newLatLng(lll);
                GPS.baiduMap.animateMapStatus(u);

               // y.setText("经度为: " + Integer.valueOf(gg.pts.size()).toString());

            }
        });
        Button set=(Button)findViewById(R.id.button3);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationClient.requestLocation();
                if (GPS.startflag){
                GPS.drawthepath=true;
                    GPS.startflag=false;
                    gg=new getGps();
                gg.start();}
                else
                {
                    GPS.drawthepath=false;
                    gg.pts.clear();
                    GPS.startflag=true;
                }


            }
        });
         mhandle=new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        try {
                            String []ss=(String [])msg.obj;
                            x.setText(ss[0]);
                            y.setText(ss[1]);
                            addr.setText(ss[2]);
                        }
                        catch (Exception e){

                        }



                }
            }
        };

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



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action, menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=2000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
    public class getGps extends Thread{
        public List<LatLng> pts=new ArrayList<LatLng>();
        public double alt=0;
        public double aLo=0;
        public String [] inf=new String[3];

        public  int kk=0;
        public void run(){
            while (GPS.drawthepath){
                if (!((alt==GPS.la)&&(aLo==GPS.lo))){
                    Log.e("比较","alt="+Double.valueOf(alt).toString()+"  "+"loc1getlat="+GPS.location1.getLatitude()+"  "+"alo="+Double.valueOf(aLo).toString()+"  "+"loc1getlat="+GPS.location1.getLongitude());
                    LatLng pp=new LatLng(GPS.la,GPS.lo);
                    pts.add(pp);}
                alt=GPS.la;
                aLo=GPS.lo;
                Log.e("List长度:",Integer.valueOf(pts.size()).toString());
                mhandle.sendEmptyMessage(0);
                inf[0]=Double.valueOf(alt).toString();
                inf[1]=Double.valueOf(aLo).toString();
                inf[2]=GPS.ad;
                 Message msgg=new Message();
                msgg.obj=inf;
                mhandle.sendMessage(msgg);
                try {

                    OverlayOptions polygonOption = new PolylineOptions()
                            .points(pts)
                            .color(0xAA00FF00);
                    GPS.baiduMap.addOverlay(polygonOption);


                }
                catch (Exception e){
                    Log.e("yic",e.toString());
                }
                try {
                    Thread.sleep(1500);

                }
                catch (Exception e){

                }
            }
        }
    }

}
