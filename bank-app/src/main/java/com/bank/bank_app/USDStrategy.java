package com.bank.bank_app;

import org.springframework.stereotype.Component;

@Component("usdStrategy")
public class USDStrategy implements currencyStrategy {
    @Override
    public double toBaseCurrency(double amount){
        return amount;
    }

    @Override
    public double fromBaseCurrency(double amount){
        return amount;
    }

    @Override
    public String getSymbol(){
        return "$";
    }

    @Override
    public String getCurrencyCode(){
        return "USD";
    }
}
