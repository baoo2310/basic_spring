package com.bank.bank_app;

public interface currencyStrategy {
    double toBaseCurrency(double amount);
    double fromBaseCurrency(double amount);
    String getSymbol();
    String getCurrencyCode();
}
