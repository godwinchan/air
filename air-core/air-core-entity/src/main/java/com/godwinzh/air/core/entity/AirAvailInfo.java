package com.godwinzh.air.core.entity;

import lombok.Data;

/**
 * @ClassName AirAvailInfo
 * @Description 舱位状态
 * @Author godwin
 * @Date 2020/9/15 22:44
 * @Version 1.0
 */
@Data
public class AirAvailInfo {
    /**
     * 舱位等线
     */
    protected String cabinClass;
    /**
     * 舱位
     */
    protected String bookingCode;
    /**
     * 状态
     */
    protected String bookingCounts;
    //protected List<AirAvailInfo.FareTokenInfo> fareTokenInfo;
    protected String providerCode;
//    @XmlAttribute(name = "HostTokenRef")
//    protected String hostTokenRef;
}
