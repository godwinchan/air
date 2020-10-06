package com.godwinzh.air.core.entity;

import lombok.Data;

/**
 * @ClassName Passenger
 * @Description 乘客
 * @Author godwin
 * @Date 2020/9/14 23:42
 * @Version 1.0
 */
@Data
public class Passenger {
    private String name;
    private String birthday;
    private String idType;
    private String idNo;
}
