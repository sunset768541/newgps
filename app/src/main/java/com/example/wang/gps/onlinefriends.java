package com.example.wang.gps;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by sunset on 16/5/29.
 * 在线好友管理界面
 */
public class onlinefriends extends Fragment {
    private View view;
    ListView listView;
    SimpleAdapter simpleAdapter;
    List<String> namelist=new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.onlinefriends, container, false);
        listView=(ListView)getActivity().findViewById(R.id.listView2);
        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        android.util.Log.d("mark", "onCreate()--------->news Fragment");
        List<Map<String,Object>> listItems=new ArrayList<Map<String,Object>>();
        Iterator iter = JSONUtile.onlinefriendobj.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            HashMap val =(HashMap) entry.getValue();
            Map<String,Object> listItem=new HashMap<String, Object>();
            // listItem.put("header",FriendInf.head[i]);
            // HashMap data=(HashMap)JSONUtile.allfriendobj.get(i);//对allfriendobj HashMap进行遍历
            Log.e("friendname",val.get("friendname").toString());
            listItem.put("header", JSONUtile.base64ToBitmap((String)val.get("friendheader")));//测试
            listItem.put("friname",val.get("friendname"));//测试
            //listItem.put("friname",FriendInf.allfirname[i]);
            //  listItem.put("add",FriendInf.addr[i]);
            listItem.put("add","广州番禹区");//测试
            //listItem.put("sta",FriendInf.sta[i]);
            listItem.put("sta","移动中");
            //listItem.put("statime",FriendInf.statime[i]);
            listItem.put("statime","20");//测试
            listItems.add(listItem);
            namelist.add((String)key);
        }
        simpleAdapter=new SimpleAdapter(this.getContext(),listItems,R.layout.fiendlist,new String[]{"header","friname","add","sta","statime"},new int[]{
                R.id.header,R.id.name,R.id.add,R.id.sta,R.id.statime
        });
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder(){

            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if( (view instanceof ImageView) & (data instanceof Bitmap) ) {
                    ImageView iv = (ImageView) view;
                    Bitmap bm = (Bitmap) data;
                    iv.setImageBitmap(bm);
                    return true;
                }
                return false;

            }

        });
        Log.e("k", "3");


    }
    public void onStart() {
        super.onStart();
        listView = (ListView) getActivity().findViewById(R.id.listView2);
        registerForContextMenu(listView);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // String usename=(String)JSONUtile.allfriend.get(position);
                String name=namelist.get(position);
                Iterator iter = JSONUtile.allfriendobj.entrySet().iterator();
                HashMap showdata=new HashMap();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object key = entry.getKey();
                    if (((String)key).equals(name)){
                        showdata =(HashMap) entry.getValue();
                        break;
                    }
                }
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(BitmapUtil.getMarkerBitmap(JSONUtile.base64ToBitmap((String)showdata.get("friendheader"))));
                String usename = showdata.get("friendname").toString();
                HashMap<String, Object> usin = new HashMap<String, Object>();
                usin.put("userhead", JSONUtile.base64ToBitmap((String) showdata.get("friendheader")));
                usin.put("username", "用户名: " + usename);
                usin.put("add", (String)showdata.get("address"));
                usin.put("stat", "移动");
                // Log.e("usema", usename);
                LatLng point1 = new LatLng(Double.parseDouble((String)showdata.get("latitude")),Double.parseDouble((String)showdata.get("longtitude")));//这个坐标从showdata中获取
                //在地图上添加Marker，并显示
                if (!FriendInf.markerfri.contains(usename)) {//确定在地图上是否已存在marker，不存在添加，存在跳过
                    OverlayOptions uname = new MarkerOptions().position(point1).icon(bitmap).title(usename);//title用力啊分辨marker
                    Marker uname1 = (Marker) GPS.baiduMap.addOverlay(uname);
                    usin.put("marker", uname1);//
                }
                FriendInf.addtomarkfri(usename, usin);//
                GPS.u = MapStatusUpdateFactory.newLatLng(point1);
                GPS.baiduMap.animateMapStatus(GPS.u);
                GPS.mLocationClient.requestLocation();//重新设置中心位置
                Intent tomai = new Intent(getContext(), com.example.wang.gps.MainActivity.class);
                startActivity(tomai);
            }
        });
    }
}
