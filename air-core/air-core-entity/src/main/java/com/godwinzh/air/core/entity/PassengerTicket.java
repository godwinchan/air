package com.godwinzh.air.core.entity;

import lombok.Data;

/**
 * @ClassName PassengerTicket
 * @Description TODO
 * @Author godwin
 * @Date 2020/9/27 23:02
 * @Version 1.0
 */
@Data
public class PassengerTicket {
    private String passengerName;
    private int fare;
    private int tax;
    private String ticketNumber;
}
