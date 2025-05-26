package com.bank.bank_app;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.context.ApplicationContext;

import com.bank.bank_app.notification.NotificationService;
import com.bank.bank_app.notification.NotificationStrategy;

@Component
public class BankService {
    private TransactionLogger _logger;
    private NotificationService _notifier;
    private ApplicationContext _applicationContext;
    private currencyService _currencyService;

    private double _balance = 0;

    // @Autowired
    public BankService(TransactionLogger logger, NotificationService notifer, ApplicationContext applicationContext, currencyService currencyService){
        this._logger = logger;
        this._notifier = notifer;
        this._applicationContext = applicationContext;
        this._currencyService = currencyService;
    }

    public void deposit(double amount){
        double amountInBaseCurrency = _currencyService.convertToBaseCurrency(amount);
        _balance += amountInBaseCurrency;
        _logger.log("Deposit " + _currencyService.formatMoney(amount));
        _notifier.notifyUser("You deposited " + _currencyService.formatMoney(amount));
    }

    public void withdraw(double amount){
        double amountInBaseCurrency = _currencyService.convertToBaseCurrency(amount);
        if (_balance >= amountInBaseCurrency) {
            _balance -= amountInBaseCurrency;
            _logger.log("Withdraw " + _currencyService.formatMoney(amount));
            _notifier.notifyUser("You withdrew " + _currencyService.formatMoney(amount));
        } else {
            _notifier.notifyUser("Insufficient funds for withdrawal of " + _currencyService.formatMoney(amount));
        }
    }

    public double getBalance() {
        return _currencyService.convertFromBaseCurrency(_balance);
    }

    public String getFormattedBalance() {
        return _currencyService.formatMoney(getBalance());
    }

    public void setNotificationChannel(Integer number){
        switch (number) {
            case 1: // Email notification
                _notifier.setNotificationStrategy(
                    (NotificationStrategy) _applicationContext.getBean("emailNotification"));
                _logger.log("Switched to Email notifications");
                break;
            
            case 2: // SMS notification
                _notifier.setNotificationStrategy(
                    (NotificationStrategy) _applicationContext.getBean("smsNotification"));
                _logger.log("Switched to SMS notifications");
                break;
        
            default:
                _logger.log("Invalid notification channel: " + number);
                break;
        }
    }

    public void setCurrency(String currencyCode) {
        _currencyService.setCurrencyStrategy(currencyCode);
        _logger.log("Switched to " + currencyCode + " currency");
    }
}
