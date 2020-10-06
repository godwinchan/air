package com.godwinzh.air.muautoticket.entity;

import com.godwinzh.air.core.entity.AirSegment;
import com.godwinzh.air.core.entity.Passenger;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName OrderRequest
 * @Description 出票请求
 * @Author godwin
 * @Date 2020/9/11 23:39
 * @Version 1.0
 */
@Data
public class OrderRequest implements Serializable {
    private static final long serialVersionUID = -8588219841066911279L;
    private String ticketType;
    private String account;
    private String password;
    private AirSegment airSegment;
    private List<Passenger> passengers;
    private Payment payment;

}
