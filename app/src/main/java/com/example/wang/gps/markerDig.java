package com.example.wang.gps;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by sunset on 16/6/11.
 */
public class markerDig extends Dialog {
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
    public markerDig(Context context,String name,HashMap<String,Object> parm) {
        super(context);
        this.name = name;
        mycontext=context;
        this.parm=parm;
       sendlocation.getContext(mycontext);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.markerdig);
        //设置标题
        setTitle(name);
        usernamhead = (ImageView)findViewById(R.id.imageView3);
        username = (TextView)findViewById(R.id.textView6);
        add = (TextView)findViewById(R.id.textView7);
        stat = (TextView)findViewById(R.id.textView14);
         trace = (Button) findViewById(R.id.button11);
         removemark = (Button) findViewById(R.id.button12);
        usernamhead.setImageBitmap((Bitmap) parm.get("userhead"));
        username.setText((String)parm.get("username"));
        add.setText((String) parm.get("add"));
        stat.setText((String) parm.get("stat"));
        trace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mycontext,"点击了显示轨迹",Toast.LENGTH_SHORT).show();
            }
        });
        removemark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mycontext,"点击了移除marker",Toast.LENGTH_SHORT).show();

            }
        });

    }
}
