package com.example.wang.gps;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sunset on 16/5/29.
 * 在线好友管理界面
 */
public class onlinefriends extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.onlinefriends, container, false);
        return view;
    }
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        android.util.Log.d("mark", "onCreate()--------->news Fragment");}
}
