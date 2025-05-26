package com.bank.bank_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class currencyService {
    private currencyStrategy _currencyStrategy;
    private final ApplicationContext context;

    @Autowired
    public currencyService(@Qualifier("usdStrategy") currencyStrategy defaultStrategy, 
                            ApplicationContext context){
        this._currencyStrategy = defaultStrategy;
        this.context = context;
    }

    public double convertToBaseCurrency(double amount){
        return _currencyStrategy.toBaseCurrency(amount);
    }

    public double convertFromBaseCurrency(double amount){
        return _currencyStrategy.fromBaseCurrency(amount);
    }

    public void setCurrencyStrategy(String currencyCode) {
        switch (currencyCode.toUpperCase()) {
            case "USD":
                this._currencyStrategy = context.getBean("usdStrategy", currencyStrategy.class);
                break;
            case "VND":
                this._currencyStrategy = context.getBean("vndStrategy", currencyStrategy.class);
                break;
            default:
                throw new IllegalArgumentException("Unsupported currency: " + currencyCode);
        }
    }

    public String formatMoney(double amount){
        return _currencyStrategy.getSymbol() + " " + amount;
    }

    public String getCurrencyCode() {
        return _currencyStrategy.getCurrencyCode();
    }

}
