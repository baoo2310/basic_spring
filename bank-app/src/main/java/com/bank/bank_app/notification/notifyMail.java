package com.bank.bank_app.notification;

import org.springframework.stereotype.Component;

@Component("emailNotification")
public class notifyMail implements NotificationObserver {
    @Override
    public void update(String msg){
        System.out.println("[EMAIL] " + msg);
    }
}
