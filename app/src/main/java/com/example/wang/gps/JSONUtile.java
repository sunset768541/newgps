package com.example.wang.gps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2016/6/4.
 * JSON工具类，用于处理服务器发送过来的json数据
 */
public class JSONUtile {
   public static JSONObject jsonObject;
  // static   HashMap <String,Object> getdatafromservice=new HashMap<>();
 public static List allfriend=new ArrayList();
 public static Map<String,Object> allfriendobj=new HashMap<>();
 public static Map<String,Object> onlinefriendobj=new HashMap<>();
 //public static List<Map<String,String>> offlinefriendobj=new ArrayList();
 public static Map<String,Object> offlinefriendobj=new HashMap<>();
   // List<Map<String,Object>> listItems=new ArrayList<Map<String,Object>>();
    public static String getdatafromjson(String json,String loginuser){
        try {
            jsonObject=new JSONObject(json);
            sendlocation.response=Integer.valueOf(jsonObject.getInt("kkk")).toString();//得到服务器的响应代码
        }
        catch (Exception e){
            Log.e("getjson",e.toString());
        }


        return "jj";
    }
    public static void getjson(String json,String loginuser){//解析登录获得的json
        try {
            JSONObject jsondata=new JSONObject(json);
            JSONObject kk=jsondata.getJSONObject(loginuser);
            String ss=kk.getString(loginuser);
           // Log.e("getjson",ss);
            Userinfo.userhead=JSONUtile.base64ToBitmap(ss);
            JSONArray friendlist=jsondata.getJSONArray("friendlist");


            for (int i=0;i<friendlist.length();i++){
                Log.e("jsonarry",Integer.valueOf(i).toString());
                allfriend.add(friendlist.getString(i));
                HashMap <String,String> f=new HashMap<>();
                JSONObject fri=jsondata.getJSONObject(friendlist.getString(i));
                int state=fri.getInt("userstate");
                String userheader=fri.getString("userheader");
               // Log.e("jsonheader",userheader);
                f.put("friendname", friendlist.getString(i));
                Log.e("frinedname", friendlist.getString(i));
                f.put("friendstate", Integer.valueOf(state).toString());
                f.put("friendheader", userheader);
                try {
                    String time=fri.getString("time");
                    Log.e("time",time);
                    String latitude= fri.getString("latitude");
                    Log.e("latitude",latitude);
                    String longtitude= fri.getString("longtitude");
                    Log.e("longtitude",longtitude);
                    String address=fri.getString("address");
                    Log.e("address",address);
                    f.put("time",time);
                    f.put("latitude",latitude);
                    f.put("longtitude",longtitude);
                    f.put("address",address);
                    Log.e("获得了定位信息用户名",friendlist.getString(i));
                }
                catch (Exception e){
                    Log.e("定位信息为空的用户",friendlist.getString(i));
                }
                finally {
                   String ne= f.get("friendname");
                    allfriendobj.put(ne, f);//ne为好友的名字，也就是allfriendobj的key,f为value
                    //Log.e("json解析泛解析",allfriendobj.get(0).get("friendname").toString());
                if (state==1){
                    onlinefriendobj.put(ne, f);//将上线的好友加入到在线好友map
                }
                else {
                    offlinefriendobj.put(ne, f);//将离线好友加入到离线好友map
                }
                }
            }

        }
        catch (Exception e){

        }
    }
    public static void decodejson(String json){//解析不断请求数据时获得的json,json中不含用户的信息，不含朋友的头像信息
        try {
            JSONObject jsondata=new JSONObject(json);
            JSONArray friendlist=jsondata.getJSONArray("friendlist");
            Iterator iter = allfriendobj.entrySet().iterator();
            while (iter.hasNext()) {//遍历allfriedonj，获得其中的key和value
                Map.Entry entry = (Map.Entry) iter.next();
                String key =(String) entry.getKey();//每获得一个key，从jsondata中就获得一个用户的json对象，从对象中取,state,time，latitude，longtitude，address信息更新到val中
                HashMap<String,String> val=(HashMap)entry.getValue();
//                String userme=(String)val.get("friendname");
//                String header=(String)val.get("friendheader");
                JSONObject fri= jsondata.getJSONObject(key);
                int state=fri.getInt("userstate");
                val.put("friendstate",Integer.valueOf(state).toString());
                try {
                    val.put("latitude",fri.getString("latitude"));
                    val.put("longtitude",fri.getString("longtitude"));
                    val.put("address",fri.getString("address"));
                    val.put("time",fri.getString("time"));
                }
                catch (Exception e){
                    Log.e("所更新用户",key+"  无定位信息");
                }
                finally {
                    allfriendobj.put(key, val);
                    if (state == 1) {
                        onlinefriendobj.put(key, val);
                    } else {
                        offlinefriendobj.put(key, val);
                    }
                }
            }
//            for (int i=0;i<allfriendobj.size();i++){
//               // HashMap <String,String> f=new HashMap<>();
//                JSONObject fri = jsondata.getJSONObject(friendlist.getString(i));
//
//                ((HashMap)allfriendobj.get(friendlist.getString(i))).put("friendname", friendlist.getString(i));
//                ((HashMap)allfriendobj.get(friendlist.getString(i))).put("friendstate", Integer.valueOf(state).toString());
//                try {
//                    String time=fri.getString("time");
//                    String latitude= fri.getString("latitude");
//                    String longtitude= fri.getString("tongtitude");
//                    String address=fri.getString("address");
//                    ((HashMap)allfriendobj.get(friendlist.getString(i))).put("time", time);
//                    ((HashMap)allfriendobj.get(friendlist.getString(i))).put("latitude", latitude);
//                    ((HashMap)allfriendobj.get(friendlist.getString(i))).put("longtitude", longtitude);
//                    ((HashMap)allfriendobj.get(friendlist.getString(i))).put("address", address);
//                }
//                catch (Exception e){
//                    Log.e("定位信息为空","ok");
//                }
//               // allfriendobj.add(f);
//                if (state==1){
//                    if (!onlinefriendobj.containsKey(friendlist.getString(i))){//如果原来不在线
//                        onlinefriendobj.put(friendlist.getString(i),((HashMap)allfriendobj.get(friendlist.getString(i))));//将上线的好友加入到在线好友map
//                     }
//                }
//                else {
//                    if (!offlinefriendobj.containsKey(friendlist.getString(i))) {
//                        offlinefriendobj.put(friendlist.getString(i),((HashMap)allfriendobj.get(friendlist.getString(i))));//将离线好友加入到离线好友map
//                    }
//                }
//            }

        }
        catch (Exception e){
            Log.e("实时更新用户数据",Log.getStackTraceString(e));
        }

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


