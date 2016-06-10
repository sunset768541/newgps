package com.example.wang.gps;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by sunset on 16/6/8.
 */
public class systemseting extends Activity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); //声明使用自定义标题
        setContentView(R.layout.seting);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.setingtitle);//自定义布局赋
    }
}
