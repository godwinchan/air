package com.godwinzh.air.muautoticket.service;

import com.godwinzh.air.muautoticket.commons.webdriver.WebDriverUtil;
import com.godwinzh.air.muautoticket.config.ChromeDriverProperty;
import com.godwinzh.air.muautoticket.config.MuUrlProperty;
import com.godwinzh.air.muautoticket.entity.MuSession;
import com.godwinzh.air.core.entity.Passenger;
import com.godwinzh.air.muautoticket.entity.OrderRequest;
import com.godwinzh.air.muautoticket.entity.OrderResponse;
import com.godwinzh.commons.api.entity.CommonResponse;
import com.godwinzh.commons.api.entity.CommonStatusCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName MuAutoTicketService
 * @Description TODO
 * @Author godwin
 * @Date 2020/8/8 19:05
 * @Version 1.0
 */
@Service
public class MuAutoTicketService {
    @Resource
    ChromeDriverProperty chromeDriverProperty;
    @Resource
    WebDriverUtil webDriverUtil;
    @Resource
    MuUrlProperty muUrlProperty;
    private Map<String, MuSession> sessionMap;

    public MuAutoTicketService() {
        sessionMap = new HashMap<>();
    }

    public CommonResponse<OrderResponse> ticket(OrderRequest orderRequest) throws Exception {
        CommonResponse<OrderResponse> commonResponse = new CommonResponse<>();
        OrderResponse orderResponse = new OrderResponse();
        commonResponse.setData(orderResponse);
        MuSession muSession = null;
        if (!sessionMap.containsKey(orderRequest.getAccount())) {
            WebDriver webDriver = webDriverUtil.getChromeDirver(null);
            muSession = new MuSession(orderRequest.getAccount(), orderRequest.getPassword(), webDriver);
            if (muSession.login()) {
                sessionMap.put(orderRequest.getAccount(), muSession);
            } else {

            }
        } else {
            muSession = sessionMap.get(orderRequest.getAccount());
            if (!muSession.checkLogin()) {
                if (!muSession.login()) {
                    commonResponse.setStatusDesc("登录失败");
                    commonResponse.setStatusCode(CommonStatusCode.BUSINESS_ERROR);
                    return commonResponse;
                }
            }
        }
//        //测试
//        orderResponse.setOrderNumber("71200927739940");
//        muSession.getTicketNumbers(orderResponse);


        muSession.searchFlight(orderRequest.getAirSegment().getOrigin(), orderRequest.getAirSegment().getDestination(), DateUtils.parseDate(orderRequest.getAirSegment().getDepartureTime(), "yyyy-MM-dd HH:mm:ss"));
        muSession.reVerify();
        if (!muSession.checkSearchBusy()) {

        }
        if (!muSession.selectFlight(orderRequest.getAirSegment().getCarrier() + orderRequest.getAirSegment().getFlightNumber(), orderRequest.getAirSegment().getAirAvailInfo().get(0).getBookingCode())) {
            commonResponse.setStatusCode(CommonStatusCode.BUSINESS_ERROR);
            commonResponse.setStatusDesc("找不到航班");
            return commonResponse;
        }
        muSession.reVerify();
        muSession.book(orderRequest.getPassengers());
        muSession.reVerify();
        muSession.paxConfirm();
        muSession.reVerify();
        orderResponse.setOrderNumber(muSession.getOrderNumber());
        if (!StringUtils.isNotEmpty(orderResponse.getOrderNumber()))
            muSession.pay(orderRequest.getPayment());
        else {
            commonResponse.setStatusCode(CommonStatusCode.BUSINESS_ERROR);
            commonResponse.setStatusDesc("下单失败");
            return commonResponse;
        }
        try {
            int getTicketTime = 0;
            while (muSession.getTicketNumbers(orderResponse) == false
                    && getTicketTime++ < 10) {
                Thread.sleep(10000);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return commonResponse;
    }

}
