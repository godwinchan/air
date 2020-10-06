package com.godwinzh.air.muautoticket.entity;

import com.godwinzh.air.core.entity.PassengerTicket;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderResponse
 * @Description TODO
 * @Author godwin
 * @Date 2020/9/11 23:39
 * @Version 1.0
 */
@Data
public class OrderResponse implements Serializable {
    private static final long serialVersionUID = -8588219841066911279L;
    private String orderNumber;
    private String payNumber;
    private BigDecimal payAmount;
    private Map<String, PassengerTicket> tickets;
}
