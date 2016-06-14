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

import com.baidu.mapapi.map.Marker;

import java.util.HashMap;

/**
 * Created by sunset on 16/6/11.
 * 带图上的marker标记管理模块
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
    String arg;
    friendtrace ft;
    public markerDig(Context context,String name,HashMap<String,Object> parm,String arg) {//点击marker显示一个diglog的构造器,name为这个diglog的标题，parm是根据不同的marker传入的数据
        super(context);
        this.name = name;
        mycontext=context;
        this.parm=parm;
        this.arg=arg;
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
            public void onClick(View v) {//新建一个线程去更新这个marker的坐标
                ft=new friendtrace(arg,(Marker)parm.get("marker"));
                FriendInf.markthred.put(arg,ft);//将这个线程的名字存入Hashmap中,在关闭时可以拿到这个线程的地址
                ft.start();
                markerDig.this.dismiss();
                Toast.makeText(mycontext,"执行追踪",Toast.LENGTH_SHORT).show();
            }
        });
        removemark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    friendtrace ff=(friendtrace)FriendInf.markthred.get(arg);//在marker移除的时候关闭更新marker位置的线程
                    ff.isrun=false;
                }
                catch (NullPointerException e){
                }
                Marker marker=(Marker)parm.get("marker");//移除marker按钮的监听函数
                marker.remove();//移除marker
                markerDig.this.dismiss();//diglog消失
                FriendInf.removefromfri(arg);//在FriendInf中删除marker列表中的朋友信息
                Toast.makeText(mycontext,"移除marker",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
