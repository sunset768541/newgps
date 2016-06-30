package com.example.wang.gps;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by sunset on 16/6/26.
 */
public class aboutdialog extends Dialog {


     public aboutdialog(Context context){
         super(context);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        setTitle("关于");
    }
}
