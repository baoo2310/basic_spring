package com.bank.bank_app;

import org.springframework.stereotype.Component;

@Component("vndStrategy")
public class VNDStrategy implements currencyStrategy {
    private static final double VND_TO_USD_RATE = 0.00004;
    private static final double USD_TO_VND_RATE = 25000.0;

    @Override
    public double toBaseCurrency(double amount){
        return amount * VND_TO_USD_RATE;
    }

    @Override
    public double fromBaseCurrency(double amount) {
        // Convert USD to VND
        return amount * USD_TO_VND_RATE;
    }
    
    @Override
    public String getSymbol() {
        return "VND";
    }
    
    @Override
    public String getCurrencyCode() {
        return "VND";
    }
}
