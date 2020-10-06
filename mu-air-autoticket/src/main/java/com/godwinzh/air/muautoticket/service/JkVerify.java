package com.godwinzh.air.muautoticket.service;

import com.godwinzh.air.muautoticket.commons.ImageBinaryzation;
import com.godwinzh.air.muautoticket.commons.Point;
import com.godwinzh.air.muautoticket.commons.Rectangle;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @ClassName JkVerify
 * @Description TODO
 * @Author godwin
 * @Date 2020/9/26 9:48
 * @Version 1.0
 */
public class JkVerify {
    public static Point getPosition(BufferedImage bitmap) {
        Point jk = new Point();
        byte[][] buffer = ImageBinaryzation.binaryzation(bitmap, -100, false);

        //Image gif = Image.FromFile(@"C:\Users\Administrator\Desktop\img\20191211075308.bmp");
        //byte[,] buffer = BitmapConvert.Binaryzation(new Bitmap(gif), -100, false);
        ImageBinaryzation.print(buffer);
        byte[][] spb = ImageBinaryzation.clone(buffer);
        int width = buffer.length,
                height = buffer[0].length;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                List<Point> tps = ImageBinaryzation.takeMap(spb, x, y);
                if (tps.size() > 500) {
                    //Console.WriteLine("x:{0},y:{1}", x, y);
                    jk.x = x;
                    jk.y = y;
                }
            }
        }

        return jk;
    }

    public static Point getJkRectangle(BufferedImage bi) {
        int x = bi.getWidth() / 2;
        int y = bi.getHeight() / 2;
        int pointX = 0;
        int pointY = 0;
        for (int i = 0; i < bi.getWidth(); i++) {
            int pixel = bi.getRGB(i, y);
            int rgbR = (pixel & 0xff0000) >> 16;
            int rgbG = (pixel & 0xff00) >> 8;
            int rgbB = (pixel & 0xff);
            if (rgbR + rgbG + rgbB > 350) {
                pointX = i;
                break;
            }
        }
        for (int i = 0; i < bi.getHeight(); i++) {
            int pixel = bi.getRGB(x, i);
            int rgbR = (pixel & 0xff0000) >> 16;
            int rgbB = (pixel & 0xff);
            int rgbG = (pixel & 0xff00) >> 8;
            if (rgbR + rgbG + rgbB > 350) {
                pointY = i;
                break;
            }
        }
        return new Point(pointX, pointY);
    }
}
