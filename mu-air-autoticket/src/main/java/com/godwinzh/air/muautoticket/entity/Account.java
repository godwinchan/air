package com.godwinzh.air.muautoticket.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName Account
 * @Description TODO
 * @Author godwin
 * @Date 2020/9/7 23:45
 * @Version 1.0
 */
@Data
public class Account implements Serializable {
    private String account;
    private String password;
}
