package edu.uom.currencymanager.currencies;

public class ExchangeRate {

    public Currency sourceCurrency;
    public Currency destinationCurrency;
    public double rate;
    public long timeLastChecked;

    public ExchangeRate(Currency sourceCurrency, Currency destinationCurrency, double rate, long timeLastChecked) {
        this.sourceCurrency =sourceCurrency;
        this.destinationCurrency = destinationCurrency;
        this.rate =rate;
        this.timeLastChecked = timeLastChecked;
    }

    public String toString() {
        return sourceCurrency.code + " 1 = " + destinationCurrency.code + " " + Util.formatAmount(rate);
    }

}
