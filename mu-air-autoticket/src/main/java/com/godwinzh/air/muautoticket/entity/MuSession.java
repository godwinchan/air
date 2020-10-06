package com.godwinzh.air.muautoticket.entity;

import com.alibaba.fastjson.JSONObject;
import com.godwinzh.air.core.entity.Passenger;
import com.godwinzh.air.core.entity.PassengerTicket;
import com.godwinzh.air.muautoticket.commons.ImageUtil;
import com.godwinzh.air.muautoticket.commons.http.HttpClient;
import com.godwinzh.air.muautoticket.service.JkVerify;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName MuSession
 * @Description TODO
 * @Author godwin
 * @Date 2020/9/9 19:36
 * @Version 1.0
 */
@Data
public class MuSession {
    private Account account;
    private WebDriver webDriver;

    public MuSession(String acc, String password, WebDriver webDriver) {
        account = new Account();
        account.setAccount(acc);
        account.setPassword(password);
        this.webDriver = webDriver;
        this.webDriver.get("http://www.ceair.com/");
    }

    public boolean login() {
        if (webDriver != null) {
            Point point = webDriver.manage().window().getPosition();
            System.out.println(point);
            WebDriverWait wait = new WebDriverWait(webDriver, 60);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login")));
            WebElement loginLink = webDriver.findElement(By.id("login"));
            if (loginLink != null) {
                System.out.println(webDriver.findElement(By.tagName("body")).getLocation());
                //loginLink.click();
                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                js.executeScript("arguments[0].click();", loginLink);
                WebElement username = webDriver.findElement(By.name("username"));
                username.sendKeys(account.getAccount());
                WebElement password = webDriver.findElement(By.name("password"));
                password.sendKeys(account.getPassword());
                WebElement loginButton = webDriver.findElement(By.name("login"));
                loginButton.click();
                try {
                    Thread.sleep(10000);
                } catch (Exception ex) {
                }
                boolean gkFlag = false;//需求验证

                do {
                    gkFlag = false;
                    gkVerify(webDriver);
                    try {
                        Thread.sleep(10000);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    if (webDriver.getCurrentUrl().startsWith("https://passport.ceair.com/")) {
                        try {
                            WebElement item = webDriver.findElement(By.className("showInfo"));
                            System.out.println(item.getText());
                            return false;
                        } catch (NoSuchElementException ex) {
                            ex.printStackTrace();
                        }
                        try {
                            WebElement item = webDriver.findElement(By.className("geetest_panel_error"));
                            if ("block".equals(item.getCssValue("display"))) {
                                System.out.print(webDriver.findElement(By.className("geetest_panel_error_title")).getText());
                                webDriver.findElement(By.className("geetest_panel_error_content")).click();
                                try {
                                    Thread.sleep(3000);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                gkFlag = true;
                            }
                            System.out.println(item);
                        } catch (NoSuchElementException ex) {
                            ex.printStackTrace();
                        }
                    }
                } while (gkFlag);
//                WebDriverWait wait = new WebDriverWait(webDriver, 2);
//                wait.until(ExpectedConditions.presenceOfElementLocated(By.className("showInfo")));
//
            }
        }
        if (webDriver.getCurrentUrl().startsWith("http://www.ceair.com/")) {
            if (exist(webDriver, By.id("login")))
                return false;
            else
                return true;
        } else
            return false;
    }


    public boolean exist(WebDriver webDriver, By selector) {
        try {
            webDriver.findElement(selector);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public void searchFlight(String org, String dst, Date flightDate) {
        String url = StringUtils.join("http://www.ceair.com/booking/", org, "-", dst, "-", DateFormatUtils.format(flightDate, "yyMMdd"), "_CNY.html");
        webDriver.get(url);
        //System.out.println(webDriver.getPageSource());
    }

    public void gkVerify(WebDriver webDriver) {
        BufferedImage bufferedImage = snapshot((TakesScreenshot) webDriver);
        com.godwinzh.air.muautoticket.commons.Point vkPoint = null;
        try {
                    /*String fileName1 = "C:\\Users\\Administrator\\Desktop\\img\\" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".png";
                    ImageUtil.writeImageFile(bufferedImage, fileName1);*/
            com.godwinzh.air.muautoticket.commons.Point jkPoint = JkVerify.getJkRectangle(bufferedImage);
            if (jkPoint == null || jkPoint.x == 0)
                return;
            bufferedImage = bufferedImage.getSubimage(jkPoint.x, jkPoint.y, 280, 280);
                    /*String fileName2 = "C:\\Users\\Administrator\\Desktop\\img\\" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".png";
                    ImageUtil.writeImageFile(bufferedImage, fileName2);*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (bufferedImage != null) {
            vkPoint = JkVerify.getPosition(bufferedImage);
        }
        if (vkPoint == null || vkPoint.x == 0)
            return;
        WebElement geetest_panel_next = webDriver.findElement(By.className("geetest_panel_next"));
        /*System.out.printf("x:%s;y:%s;X:%s;Y%s", geetest_panel_next.getLocation().x, geetest_panel_next.getLocation().y, geetest_panel_next.getLocation().getX(), geetest_panel_next.getLocation().getY());
        System.out.printf("x:%s; y:%s, width:%s; height:%s", geetest_panel_next.getRect().x, geetest_panel_next.getRect().y, geetest_panel_next.getRect().width, geetest_panel_next.getRect().height);
        //String url = "http://127.0.0.1:9601/godwinchan/positon-service/position?x=817&y=854&width=278&height=286";
        String url = "http://127.0.0.1:9601/godwinchan/positon-service/position?x=820&y=329&width=278&height=286";
        String html = HttpClient.doGet(url);
        System.out.println(html);
        JSONObject jsonObject = JSONObject.parseObject(html);
        int length = Integer.parseInt(jsonObject.getString("X")) - 15;*/
        int length = vkPoint.x - 15;
        Random random = new Random(System.currentTimeMillis());
        int max = length / 10;
        int step = 0;
        WebElement geetest_slider_button = webDriver.findElement(By.className("geetest_slider_button"));

        Actions action = new Actions(webDriver);
        int idx = 0;
        if (geetest_slider_button != null) {
            do {
                int xOffset = random.nextInt(max) + max / 2;
                int y = random.nextInt(3);
                int fx = random.nextInt(2);
                if (fx == 1) {
                    y = y * -1;
                }
                System.out.printf("x:%s;y:%s%n", xOffset, y);
                action.clickAndHold(geetest_slider_button).moveByOffset(
                        xOffset, y);
                step += xOffset;
                idx++;
            } while (length - step > max);
        }
        action.clickAndHold(geetest_slider_button).moveByOffset(
                length - step, 0);
        //拖动完释放鼠标
        action.moveToElement(geetest_slider_button).release();
        //组织完这些一系列的步骤，然后开始真实执行操作
        Action actions = action.build();
        System.out.println(idx);
        actions.perform();
    }

    public boolean selectFlight(String flightNum, String cabin) {
        //flight-departure/.booking-select
        //flight-error//没有航班的时候
        if (webDriver.getCurrentUrl().startsWith("http://www.ceair.com/booking")) {
            WebDriverWait wait = new WebDriverWait(webDriver, 60);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("sylvanas_3")));
            String searchingClass = "";
            int waitTime = 0;
            do {
                searchingClass = webDriver.findElement(By.id("flight-departure")).findElement(By.className("searching")).getAttribute("class");
                if (!searchingClass.contains("none")) {
                    try {
                        Thread.sleep(10000);//等待10秒
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } while (!searchingClass.contains("none") && waitTime++ < 10);
            WebElement sylvanas_3 = webDriver.findElement(By.id("sylvanas_3"));
            List<WebElement> flights = sylvanas_3.findElements(By.className("flight"));
            for (WebElement flight : flights) {
                if (flight.getText().contains(flightNum)) {
                    List<WebElement> lowests = flight.findElements(By.name("lowest"));
                    for (WebElement lowest : lowests) {
                        lowest.click();
                        List<WebElement> dls = flight.findElement(By.className("body")).findElement(By.className("product-list")).findElements(By.tagName("dl"));
                        for (WebElement dl : dls) {
                            WebElement dt = dl.findElement(By.tagName("dt"));
                            if (dt.getText().contains(cabin) && !dt.getText().contains("青老年特惠")
                                    && !dt.getText().contains("开学季")
                                    && !dt.getText().contains("多人特惠")) {
                                List<WebElement> dds = dl.findElements(By.tagName("dd"));
                                dds.get(dds.size() - 1).click();
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void snapshot(TakesScreenshot drivername, String filename) {
        // this method will take screen shot ,require two parameters ,one is driver name, another is file name


        File scrFile = drivername.getScreenshotAs(OutputType.FILE);
        // Now you can do whatever you need to do with it, for example copy somewhere
        try {
            System.out.println("save snapshot path is:E:/" + filename);
            FileUtils.copyFile(scrFile, new File("E:\\" + filename));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Can't save screenshot");
            e.printStackTrace();
        } finally {
            System.out.println("screen shot finished");
        }
    }

    public static BufferedImage snapshot(TakesScreenshot drivername) {

        try {
            byte[] bytes = drivername.getScreenshotAs(OutputType.BYTES);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            return ImageIO.read(bais);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean book(List<Passenger> passengers) {
        if (webDriver.getCurrentUrl().startsWith("http://www.ceair.com/booking/passenger.html")) {
            WebDriverWait wait = new WebDriverWait(webDriver, 60);
            wait.until(ExpectedConditions.elementToBeClickable(By.name("idNo")));
            WebElement form_field = webDriver.findElement(By.className("form-field"));
            WebElement btn_passenger = webDriver.findElement(By.className("btn-passenger"));
            for (int i = 1; i < passengers.size(); i++) {
                try {
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                btn_passenger.click();
            }
            List<WebElement> list = form_field.findElements(By.className("list"));
            int i = 0;
            for (WebElement listItem : list) {
                Passenger passenger = passengers.get(i);
                i++;
                WebElement paxName = listItem.findElement(By.name("paxName"));
                paxName.sendKeys(passenger.getName());
                WebElement idNo = listItem.findElement(By.name("idNo"));
                idNo.sendKeys(passenger.getIdNo());
                WebElement mobile = listItem.findElement(By.name("mobile"));
                mobile.sendKeys("13387554248");
            }
            webDriver.findElement(By.id("btn_passenger")).click();
        }
        return true;
    }

    public boolean paxConfirm() {
        if (webDriver.getCurrentUrl().startsWith("http://www.ceair.com/booking/confirm.html")) {
            try {
                WebDriverWait wait = new WebDriverWait(webDriver, 60);
                wait.until(ExpectedConditions.elementToBeClickable(By.id("pax_confirm")));
                WebElement pax_confirm = webDriver.findElement(By.id("pax_confirm"));
                pax_confirm.click();
                WebElement bookDomesNext = webDriver.findElement(By.id("bookDomesNext"));
                bookDomesNext.click();
                return  true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public boolean pay(Payment payment) {
        WebDriverWait wait = new WebDriverWait(webDriver, 60);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("pay")));
        webDriver.findElement(By.id("pay")).findElement(By.xpath("article/div[2]/figure/div")).getText();
        webDriver.findElement(By.name("UNI_YEEPAY_002")).click();
        webDriver.findElement(By.id("btn_flight_pay")).click();

        for (String key : webDriver.getWindowHandles()) {
            webDriver.switchTo().window(key);
            if (webDriver.getPageSource().contains("易宝支付收银台")) {
                break;
            }
        }
        webDriver.findElement(By.name("userAccount")).sendKeys(payment.getAccount());
        webDriver.findElement(By.name("tradePassword")).sendKeys(payment.getPassword());
        webDriver.findElement(By.id("passPayButton")).click();
        try {
            Thread.sleep(5000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (webDriver.getWindowHandles().size() > 1)
            webDriver.close();
        for (String key : webDriver.getWindowHandles()) {
            webDriver.switchTo().window(key);
            break;
        }
        return false;
    }

    public String getOrderNumber() {
        try {
            if (webDriver.getCurrentUrl().startsWith("https://unipay.ceair.com/unipay/preparepay/pay")) {
                WebDriverWait wait = new WebDriverWait(webDriver, 60);
                wait.until(ExpectedConditions.elementToBeClickable(By.id("pay")));
                WebElement pay = webDriver.findElement(By.id("pay"));
                WebElement orderInfo = pay.findElement(By.xpath("article/div[2]/figure/div"));
                System.out.println(orderInfo.getText());
                if (StringUtils.isNotEmpty(orderInfo.getText())) {
                    return orderInfo.getText().replace("订单编号:", "");
                } else
                    return orderInfo.getText();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean getTicketNumbers(OrderResponse orderResponse) {
        String orderNumber = orderResponse.getOrderNumber();
        webDriver.get("http://ecrm.ceair.com/order/detail.html?orderNo=" + orderNumber + "&orderType=AIR");
        WebDriverWait wait = new WebDriverWait(webDriver, 60);
        wait.until(ExpectedConditions.elementToBeClickable(By.className("card")));
        WebElement card = webDriver.findElement(By.className("card"));
        WebElement order_status = webDriver.findElement(By.className("order-status"));
        WebElement orderPrice = webDriver.findElement(By.className("order-price"));
        try {
            String orderPriceStr = orderPrice.findElement(By.xpath("span/em")).getText().replaceAll("[\\D\\.]", "");
            orderResponse.setPayAmount(new BigDecimal(orderPriceStr));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (!order_status.getText().contains("交易成功"))
            return false;
        List<WebElement> passengerHeads = card.findElement(By.className("passengerHead")).findElements(By.tagName("li"));
        List<WebElement> travelSections = card.findElement(By.className("travelSection")).findElements(By.tagName("hgroup"));

        Map<String, String> data_rel = new HashMap<>();
        Map<String, PassengerTicket> ticketList = new HashMap<>();
        for (WebElement item : passengerHeads) {
            data_rel.put(item.getAttribute("data-rel"), item.getAttribute("title"));
            ticketList.put(item.getAttribute("title"), new PassengerTicket());
        }
        try {
            List<WebElement> basicInfos = card.findElement(By.className("passengerHead")).findElements(By.className("basicInfo"));
            for (WebElement basicInfo : basicInfos) {
                String key = basicInfo.getAttribute("name");
                passengerHeads.get(basicInfos.indexOf(basicInfo)).click();
                WebElement price = basicInfo.findElement(By.className("price"));
                String adtPrice = price.findElement(By.xpath("dd[2]")).getText().replaceAll("[\\D\\.]", "");
                String taxPrice = price.findElement(By.xpath("dd[4]")).getText().replaceAll("[\\D\\.]", "");
                ticketList.get(data_rel.get(key)).setFare(Integer.parseInt(adtPrice));
                ticketList.get(data_rel.get(key)).setPassengerName(data_rel.get(key));
                ticketList.get(data_rel.get(key)).setTax(Integer.parseInt(taxPrice));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        int idx = 0;
        for (WebElement item : travelSections) {
            passengerHeads.get(idx).click();
            idx++;
            String name = item.getAttribute("name");
            name = data_rel.get(name);
            WebElement ticket = item.findElement(By.xpath("div/ul[1]/span[1]"));
            String ticketNumber = ticket.getText();
            ticketNumber = ticketNumber.replace("票号：", "");
            ticketNumber = ticketNumber.replace("-", "");
            ticketList.get(name).setTicketNumber(ticketNumber);
            System.out.println("name:" + name + "ticketNumber:" + ticketNumber);
        }
        orderResponse.setTickets(ticketList);
        return true;
    }

    public boolean checkLogin() {
        //https://easternmiles.ceair.com/members/
        webDriver.get("https://easternmiles.ceair.com/members/");
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.className("loginHead")));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * @Author godwin
     * @Description //booking-select检查系统繁忙 true不繁忙 false繁忙
     * @Date 2020/9/27 15:00
     * @Param
     * @Return
     **/
    public boolean checkSearchBusy() {
        if (webDriver.getCurrentUrl().contains("www.ceair.com/booking")) {
            try {
                WebDriverWait wait = new WebDriverWait(webDriver, 10);
                wait.until(ExpectedConditions.elementToBeClickable(By.className("booking-select")));
                WebElement booking_select = webDriver.findElement(By.className("booking-select"));
                if (booking_select.getText().contains("网络繁忙，请检查后，重新再试！ ")) {
                    return false;
                }
                return true;
            } catch (TimeoutException tex) {
                tex.printStackTrace();
            }
        }
        return false;
    }

    public boolean reVerify() {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, 2);
            wait.until(ExpectedConditions.elementToBeClickable(By.id("centerWindow")));
            WebElement centerWindow = webDriver.findElement(By.id("centerWindow"));
            webDriver.switchTo().frame(0);
            wait.until(ExpectedConditions.elementToBeClickable(By.className("geetest_btn")));
            WebElement geetest_btn = webDriver.findElement(By.className("geetest_btn"));
            geetest_btn.click();
            gkVerify(this.webDriver);
            return true;
        } catch (TimeoutException tex) {
            tex.printStackTrace();
        }
        return false;
    }
}
