package com.godwinzh.air.muautoticket.entity;

import lombok.Data;
import org.openqa.selenium.WebDriver;

/**
 * @ClassName LySession
 * @Description TODO
 * @Author godwin
 * @Date 2020/9/7 23:51
 * @Version 1.0
 */
@Data
public class LySession {
    private Account account;
    private WebDriver webDriver;

    public LySession(String acc, String password, WebDriver webDriver) {
        account = new Account();
        account.setAccount(acc);
        account.setPassword(password);
        this.webDriver = webDriver;
    }
}
