package com.godwinzh.air.muautoticket.commons;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ImageBinaryzation
 * @Description TODO
 * @Author godwin
 * @Date 2020/9/25 22:51
 * @Version 1.0
 */
public class ImageBinaryzation {
    //二值化
    public static byte[][] binaryzation(BufferedImage map) {
        return binaryzation(map, 0, false);
    }

    public static byte[][] binaryzation(BufferedImage map, boolean reversal) {
        return binaryzation(map, 0, reversal);
    }

    /// <summary>
    /// 将颜色二值化
    /// </summary>
    /// <param name="map"></param>
    /// <param name="seed">平均颜色值上调整</param>
    /// <param name="isLight">是否浅色，ture则浅于平均值则为黑色，false 当颜色深于平均色则为黑色</param>
    /// <returns></returns>
    public static byte[][] binaryzation(BufferedImage map, int seed, boolean isLight) {
        byte[][] buffer = new byte[map.getWidth()][map.getHeight()];
        //RGB的平均值
        int pxMax = 255;
        long agvMax = Long.MAX_VALUE / pxMax;//最多支持平均值个数,超过这个值会导致算术溢出
        long redTotal = 0,
                greenTotal = 0,
                bluTotal = 0;

        List<Long> redAvgList = new ArrayList<>();
        List<Long> greenAvgList = new ArrayList<>();
        List<Long> bluAvgList = new ArrayList<>();
        int avgCnt = 0;
        for (int h = 0; h < map.getHeight(); h++) {
            for (int w = 0; w < map.getWidth(); w++) {
                avgCnt++;
                int pixel = map.getRGB(w, h);
                int rgbR = (pixel & 0xff0000) >> 16;
                int rgbG = (pixel & 0xff00) >> 8;
                int rgbB = (pixel & 0xff);
                redTotal += rgbR;
                greenTotal += rgbG;
                bluTotal += rgbB;
                //
                if (avgCnt >= agvMax) {
                    redAvgList.add(redTotal / avgCnt);
                    greenAvgList.add(greenTotal / avgCnt);
                    bluAvgList.add(bluTotal / avgCnt);
                    redTotal = 0;
                    greenTotal = 0;
                    bluTotal = 0;
                    avgCnt = 0;
                }
            }
        }
        //最后算一次
        redAvgList.add(redTotal / avgCnt);
        greenAvgList.add(greenTotal / avgCnt);
        bluAvgList.add(bluTotal / avgCnt);
        redTotal = 0;
        greenTotal = 0;
        bluTotal = 0;
        avgCnt = 0;
        //算出平均值
        long redAvg = 0, greenAvg = 0, bluAvg = 0;
        //红
        for (Long l : redAvgList) {
            redAvg += l;
        }
        redAvg = redAvg / redAvgList.size() + seed;
        //绿
        for (Long l : greenAvgList) {
            greenAvg += l;
        }
        greenAvg = greenAvg / greenAvgList.size() + seed;
        //蓝
        for (Long l : bluAvgList) {
            bluAvg += l;
        }
        bluAvg = bluAvg / bluAvgList.size() + seed;
        for (int h = 0; h < map.getHeight(); h++) {
            for (int w = 0; w < map.getWidth(); w++) {
                int pixel = map.getRGB(w, h);
                int rgbR = (pixel & 0xff0000) >> 16;
                int rgbG = (pixel & 0xff00) >> 8;
                int rgbB = (pixel & 0xff);
                if (rgbR + rgbG + rgbB > redAvg + greenAvg + bluAvg) {//大于就设置为黑
                    if (isLight) {
                        //map.SetPixel(w, h, Color.Black);
                        buffer[w][h] = 1;
                    } else {
                        //map.SetPixel(w, h, Color.White);
                        buffer[w][h] = 0;
                    }
                } else {
                    if (isLight) {
                        //map.SetPixel(w, h, Color.White);
                        buffer[w][h] = 0;
                    } else {
                        //map.SetPixel(w, h, Color.Black);
                        buffer[w][h] = 1;
                    }
                }
            }
        }
        return buffer;
    }

    public static byte[][] binaryzation(BufferedImage map, int r, int g, int b, boolean isLight) {
        return binaryzation(map, r, g, b, isLight, 0, 0, map.getWidth(), map.getHeight());
    }

    public static byte[][] binaryzation(BufferedImage map, int r, int g, int b, boolean isLight, int x, int y, int width, int height) {
        return binaryzation(map, r, g, b, 1, isLight, x, y, width, height);
    }

    protected static boolean compareColor(int pixel, int r, int g, int b, int type, int compare) {
        int rgbR = (pixel & 0xff0000) >> 16;
        int rgbG = (pixel & 0xff00) >> 8;
        int rgbB = (pixel & 0xff);
        if (compare > 0)
            return rgbR >= r && rgbG >= g && rgbB >= b;
        else if (compare < 0) {
            return rgbR <= r && rgbG <= g && rgbB <= b;
        } else {
            return rgbR == r && rgbG == g && rgbB == b;
        }
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="map"></param>
    /// <param name="r"></param>
    /// <param name="g"></param>
    /// <param name="b"></param>
    /// <param name="compare">小于，等于，大于</param>
    /// <param name="isLight"></param>
    /// <param name="x"></param>
    /// <param name="y"></param>
    /// <param name="width"></param>
    /// <param name="height"></param>
    /// <returns></returns>
    public static byte[][] binaryzation(BufferedImage map, int r, int g, int b, int compare, boolean isLight, int x, int y, int width, int height) {
        byte[][] buffer = new byte[width][height];
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                int pixel = map.getRGB(w + x, h + y);
                if (compareColor(pixel, r, g, b, 1, compare)) {//大于就设置为黑
                    if (isLight) {
                        //map.SetPixel(w, h, Color.Black);
                        buffer[w][h] = 1;
                    } else {
                        //map.SetPixel(w, h, Color.White);
                        buffer[w][h] = 0;
                    }
                } else {
                    if (isLight) {
                        //map.SetPixel(w, h, Color.White);
                        buffer[w][h] = 0;
                    } else {
                        //map.SetPixel(w, h, Color.Black);
                        buffer[w][h] = 1;
                    }
                }
            }
        }
        return buffer;
    }

    public static void print(byte[][] buffer) {
        for (int x = 0; x < buffer.length; x++) {
            for (int y = 0; y < buffer[x].length; y++) {
                if (buffer[x][y] == (byte) 1)
                    System.out.print("#");
                else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public static List<Point> takeMap(byte[][] buffer, int l, int t) {
        List<Point> pos = new ArrayList<>();
        int width = buffer.length,
                height = buffer[0].length;
        if (l < 0 || t < 0 || l >= width || t >= height) {
            return pos;
        }
        //Color c = map.GetPixel(l, t);
        if (buffer[l][t] == (byte)1) {
            buffer[l][t] = 0;
            pos.add(new Point(l, t));
            pos.addAll(takeMap(buffer, l - 1, t));
            pos.addAll(takeMap(buffer, l + 1, t));
            pos.addAll(takeMap(buffer, l, t - 1));
            pos.addAll(takeMap(buffer, l, t + 1));
            return pos;
        } else {
            return pos;
        }
    }

    public static byte[][] clone(byte[][] buffer) {
        byte[][] rs = new byte[buffer.length][buffer[0].length];
        for (int x = 0; x < buffer.length; x++) {
            for (int y = 0; y < buffer[x].length; y++) {
                rs[x][y] = buffer[x][y];
            }
        }
        return rs;
    }
}
