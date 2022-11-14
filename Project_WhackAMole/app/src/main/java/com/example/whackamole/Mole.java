package com.example.whackamole;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Mole {

    // Point holds two integer coordinates
    public static final Point TOP_LEFT_POINT = new Point(130, 970);
    public static Bitmap MOLE_PNG;

    private int animationFrame;
    private int xHole, yHole;

    public int getHoleX() {
        return xHole;
    }

    public int getHoleY() {
        return yHole;
    }

    /**
     * 通常是传一个random generate 的Integer作为地鼠出现的地方
     *
     * @param x 地鼠出现的x坐标
     * @param y 地鼠出现的y坐标
     */
    public void setHole(int x, int y) {
        xHole = x;
        yHole = y;

    }

    public int getAnimationFrame() {
        return animationFrame;
    }

    public void setAnimationFrame(int x) {
        animationFrame = x;
    }

    /**
     * 获得地鼠在 Pixel 3 屏幕上实际出现的地址
     *
     * @return 地鼠在 Pixel 3 屏幕上实际出现的地址。数据格式为Point，涵盖x和y坐标
     */
    public Point getMoleCoords() {
        return new Point(TOP_LEFT_POINT.x + xHole * 330, TOP_LEFT_POINT.y + yHole * 340 - animationFrame * 13);
    }

    public Bitmap getBitmap() {
        return MOLE_PNG;
    }

    public void drawMole(Canvas canvas, Paint paint) {
        canvas.drawBitmap(MOLE_PNG, getMoleCoords().x, getMoleCoords().y, paint);
    }
}
