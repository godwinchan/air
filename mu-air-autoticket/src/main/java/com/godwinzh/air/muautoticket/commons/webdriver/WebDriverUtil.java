package com.godwinzh.air.muautoticket.commons.webdriver;

import com.godwinzh.air.muautoticket.config.ChromeDriverProperty;
import com.godwinzh.air.muautoticket.util.EnvironmentUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * @ClassName WebDriverUtil
 * @Description TODO
 * @Author godwin
 * @Date 2020/9/8 0:00
 * @Version 1.0
 */
@Component
public class WebDriverUtil {
    @Resource
    ChromeDriverProperty chromeDriverProperty;

    public WebDriver getChromeDirver(ChromeDriverService service) {
        if (EnvironmentUtil.isWindows()) {
            return getChromeDriver4Windows(service);
        } else
            return getChromeDriver4Linux();
    }

    public WebDriver getChromeDriver4Windows(ChromeDriverService service) {
        System.setProperty(chromeDriverProperty.getBinKey(), chromeDriverProperty.getBinPath());
        //"D:\\Programs\\chromedriver_win32\\chromedriver"
        //创建一个　ChromeDriver 接口
        service = new ChromeDriverService.Builder().usingDriverExecutable(
                new File(chromeDriverProperty.getExePath())).usingAnyFreePort().build();
        try {
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ChromeDriverService启动异常");
        }
        //创建一个　chrome 浏览器实例
        return new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
    }

    public WebDriver getChromeDriver4Linux() {
        ChromeOptions options = new ChromeOptions();
        //chrome安装位置
        //"webdriver.chrome.bin","/opt/google/chrome/chrome"
        System.setProperty(chromeDriverProperty.getBinKey(), chromeDriverProperty.getBinPath());
        //chromederiver存放位置"webdriver.chrome.driver", "/usr/bin/chromedriver"
        System.setProperty(chromeDriverProperty.getExeKey(), chromeDriverProperty.getExePath());
        //无界面参数
        options.addArguments("headless");
        //禁用沙盒
        options.addArguments("no-sandbox");
        WebDriver webDriver = new ChromeDriver(options);
        return webDriver;
    }
}
