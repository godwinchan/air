package com.godwinzh.air.muautoticket.util;

/**
 * @ClassName EnvironmentUtil
 * @Description TODO
 * @Author godwin
 * @Date 2020/8/8 16:43
 * @Version 1.0
 */
public class EnvironmentUtil {

  public static boolean isWindows() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            //System.out.println(os + " can't gunzip");
            return true;
        } else
            return false;
    }
}
