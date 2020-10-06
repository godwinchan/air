package com.godwinzh.air.core.entity;

import lombok.Data;

/**
 * @ClassName SearchAirLeg
 * @Description TODO
 * @Author godwin
 * @Date 2020/9/15 22:36
 * @Version 1.0
 */
@Data
public class SearchAirLeg {
    protected String searchOrigin;
    protected String searchDestination;
    protected String searchDepTime;
    protected String searchArvTime;
}
