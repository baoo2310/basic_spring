package com.bank.bank_app.notification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class NotificationService {
    private final List<NotificationObserver> _observer = new ArrayList<>();

    // @Autowired
    public NotificationService(List<NotificationObserver> observers){
        if(observers != null && !observers.isEmpty()){
            this._observer.addAll(observers);
        }
    }

    public void addObserver(NotificationObserver observer){
        if(!_observer.contains(observer)){
            _observer.add(observer);
        }
    }

    public void notifyAllObservers(String msg){
        for (NotificationObserver notificationObserver : _observer) {
            notificationObserver.update(msg);
        }
    }

    public void notifyUser(String msg){
        notifyAllObservers(msg);
    }

    public void removeObserver(NotificationObserver observer) {
        if(_observer.contains(observer)){
            _observer.remove(observer);
        }
    }
}
