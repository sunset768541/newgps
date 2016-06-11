package com.example.wang.gps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import java.util.HashMap;

/**
 * Created by sunset on 16/6/8.
 */
public class systemseting extends Activity {
    public ImageView hed;
    public int IMAGE_SELECT = 0;
    public int CROP = 1;
    Context cc;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); //声明使用自定义标题
        cc = this.getApplicationContext();
        setContentView(R.layout.seting);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.setingtitle);//自定义布局赋
        hed = (ImageView) findViewById(R.id.imageView2);
        hed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_SELECT);

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
