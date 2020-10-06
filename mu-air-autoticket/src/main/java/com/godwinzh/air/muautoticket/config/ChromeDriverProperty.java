package com.godwinzh.air.muautoticket.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName ChromeDriverProperty
 * @Description TODO
 * @Author godwin
 * @Date 2020/8/8 16:51
 * @Version 1.0
 */
@Component
@ConfigurationProperties(prefix = "chromedriver")
@Data
public class ChromeDriverProperty {
    private String binKey;
    private String binPath;
    private String exeKey;
    private String exePath;

}
