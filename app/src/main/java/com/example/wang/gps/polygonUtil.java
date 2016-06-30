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
//            for (int m=0;m<polygon.size();m++){
//                LatLng ofLine=polygon.get(m);
//                for (int n=0;n<polygon.size();n++){
//                    if ()
//                }
//
//
//            }
            for (int j=2;j<polygon.size()-2;j++){
                double a=computedistance(polygon.get(0),polygon.get(j+1));
                double b=computedistance(polygon.get(j+1),polygon.get(j+2));
                double c=computedistance(polygon.get(0),polygon.get(j+2));
                if((b+c>a)&&(b+a>c)&&(c+a>b)){//两边之和大于第三遍，并排除三边共线的，三边共线会导致NaN
                double s=0.5*(a*c*Math.sin(Math.acos((Math.pow(a,2) + Math.pow(c,2) - Math.pow(b,2))/(2*a*c))));
                area=area+s;}
            }
        }
       else {
            Log.e("测多边形面积","不是多边形");
        }
        return area;

    }

}

