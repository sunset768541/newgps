package com.example.wang.gps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;

import java.util.HashMap;

/**
 * Created by sunset on 16/6/8.
 * 系统设置界面管理模块
 */
public class systemseting extends Activity {
    public ImageView hed;
    public int IMAGE_SELECT = 0;
    public int CROP = 1;
    Context cc;
    TextView uname;
    TextView add;
    Button exitapp;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); //声明使用自定义标题
        cc = this.getApplicationContext();
        setContentView(R.layout.seting);
        SysApplication.getInstance().addActivity(this);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.setingtitle);//自定义布局赋
        uname=(TextView)findViewById(R.id.textView4);
        add=(TextView)findViewById(R.id.textView5);

        exitapp=(Button)findViewById(R.id.button10);
        exitapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Userinfo.isonline=false;
              final  HashMap<String,String> tellserviceonling=new HashMap();
                tellserviceonling.put("commond", "informuserstate");
                tellserviceonling.put("username",Userinfo.username);
                tellserviceonling.put("userstate","0");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendlocation.sendPostRequest(tellserviceonling);//发送下线通知
                    }
                }).start();
                try {
                    Thread.sleep(200);
                }
                catch (Exception e){
                    Log.e("退出","程序");
                }
                    Log.e("发送下线","下线");
               // if (sendlocation.canoffline){
                //
                      //  sendlocation.canoffline=false;
                        SysApplication.getInstance().exit();//退出程序

                  //   }
                //android.os.Process.killProcess(android.os.Process.myPid());   //获取PID
                //System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出
            }
        });
        hed = (ImageView) findViewById(R.id.imageView2);
        hed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_SELECT);

            }
        });
        hed.setImageBitmap(Userinfo.userhead);
        uname.setText("用户名: " + Userinfo.username);
        add.setText("当前位置: " + GPS.ad);
        View about =(LinearLayout)findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutdialog mm = new aboutdialog(systemseting.this);//如果找到匹配的marker，就将这个marker的数据传入diglog
                mm.show();
            }
        });
        final View maptype=(LinearLayout)findViewById(R.id.maptype);
        maptype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(systemseting.this).setTitle("选择地图类型").setIcon(
                      android.R.drawable.ic_dialog_info).setSingleChoiceItems(
                         new String[] { "2D地图", "卫星地图"},GPS.choicemaptype,
                        new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case 0:GPS.baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                                    break;
                                case 1:GPS.baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                                    break;
                            }
                            GPS.choicemaptype=which;
                       dialog.dismiss();
                       }
                    }).setNegativeButton("取消", null).show();

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
                hed.setImageBitmap(bitmap);
                Userinfo.userhead=bitmap;
            } catch (Exception e) {
                Log.e("eeee", Log.getStackTraceString(e));
            }
        }
        else if (resultCode==9){
            return;
        }
    }
}
