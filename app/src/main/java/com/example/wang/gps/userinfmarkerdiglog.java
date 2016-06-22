package com.example.wang.gps;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.Marker;

import java.util.HashMap;

/**
 * Created by sunset on 16/6/15.
 */
public class userinfmarkerdiglog extends Dialog {
        public interface OnCustomDialogListener{
            public void back(String name);
        }
        private String name;
        Context mycontext;
        private OnCustomDialogListener customDialogListener;
        ImageView usernamhead;
        TextView username;
        TextView add;
        TextView stat;
        Button trace;
        Button removemark;
        HashMap<String,Object> parm;
        String arg;//为用户的名字
         TextView latitude;
         TextView longtitude;
    Button outbound;
    Button closebound;
        friendtrace ft;
        public userinfmarkerdiglog(Context context,String name,HashMap<String,Object> parm,String arg) {//点击marker显示一个diglog的构造器,name为这个diglog的标题，parm是根据不同的marker传入的数据
            super(context);
            this.name = name;
            mycontext=context;
            this.parm=parm;
            this.arg=arg;
            sendlocation.getContext(mycontext);
        }

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.userinfodigloh);
            //设置标题
            setTitle(name);
            usernamhead = (ImageView)findViewById(R.id.imageView3);
            username = (TextView)findViewById(R.id.textView6);
            add = (TextView)findViewById(R.id.textView7);
            stat = (TextView)findViewById(R.id.textView14);
            usernamhead.setImageBitmap((Bitmap) parm.get("userhead"));
            username.setText((String) parm.get("username"));
            add.setText((String) parm.get("add"));
            stat.setText((String) parm.get("stat"));
            latitude=(TextView)findViewById(R.id.textView18);//纬度
            longtitude=(TextView)findViewById(R.id.textView19);//经度
            latitude.setText("纬度为:"+Double.valueOf(GPS.la));
            longtitude.setText("经度为:"+Double.valueOf(GPS.lo));
            outbound=(Button)findViewById(R.id.button15);
            outbound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    drawtrace.drawpat(true);
                    userinfmarkerdiglog.this.dismiss();
                }
            });
            Log.e("用户的marker", "ok");
            closebound=(Button)findViewById(R.id.button16);
            closebound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawtrace.drawpat(false);
                    for (int i=0;i<drawtrace.userpolyline.size();i++){
                        drawtrace.userpolyline.get(i).remove();//删除所有线
                    }
                    userinfmarkerdiglog.this.dismiss();
                }
            });
        }

    }


