package com.example.wang.gps;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by sunset on 16/6/8.
 */
public class mydilog extends Dialog {
    public interface OnCustomDialogListener{
        public void back(String name);
    }
    private String name;
    Context mycontext;
    private OnCustomDialogListener customDialogListener;
    EditText usernam;
    EditText password1;
    EditText password2;
    ImageView regestheader;
    HashMap<String,String> getuser=new HashMap<String, String>();
    public mydilog(Context context,String name,OnCustomDialogListener customDialogListener) {
        super(context);
        this.name = name;
        mycontext=context;
        sendlocation.getContext(mycontext);
        this.customDialogListener = customDialogListener;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diglog);
        //设置标题
        setTitle(name);
        usernam = (EditText)findViewById(R.id.editText3);
        password1 = (EditText)findViewById(R.id.editText4);
        password2 = (EditText)findViewById(R.id.editText5);
        Button ensure = (Button) findViewById(R.id.button6);
        regestheader=(ImageView)findViewById(R.id.imageView5);
        regestheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(intent, IMAGE_SELECT);
            }
        });
        ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if((password1.getText().toString()).equals(password2.getText().toString())){
                        getuser.put("commond", "regest");
                        getuser.put("regestname", usernam.getText().toString());
                        getuser.put("regestpassword", password1.getText().toString());
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                sendlocation.sendPostRequest(getuser);

                                Log.e("recode",sendlocation.response);
                                switch(Integer.parseInt(sendlocation.response)){
                                    case 0:{//注册成功
                                        Log.e("注册", "ok");
                                        mydilog.this.dismiss();
                                        Looper.prepare();
                                        Toast.makeText(mycontext, "注册成功", Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }
                                    case 1:{//用户名已存在
                                        Looper.prepare();
                                        Toast.makeText(mycontext, "用户名已存在", Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }
                                    case 2:{//访问服务器失败

                                    }

                                }
                            }
                        }).start();


                    }
                    else {
                        Toast.makeText(mycontext, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (NullPointerException e){
                    Log.e("注册异常",Log.getStackTraceString(e));
                    Toast.makeText(mycontext, "用户名或密码输入不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Button cancel=(Button)findViewById(R.id.button7);
        cancel.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {


        public void onClick(View v) {
            customDialogListener.back(String.valueOf(usernam.getText()));
            Log.e("返回","ok");
            mydilog.this.dismiss();
        }
    };



}
