package com.bank.bank_app;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.context.ApplicationContext;

import com.bank.bank_app.notification.NotificationObserver;
import com.bank.bank_app.notification.NotificationService;

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

    public void addNotificationChannel(String observerName) {
        NotificationObserver observer = 
            (NotificationObserver) _applicationContext.getBean(observerName);
        _notifier.addObserver(observer);
        _logger.log("Added " + observerName + " notification channel");
    }
    
    public void removeNotificationChannel(String observerName) {
        NotificationObserver observer = 
            (NotificationObserver) _applicationContext.getBean(observerName);
        // Assuming you add a removeObserver method to NotificationService
        _notifier.removeObserver(observer);
        _logger.log("Removed " + observerName + " notification channel");
    }

    public void setCurrency(String currencyCode) {
        _currencyService.setCurrencyStrategy(currencyCode);
        _logger.log("Switched to " + currencyCode + " currency");
    }
}
