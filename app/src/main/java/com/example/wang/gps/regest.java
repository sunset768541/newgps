package com.example.wang.gps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by sunset on 16/6/14.
 * 用户注册模块
 */
public class regest extends Activity{ EditText usernam;
    EditText password1;
    EditText password2;
    ImageView regestheader;
    HashMap<String,String> getuser=new HashMap<String, String>();
    public int IMAGE_SELECT = 0;
    public int CROP = 1;
    Bitmap header;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diglog);
        //设置标题
        usernam = (EditText)findViewById(R.id.editText3);
        password1 = (EditText)findViewById(R.id.editText4);
        password2 = (EditText)findViewById(R.id.editText5);
        Button ensure = (Button) findViewById(R.id.button6);
        regestheader=(ImageView)findViewById(R.id.imageView5);
        header=BitmapFactory.decodeResource(getResources(),R.drawable.def);
        regestheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_SELECT);
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
                        getuser.put("header", JSONUtile.imgToBase64(header));
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                sendlocation.sendPostRequest(getuser);

                                Log.e("recode", sendlocation.response);
                                switch(Integer.parseInt(sendlocation.response)){
                                    case 0:{//注册成功
                                        Log.e("注册", "ok");
                                        finish();
                                        Looper.prepare();
                                        Toast.makeText(regest.this, "注册成功", Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                        Intent gotologin=new Intent(getApplication(),login.class);
                                        startActivity(gotologin);

                                    }
                                    case 1:{//用户名已存在
                                        Looper.prepare();
                                        Toast.makeText(regest.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                                        Looper.loop();

                                    }
                                    case 2:{//访问服务器失败

                                    }

                                }
                            }
                        }).start();


                    }
                    else {
                        Toast.makeText(regest.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (NullPointerException e){
                    Log.e("注册异常",Log.getStackTraceString(e));
                    Toast.makeText(regest.this, "用户名或密码输入不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Button cancel=(Button)findViewById(R.id.button7);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotologin=new Intent(getApplication(),login.class);
                startActivity(gotologin);
                finish();
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_SELECT) {
            try {
                Uri uri = data.getData();
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(uri, "image/*");
                intent.putExtra("crop", "true");//可裁剪
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("outputX", 300);
                intent.putExtra("outputY", 300);
                intent.putExtra("scale", true);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                intent.putExtra("return-data", true);//若为false则表示不返回数据
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                intent.putExtra("noFaceDetection", true);
                //setResult(3);
                startActivityForResult(intent, CROP);

            }
            catch (NullPointerException e){
                setResult(9);

            }

            //   Log.e("url",uri.getEncodedPath());
//            Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
//            cursor.moveToFirst();
//            for (int i = 0; i < cursor.getColumnCount(); i++)
//            {// 取得图片uri的列名和此列的详细信息
//                Log.e("图片路径",i + "-" + cursor.getColumnName(i) + "-" + cursor.getString(i));
//            }

        } else if (resultCode == RESULT_OK) {
            try {
                //Log.e("图片", "剪切");
                Bitmap bitmap = data.getExtras().getParcelable("data");
                // String ss= JSONUtile.imgToBase64(bitmap);
                //  Log.e("base",ss);
                regestheader.setImageBitmap(bitmap);
               header=bitmap;
            } catch (Exception e) {
                Log.e("eeee", Log.getStackTraceString(e));
            }
        }
        else if (resultCode==9){
            return;
        }
    }

}
