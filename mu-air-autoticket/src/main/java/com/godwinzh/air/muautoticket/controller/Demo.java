package com.godwinzh.air.muautoticket.controller;

import com.godwinzh.air.muautoticket.config.ChromeDriverProperty;
import com.godwinzh.air.muautoticket.entity.OrderRequest;
import com.godwinzh.air.muautoticket.entity.OrderResponse;
import com.godwinzh.air.muautoticket.service.MuAutoTicketService;
import com.godwinzh.commons.api.entity.CommonRequest;
import com.godwinzh.commons.api.entity.CommonResponse;
import com.godwinzh.commons.api.entity.CommonStatusCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Demo
 * @Description TODO
 * @Author godwin
 * @Date 2020/8/8 18:59
 * @Version 1.0
 */
@RestController
@ConfigurationProperties(prefix = "test")
@RequestMapping(value = "/mu-air")
public class Demo {
    @Resource
    ChromeDriverProperty chromeDriverProperty;
    @Resource
    MuAutoTicketService muAutoTicketService;
    private String msg = "default";//现在我们在配置文件写hello.msg=world,因为简单就不再展示;如果那么默认为default.

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @RequestMapping(value = "/ticketing", method = RequestMethod.POST)
    public CommonResponse<OrderResponse> ticketing(@RequestBody CommonRequest<OrderRequest> orderRequestCommonRequest) {
        try {
            Long curTime = System.currentTimeMillis();

            CommonResponse<OrderResponse> rs =muAutoTicketService.ticket(orderRequestCommonRequest.getData());
//            new CommonResponse<>(orderRequestCommonRequest.getSessionId(), CommonStatusCode.SUCCESS, null, orderResponse);
//            OrderResponse orderResponse =

            rs.setProcessTime((int) (System.currentTimeMillis() - curTime));
            rs.setTimestamp(curTime);
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new CommonResponse<>(orderRequestCommonRequest.getSessionId(), CommonStatusCode.BUSINESS_EXCEPTION, ex.getMessage(), null);
        }
    }
//
//    @RequestMapping(value = "/tktnumber", method = RequestMethod.POST)
//    public CommonResponse<OrderResponse> tktnumber(@RequestBody CommonRequest<OrderRequest> orderRequestCommonRequest) {
//        try {
//            OrderResponse orderResponse = muAutoTicketService.ticketNumber(orderRequestCommonRequest.getData());
//            System.out.println(chromeDriverProperty.getExePath());
//            return new CommonResponse<>(orderRequestCommonRequest.getSessionId(), CommonStatusCode.SUCCESS, null, orderResponse);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return new CommonResponse<>(orderRequestCommonRequest.getSessionId(), CommonStatusCode.BUSINESS_EXCEPTION, ex.getMessage(), null);
//        }
//    }
}
