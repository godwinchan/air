package com.godwinzh.air.muautoticket.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName MuUrlProperty
 * @Description TODO
 * @Author godwin
 * @Date 2020/8/9 9:19
 * @Version 1.0
 */
@Component
@ConfigurationProperties(prefix = "muair")
@Data
public class MuUrlProperty {
    private String index;
}
