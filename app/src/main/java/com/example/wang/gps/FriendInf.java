package com.example.wang.gps;

import android.content.Context;

/**
 * Created by sunset on 16/6/10.
 */
public class FriendInf {
    public static String [] allfirname=new String[]{"王璞","王姐","胡可","彭泽","余力","富安","梁思","维金斯"};
    public static String [] addr=new String[]{"北京市四环","杭州市","西安","成都","深圳","上海","南昌","武汉"};
    public static String [] sta=new String[]{"静止中","移动中","静止中","静止中","移动中","移动中","静止中","移动中"};
    public static String [] statime=new String[]{"5分","10分","2小时","30分","6小时","12分","10分","20小时"};
    public static int []    head=new int[]{
            R.drawable.d001,R.drawable.d002,R.drawable.d003,R.drawable.d004,
            R.drawable.d005, R.drawable.d006, R.drawable.d007, R.drawable.d008
    };
    public  static Context cc;
    public static void  getCo(Context context){

        cc=context;
    }
}
