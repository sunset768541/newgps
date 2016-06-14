package com.example.wang.gps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by sunset on 16/6/11.
 * 用户头像处理代码
 */
public class BitmapUtil{
    public int IMAGE_SELECT = 0;
    public int CROP = 1;
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {//获取圆形图片

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        //保证是方形，并且从中心画
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int w;
        int deltaX = 0;
        int deltaY = 0;
        if (width <= height) {
            w = width;
            deltaY = height - w;
        } else {
            w = height;
            deltaX = width - w;
        }
        final Rect rect = new Rect(deltaX, deltaY, w, w);
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        int radius = (int) (Math.sqrt(w * w * 2.0d) / 2);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
    public static Bitmap getMarkerBitmap(Bitmap bitmapsource) {//获取marker图片
        Bitmap bitmap=big(bitmapsource);
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight()+bitmap.getHeight()/2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
         Paint paint = new Paint();
        Paint re=new Paint();//marker画笔
        re.setColor(Color.DKGRAY);
        re.setStyle(Paint.Style.FILL);
        Path tri=new Path();//marker形状
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int w;
        int deltaX = 0;
        int deltaY = 0;
        if (width <= height) {
            w = width;
            deltaY = height - w;
        } else {
            w = height;
            deltaX = width - w;
        }
         Rect rect = new Rect(deltaX, deltaY, w, w);
         RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        int radius = (int) (Math.sqrt(w * w * 2.0d) / 2);
        tri.moveTo((float)deltaX,(float)deltaY+w/2);
        tri.lineTo((float) w, (float) deltaY + w / 2);
        tri.lineTo((float) deltaX + w / 2, (float) w + w / 2);
        tri.close();
        canvas.drawPath(tri, re);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
    private static Bitmap big(Bitmap bitmap) {//图片缩放
        Matrix matrix = new Matrix();
        float w=70;
        float h=70;
       // matrix.postScale((float)bitmap.getWidth()/w,(float)bitmap.getHeight()/h); //(float)bitmap.getHeight()/h长和宽放大缩小的比例
        matrix.postScale((float)w/bitmap.getWidth(),(float)h/bitmap.getHeight()); //(float)bitmap.getHeight()/h长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }
    public static Bitmap getGreyImage(Bitmap old) {//获得灰色的图像
        int width, height;
        height = old.getHeight();
        width = old.getWidth();
        //Bitmap grey =  Bitmap.createBitmap(width, height, old.Config);
        Canvas c = new Canvas(old);
        Paint paint = new Paint();
        paint.setAntiAlias(true); // 设置抗锯齿,也即是边缘做平滑处理
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0.1f);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(old, 0, 0, paint);
        return old;
    }


}
