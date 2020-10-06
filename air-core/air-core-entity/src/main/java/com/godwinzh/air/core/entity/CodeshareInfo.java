package com.godwinzh.air.core.entity;

import lombok.Data;

/**
 * @ClassName CodeshareInfo
 * @Description 航班共享信息
 * @Author godwin
 * @Date 2020/9/15 22:42
 * @Version 1.0
 */
@Data
public class CodeshareInfo {

    protected Boolean codeShare;
    protected String operatingCarrier;
    protected String operatingFlightNumber;
}
