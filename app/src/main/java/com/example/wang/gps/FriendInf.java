package com.example.wang.gps;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.baidu.mapapi.map.Marker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sunset on 16/6/10.
 */
public class FriendInf {
    public static String [] allfirname=new String[]{"王璞","王姐","胡可","彭泽","余力","富安","梁思","维金斯"};
    public static String [] addr=new String[]{"北京市四环","杭州市","西安","成都","深圳","上海","南昌","武汉"};
    public static String [] sta=new String[]{"静止中","移动中","静止中","静止中","移动中","移动中","静止中","移动中"};
    public static String [] statime=new String[]{"5分","10分","2小时","30分","6小时","12分","10分","20小时"};
    public static int []    head=new int[]{//改成Bitmap数组
            R.drawable.d001,R.drawable.d002,R.drawable.d003,R.drawable.d004,
            R.drawable.d005, R.drawable.d006, R.drawable.d007, R.drawable.d008
    };

    public  static Context cc;
    public static void  getCo(Context context){

        cc=context;
    }
    public static ArrayList<String> markerfri=new ArrayList<>();//全局变量存放用户，表示在地图上显示的用户marker
    public static HashMap<String,Object>firinf=new HashMap<>();//用来存用户和用户的信息当点击相应的marker的时候在弹出的diglog上显示相应的信息
    public static void  addtomarkfri(String arg,HashMap<String,Object> inf){//每次点击listview中的item时会触发这个函数
        if (!markerfri.contains(arg)){
        markerfri.add(arg);
        firinf.put(arg,inf);
        }
    }
    public static void removefromfri(String arg){//每次触发移除marker的事件的时候调用
        if (markerfri.contains(arg)){
        markerfri.remove(arg);
        firinf.remove(arg);
        }
    }

    /**
     * allfriendlist集合的作用是存储全部好友的数据，key String 表示好友的名字，vlaue Hashmap 表示该好友的数据该
     * Hashmao中存的数据为sta(是否在线),time,latitude,longtitude,addr,header(头像)
     */

      public static HashMap<String,HashMap<String,Object>> allfriendlist=new HashMap<>();
    /**
     * onlinefriendlist，key String 表示好友的名字，vlaue Hashmap 表示该好友的数据该
     * Hashmao中存的数据为sta(是否在线),time,latitude,longtitude,addr,header(头像)
     */

    public static HashMap<String,HashMap<String,Object>> onlinefriendlist=new HashMap<>();
    /**
     * offlinefriendlist，key String 表示好友的名字，vlaue Hashmap 表示该好友的数据该
     * Hashmao中存的数据为sta(是否在线),time,latitude,longtitude,addr,header(头像)
     */

    public static HashMap<String,HashMap<String,Object>> offlinefriendlist=new HashMap<>();



    /**
     * 这个方法的主要功能就是获取服务器发送过来的用户信息，将这些信息解析后存放到相应的容器中
     * 实现的功能有，更新全部好友，在线好友和离线好友的容器中的内容
     */
    public static void updatafriendlist(){//更新好友数据方法



    }
    public static HashMap<String,Thread>markthred=new HashMap<>();
}
