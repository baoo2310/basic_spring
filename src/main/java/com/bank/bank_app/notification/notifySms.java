package com.bank.bank_app.notification;

import org.springframework.stereotype.Component;

@Component("smsNotification")
public class notifySms implements NotificationStrategy {
    @Override
    public void notify(String msg){
        System.out.println("[SMS] " + msg);
    }
}
