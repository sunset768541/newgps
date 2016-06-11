package com.example.wang.gps;

//import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * Created by sunset on 16/5/29.
 */
public class allfriends extends Fragment{
    private View view;
    ListView listView;
    SimpleAdapter simpleAdapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.allfriends, container, false);
       //  listView=(ListView)getActivity().findViewById(R.id.listView);
        //registerForContextMenu(listView);
        Log.e("k", "2");

//        listView.setAdapter(simpleAdapter);
        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        android.util.Log.d("mark", "onCreate()--------->news Fragment");
        List<Map<String,Object>> listItems=new ArrayList<Map<String,Object>>();
        for (int i=0;i<FriendInf.allfirname.length;i++){
            Map<String,Object> listItem=new HashMap<String, Object>();
            listItem.put("header",FriendInf.head[i]);
            listItem.put("friname",FriendInf.allfirname[i]);
            listItem.put("add",FriendInf.addr[i]);
            listItem.put("sta",FriendInf.sta[i]);
            listItem.put("statime",FriendInf.statime[i]);
            listItems.add(listItem);

        }
         simpleAdapter=new SimpleAdapter(this.getContext(),listItems,R.layout.fiendlist,new String[]{"header","friname","add","sta","statime"},new int[]{
                R.id.header,R.id.name,R.id.add,R.id.sta,R.id.statime
        });
        Log.e("k","3");


    }
    public void onStart() {
        super.onStart();
       Log.e("k","1");
        listView = (ListView) getActivity().findViewById(R.id.listView);
        registerForContextMenu(listView);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String usename=FriendInf.allfirname[position];
                    BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(BitmapUtil.getMarkerBitmap(BitmapFactory.decodeResource(getResources(), FriendInf.head[position])));
                    String add=FriendInf.addr[position];
                    String sta=FriendInf.sta[position];
                    HashMap<String,Object> usin=new HashMap<String, Object>();
                    usin.put("userhead",BitmapFactory.decodeResource(getResources(), FriendInf.head[position]));
                    usin.put("username","用户名: "+usename);
                    usin.put("add",add);
                    usin.put("stat",sta);
                    Log.e("usema",usename);
                    FriendInf.addtomarkfri(usename,usin);
                    LatLng point1 = new LatLng(GPS.la+position/10, GPS.lo+position);
                    GPS.option = new MarkerOptions()
                        .position(point1)
                        .icon(bitmap).title(usename);
                //在地图上添加Marker，并显示
                    GPS.baiduMap.addOverlay(GPS.option);
                Intent tomai=new Intent(getContext(), com.example.wang.gps.MainActivity.class);
                startActivity(tomai);

                //GPS.lll = new LatLng(GPS.la, GPS.lo);
                //GPS.u = MapStatusUpdateFactory.newLatLng(point);
                //GPS.baiduMap.animateMapStatus(GPS.u);
                //GPS.mLocationClient.requestLocation();
            }
        });
    }
}
