package com.godwinzh.air.muautoticket.entity;

import lombok.Data;

/**
 * @ClassName Payment
 * @Description TODO
 * @Author godwin
 * @Date 2020/9/11 23:38
 * @Version 1.0
 */
@Data
public class Payment {
    private String payType;
    private String account;
    private String password;
}
