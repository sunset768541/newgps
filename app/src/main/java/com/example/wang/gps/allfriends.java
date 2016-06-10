package com.example.wang.gps;

//import android.app.Fragment;
import android.content.Context;
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

            }
        });
    }
}
