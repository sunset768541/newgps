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
import java.util.Objects;


/**
 * Created by sunset on 16/5/29.
 * 所有朋友fragment
 */
public class allfriends extends Fragment{
    private View view;
    ListView listView;
    SimpleAdapter simpleAdapter;
    List<String> namelist=new ArrayList<>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.allfriends, container, false);
        listView=(ListView)getActivity().findViewById(R.id.listView);
        //registerForContextMenu(listView);
        Log.e("k", "onCreateView");

//        listView.setAdapter(simpleAdapter);
        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        android.util.Log.d("mark", "onCreate()--------->news Fragment");
        List<Map<String,Object>> listItems=new ArrayList<Map<String,Object>>();
       // for (int i=0;i<FriendInf.allfirname.length;i++){//在fragment创建的时候将朋友信息添加到Listview作为simpleAdapetr的数据
        Iterator iter = JSONUtile.allfriendobj.entrySet().iterator();
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
//        for (int i=0;i<JSONUtile.allfriendobj.size();i++){//在fragment创建的时候将朋友信息添加到Listview作为simpleAdapetr的数据
//
//
//        }
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
        Log.e("k","onCreate");


    }
    public void onStart() {
        super.onStart();
       Log.e("k","onStart");
        listView = (ListView) getActivity().findViewById(R.id.listView);
        registerForContextMenu(listView);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   // String usename=(String)JSONUtile.allfriend.get(position);
                String name=namelist.get(position);
                Iterator iter = JSONUtile.allfriendobj.entrySet().iterator();
                HashMap val=new HashMap();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object key = entry.getKey();
                    if (((String)key).equals(name)){
                         val =(HashMap) entry.getValue();
                        break;
                    }
                  }
/**
 * 该部分的功能是点击item之后，会在地图上创建一个用户的marker，并跳转到地图页面，在全部好又fragment中这个功能取消，替换的功能为弹出一个diglog显示这个好友的状态信息的，包括用户名，头像，在线（当前位置，当前状态）还是离线（最后登录时间，地点）
 *
                BitmapDescriptor bitmap =  BitmapDescriptorFactory.fromBitmap(BitmapUtil.getMarkerBitmap(JSONUtile.base64ToBitmap((String) val.get("friendheader"))));
                   // String add=FriendInf.addr[position];
                  //  String sta=FriendInf.sta[position];

                String usename=val.get("friendname").toString();
                    HashMap<String,Object> usin=new HashMap<String, Object>();
                    usin.put("userhead",JSONUtile.base64ToBitmap((String) val.get("friendheader")));
                    usin.put("username","用户名: "+usename);
                    usin.put("add","番禹区");
                    usin.put("stat", "移动");
                   // Log.e("usema", usename);
                    LatLng point1 = new LatLng(GPS.la+position/10, GPS.lo+position);//这里或的用户实时定位信息
                 if (!FriendInf.markerfri.contains(usename)) {
                    OverlayOptions uname = new MarkerOptions().position(point1).icon(bitmap).title(usename);
                    Marker uname1 = (Marker) GPS.baiduMap.addOverlay(uname);
                usin.put("marker",uname1);}
                FriendInf.addtomarkfri(usename, usin);//
                GPS.u = MapStatusUpdateFactory.newLatLng(point1);
                GPS.baiduMap.animateMapStatus(GPS.u);
                GPS.mLocationClient.requestLocation();//重新设置中心位置
                Intent tomai=new Intent(getContext(), com.example.wang.gps.MainActivity.class);
                startActivity(tomai);*/
            }
        });
    }
}
