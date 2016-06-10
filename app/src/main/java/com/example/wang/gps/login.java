package com.example.wang.gps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.HashMap;

/**
 * Created by sunset on 16/6/8.
 */
public class login extends Activity {
    public EditText username;
    public EditText password;
    public Button regest;
    public Button loginb;
    public HashMap<String, String> userlogin = new HashMap<String, String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        sendlocation.getContext(this);
        username = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        loginb = (Button) findViewById(R.id.button5);
        loginb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    userlogin.put("commond", "login");
                    userlogin.put("username", username.getText().toString());
                    userlogin.put("password", password.getText().toString());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            sendlocation.sendGetRequest(userlogin);

                            switch(Integer.parseInt(sendlocation.response)){
                                case 0:{//dengl
                                    Log.e("登录", "ok");
                                    GPS.username=username.getText().toString();
                                    Intent toMain = new Intent(getApplication(), com.example.wang.gps.MainActivity.class);
                                    startActivity(toMain);
                                    finish();
                                    Looper.prepare();
                                    Toast.makeText(login.this, "登录成功", Toast.LENGTH_SHORT).show();
                                    Looper.loop();

                                }
                                case 1:{//用户名已存在
                                    Looper.prepare();
                                    Toast.makeText(login.this, "用户名不存在或密码错误", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                                case 2:{//访问服务器失败
                                    Log.e("访问服务器是",sendlocation.response);
                                }

                            }
                        }
                    }).start();
                } catch (NullPointerException e) {
                    Log.e("登录异常", Log.getStackTraceString(e));
                    Looper.prepare();
                    Toast.makeText(login.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
        regest = (Button) findViewById(R.id.button4);
        regest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydilog mm = new mydilog(login.this, "注册用户", new mydilog.OnCustomDialogListener() {
                    public void back(String name) {
                        Log.e("mydio", "jj");
                    }
                });
                mm.show();
            }
        });

    }
}
