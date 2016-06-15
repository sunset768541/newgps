package com.example.wang.gps;

import android.app.Dialog;
import android.content.Context;
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
 * Created by sunset on 16/6/10.
 * 添加好友功能代码块
 */
public class addfrienddig extends Dialog {
    public interface OnCustomDialogListener{
        public void back(String name);
    }
    private OnCustomDialogListener customDialogListener;
    private String name;
    public Button findfriend;
    public Button cancel;

    public EditText friendname;
    public TextView getfiendname;
    public ImageView friendheader;
    Context mycontext;
    public HashMap<String,String> findf=new HashMap<String,String>();
    public addfrienddig(Context context,String name,OnCustomDialogListener customDialogListener) {
        super(context);
        this.name = name;
        mycontext=context;
        sendlocation.getContext(mycontext);
        this.customDialogListener = customDialogListener;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfriend);
        //设置标题
        setTitle(name);

        friendname= (EditText)findViewById(R.id.editText6);
        getfiendname=(TextView)findViewById(R.id.textView3);
        friendheader=(ImageView)findViewById(R.id.imageView);
        findfriend= (Button)findViewById(R.id.button9);
        findfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    findf.put("commond", "findfriend");
                    findf.put("friendname", friendname.getText().toString());
                    findf.put("username",Userinfo.username);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            sendlocation.sendPostRequest(findf);

                            Log.e("recode", sendlocation.response);
                            switch(Integer.parseInt(sendlocation.response)){
                                case 0:{
                                    Log.e("发现朋友", "ok");
                                    addfrienddig.this.dismiss();
                                    Looper.prepare();
                                    Toast.makeText(mycontext, "添加朋友成功", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                                case 1:{//用户名已存在
                                    Looper.prepare();
                                    Toast.makeText(mycontext, "用户不存在", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                                case 2:{//访问服务器失败

                                }

                            }
                        }
                    }).start();
                }
                catch (NullPointerException e){

                }

            }
        });
        cancel=(Button)findViewById(R.id.button8);
        cancel.setOnClickListener(clickListener);
    }
    private View.OnClickListener clickListener = new View.OnClickListener() {


        public void onClick(View v) {
            customDialogListener.back(String.valueOf(friendname.getText()));
            Log.e("返回","ok");
            addfrienddig.this.dismiss();
        }
    };
}
