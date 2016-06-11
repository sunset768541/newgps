package com.example.wang.gps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by wang on 2016/6/4.
 */
public class JSONUtile {
   public static JSONObject jsonObject;
    public static JSONObject getjson(){
        try {
             jsonObject=new JSONObject();
           // jsonObject.put("username",GPS.username);
            jsonObject.put("time",GPS.time);
            jsonObject.put("locatidis",GPS.ad);
            jsonObject.put("lati",GPS.la);
            jsonObject.put("lonti",GPS.lo);
        }
        catch (Exception e){
            Log.e("getjson",e.toString());
        }


        return jsonObject;
    }
    public static String imgToBase64(Bitmap bitmap) {//将Bitmap转换为字符串
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            out.flush();
            out.close();

            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public static Bitmap base64ToBitmap(String base64Data) {//将字符串转换为Bitmap
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
        }
    }


