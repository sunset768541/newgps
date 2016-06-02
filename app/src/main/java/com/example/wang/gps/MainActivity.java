package com.example.wang.gps;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
    private Button ok;
    //private LocationManager locationManager;
    //public Location ll;
    private Calendar cc;
    MapView mMapView;
    LatLng lll;
    MapStatusUpdate u;
    //LocationListener locationListener;
    BaiduMapOptions baiduMapOptions;
    Button friend;
    //git tesee

    getGps gg=new getGps();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
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
        ok = (Button) findViewById(R.id.button);
        GPS.locationManager=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        GPS.getLocation();
        //locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        testGps();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //定义Maker坐标点
                LatLng point = new LatLng(GPS.location1.getLatitude(), GPS.location1.getLongitude());
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
                lll = new LatLng(GPS.location1.getLatitude(), GPS.location1.getLongitude());
                u = MapStatusUpdateFactory.newLatLng(lll);
                GPS.baiduMap.animateMapStatus(u);
                x.setText("纬度为: " + Double.valueOf(GPS.location1.getLatitude()).toString());
                //y.setText("经度为: " + Double.valueOf(GPS.location1.getLongitude()).toString());
                y.setText("list数目: " + Integer.valueOf(gg.pts.size()).toString());

            }
        });
        Button set=(Button)findViewById(R.id.button3);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

//                LatLng pt1 = new LatLng(23.05657189, 113.37007428);
//                LatLng pt2 = new LatLng(23.06657190, 113.38007429);
//                LatLng pt3 = new LatLng(23.07657191, 113.390074210);
//                LatLng pt4 = new LatLng(23.08657192, 113.36007430);
//                LatLng pt5 = new LatLng(23.09657193, 113.35007431);
//                List<LatLng> pts = new ArrayList<LatLng>();
//                List<LatLng> pts1=new ArrayList<LatLng>();
//                pts.add(pt1);
//                pts.add(pt2);
//                pts1.add(pt1);
//                pts1.add(pt4);
//                pts1.add(pt5);
//                pts1.add(pt1);
////构建用户绘制多边形的Option对象
//                OverlayOptions polygonOption = new PolylineOptions()
//                        .points(pts)
//                        .color(0xAA00FF00);
//                OverlayOptions polygonOption1 = new PolylineOptions()
//                        .points(pts1)
//                        .color(0xAAFF0000);
////在地图上添加多边形Option，用于显示
//                GPS.baiduMap.addOverlay(polygonOption1);
//                GPS.baiduMap.addOverlay(polygonOption);//都可以显示


            }
        });

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
}
