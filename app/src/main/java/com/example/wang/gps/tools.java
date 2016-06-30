package com.example.wang.gps;

import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.HeatMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Polygon;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sunset on 16/6/24.
 */
public class tools extends Activity {
    public BaiduMap baidmap;
    public MapView mapview;
    public MapStatusUpdate mapstatus;
   // public MyLocationListener mlis;
    UiSettings uiSettings;
    boolean draw=false;
    Button compute;
    Button clear;
    CheckBox measureLengthch;
    CheckBox measureAreach;
    RadioGroup choseOperate;
    RadioButton choseDraw;
    RadioButton choseDrag;
    TextView length;
    TextView area;
    HashMap<String,List> axislist=new HashMap<>();
    Polyline pl;
    Polygon pg;
    //Object ppl;
    Point pp=new Point(0,0);
    Point uptpoin=new Point(0,0);
    List<LatLng> data=new ArrayList<LatLng>();
    List<LatLng> prdata=new ArrayList<LatLng>();
    List<Polyline> computline=new ArrayList<>();
    List<Polygon> computPloy=new ArrayList<>();
    Point prepoint;
    boolean measureLength;
    boolean measureArea;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); //声明使用自定义标题
        setContentView(R.layout.tools);
        SysApplication.getInstance().addActivity(this);//退出功能注册
        mapview = (MapView) findViewById(R.id.bmapViewtool);
        baidmap = mapview.getMap();
        LatLng ll = new LatLng(GPS.la, GPS.lo);//设置地图的中心
        mapstatus = MapStatusUpdateFactory.newLatLng(ll);
        baidmap.animateMapStatus(mapstatus);
        uiSettings=baidmap.getUiSettings();//控制地图的手势
        compute=(Button) findViewById(R.id.button18);
        clear=(Button) findViewById(R.id.button19);
        choseOperate=(RadioGroup)findViewById(R.id.operate);
        choseDrag=(RadioButton)findViewById(R.id.radioButton2);
        choseDraw=(RadioButton)findViewById(R.id.radioButton);
        measureLengthch=(CheckBox)findViewById(R.id.checkBox);
        measureAreach=(CheckBox)findViewById(R.id.checkBox2);
        length=(TextView)findViewById(R.id.textView22);
        area=(TextView)findViewById(R.id.textView23);
        baidmap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                baidmap.getProjection().fromScreenLocation(pp);
            }
        });
        baidmap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                float x = motionEvent.getX();//获得触摸点的横坐标
                float y = motionEvent.getY();//获得触摸点的纵坐标
                if (draw) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN://绘制线开始
                            uiSettings.setAllGesturesEnabled(false);
                           // data.clear();
                            pp = new Point((int) x, (int) y);
                          //  lp();//确保setOnMapLoadedCallback回调完成，可以使用projection
                            if (uptpoin.equals(0,0)){
                                data.add(baidmap.getProjection().fromScreenLocation(pp));//
                            }
                            else {
                                data.add(baidmap.getProjection().fromScreenLocation(uptpoin));//因为创建Polyline至少需要两个点
                            }
                            data.add(baidmap.getProjection().fromScreenLocation(pp));//
                                 pl = (Polyline) baidmap.addOverlay(new PolylineOptions()//创建Polyline
                                        .points(data)
                                        .color(0xAA0000FF));//设置线的颜色
                                computline.add(pl);
                        case MotionEvent.ACTION_MOVE://绘制线进行中
                            prepoint = pp;
                            pp = new Point((int) x, (int) y);
                            if (!pp.equals(prepoint)) {//移动时相同的点不会再一次加入到List中
                                data.add(baidmap.getProjection().fromScreenLocation(pp));
                            }
                                pl.setPoints(data);


                        case MotionEvent.ACTION_UP://绘制线结束，存储绘制的line,获得这个line的LatLng List
                            uptpoin=new Point((int) x,(int) y);
                                pl.setPoints(data);

                           // Log.e("pts.size: ", Integer.valueOf(pts.size()).toString() + "总长度为： " + Double.valueOf(polygonUtil.computealldistabce(pts)).toString());
                            //Log.e("pts.size: ", Integer.valueOf(pts.size()).toString() + "面积为： " + Double.valueOf(polygonUtil.computearea(pts)).toString());

                    }
                }

            }
        }
        );
        compute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (measureArea){
                    if (computPloy.size()<1){
                        try {
                            pg=(Polygon)baidmap.addOverlay(new PolygonOptions().points(data)
                                    .stroke(new Stroke(1,0xAAFF00FF)).fillColor(0xAA0000FF));
                            computPloy.add(pg);
                        }
                 catch (Exception e){
                     Toast.makeText(getApplicationContext(),"绘制的不是一个面",Toast.LENGTH_SHORT).show();
                 }

                    }
                    Log.e("显plogsize",Integer.valueOf(computPloy.size()).toString());
                    Log.e("测面","ok");
                    Double l=polygonUtil.computearea(data);//目前图像含有交叉点无法测
                    area.setText(Double.valueOf(l).toString()+"平方米");

                }
                if (measureLength){
                    Log.e("测测","ok");
                    length.setText(Double.valueOf(polygonUtil.computealldistabce(data)).toString()+"米");
                }
                choseDrag.setChecked(true);
                choseDraw.setChecked(false);
                choseDraw.setClickable(false);
            }
        });
clear.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        for (int i=0;i<computline.size();i++){
            try {
                computline.get(i).remove();
            }
            catch (Exception e){
                Log.e("清空绘图",Log.getStackTraceString(e));
            }
        }
            for (int i=0;i<computPloy.size();i++){
            try {
                computPloy.get(i).remove();
            }
            catch (Exception e){
                Log.e("清空绘图",Log.getStackTraceString(e));
            }


        }
        computline.clear();
        computPloy.clear();
        data.clear();
        choseDraw.setClickable(true);
        uptpoin=new Point(0,0);
    }
});
        choseOperate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {//选择拖动地图还是绘制
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==choseDrag.getId()){
                    draw=false;
                    uiSettings.setAllGesturesEnabled(true);
                }
                else if (checkedId==choseDraw.getId()){
                    draw=true;
                    uiSettings.setAllGesturesEnabled(false);

                }
            }
        });

        measureAreach.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    measureArea=true;

                }
                else {
                measureArea=false;
                }
            }
        });
        measureLengthch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    measureLength=true;

                }
                else {
                    measureLength=false;
                }
            }
        });
    }


}