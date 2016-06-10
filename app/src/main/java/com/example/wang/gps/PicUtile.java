package com.example.wang.gps;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * Created by sunset on 16/6/10.
 */
public class PicUtile {
    public static Bitmap getGreyImage(Bitmap old) {
        int width, height;
        height = old.getHeight();
        width = old.getWidth();
        Bitmap grey =  Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(old);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(grey, 0, 0, paint);
        return grey;
    }
}
