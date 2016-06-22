package com.example.wang.gps;

import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.List;

/**
 * Created by sunset on 16/6/22.
 */
public class polygonUtil {

    public static double computedistance(LatLng p1,LatLng p2){//计算两个坐标之间的距离
        double distance= DistanceUtil.getDistance(p1,p2);
        return distance;
    }
    public static double computealldistabce(List<LatLng> points){
        double distances=0;
        for (int i=0;i<points.size()-1;i++){
            distances=distances+computedistance(points.get(i),points.get(i+1));
        }

        return distances;
    }
    public static double computearea(List<LatLng> polygon){
        double area=0;
        if (!(polygon.size()<3)){
            for (int j=2;j<polygon.size()-2;j++){
                double a=computedistance(polygon.get(0),polygon.get(j+1));
             // Log.e("a=",Double.valueOf(a).toString());
                double b=computedistance(polygon.get(j+1),polygon.get(j+2));
               // Log.e("b=",Double.valueOf(b).toString());
                double c=computedistance(polygon.get(0),polygon.get(j+2));
                //Log.e("c=",Double.valueOf(c).toString());

                //Log.e("a2+c2-b2=", Double.valueOf((Math.pow(a, 2) + Math.pow(c, 2) - Math.pow(b, 2)) / (2 * a * c)).toString());
                //Log.e("acos=",Double.valueOf(Math.acos((Math.pow(a, 2) + Math.pow(c, 2) - Math.pow(b, 2)) / (2 * a * c))).toString());
              //  Log.e("sin(acos)=",Double.valueOf(Math.sin(Math.acos((Math.pow(a, 2) + Math.pow(c, 2) - Math.pow(b, 2)) / (2 * a * c)))).toString());

                if((b+c>a)&&(b+a>c)&&(c+a>b)){
                double s=0.5*(a*c*Math.sin(Math.acos((Math.pow(a,2) + Math.pow(c,2) - Math.pow(b,2))/(2*a*c))));
               // Log.e("第"+Integer.valueOf(j).toString()+"个三角形的面积为：",Double.valueOf(s).toString());
                area=area+s;}

            }
        }
       else {
            Log.e("测多边形面积","不是多边形");
        }
        return area;

    }


}

