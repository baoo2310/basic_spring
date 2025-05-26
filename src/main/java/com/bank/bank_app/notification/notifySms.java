package com.bank.bank_app.notification;

import org.springframework.stereotype.Component;

@Component("smsNotification")
public class notifySms implements NotificationObserver {
    @Override
    public void update(String msg){
        System.out.println("[SMS] " + msg);
    }
}
