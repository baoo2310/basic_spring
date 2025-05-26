package com.bank.bank_app.notification;

import org.springframework.stereotype.Component;

@Component("emailNotification")
public class notifyMail implements NotificationStrategy {
    @Override
    public void notify(String msg){
        System.out.println("[EMAIL] " + msg);
    }
}
