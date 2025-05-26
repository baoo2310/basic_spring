package com.bank.bank_app.notification;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class NotificationService {
    private NotificationStrategy _notifier;

    public NotificationService(@Qualifier("emailNotification") 
                    NotificationStrategy notificationStrategy){
        this._notifier = notificationStrategy;
    }   

    public void notifyUser(String msg){
        _notifier.notify(msg);
    }

    public void setNotificationStrategy(NotificationStrategy notifier){
        this._notifier = notifier;
    }
}
