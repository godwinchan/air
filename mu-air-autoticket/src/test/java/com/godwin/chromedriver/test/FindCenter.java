package com.godwin.chromedriver.test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * @ClassName FindCenter
 * @Description TODO
 * @Author godwin
 * @Date 2020/9/26 11:20
 * @Version 1.0
 */
public class FindCenter {
    public static void main(String[] args) {
        File f = new File("C:\\Users\\Administrator\\Desktop\\img\\20200926113852.png");
        BufferedImage bi;
        try {
            bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "png", baos);  //经测试转换的图片是格式这里就什么格式，否则会失真
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
            System.out.println("pointX:" + pointX + ";pointY:" + pointY + ";");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
