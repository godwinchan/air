package com.godwinzh.air.muautoticket.service;

import com.godwinzh.air.muautoticket.entity.LySession;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName YlPolicyService
 * @Description TODO
 * @Author godwin
 * @Date 2020/9/7 23:28
 * @Version 1.0
 */
public class YlPolicyService {
    private Map<String, LySession> sessionMap;

    public YlPolicyService() {
        sessionMap = new HashMap<>();
    }

    public void add(String account, String password) {
        if (!sessionMap.containsKey(account)) {

        }
    }
}
