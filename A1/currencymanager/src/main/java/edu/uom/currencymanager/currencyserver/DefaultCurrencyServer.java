package edu.uom.currencymanager.currencyserver;

public class DefaultCurrencyServer implements CurrencyServer {
    //Making use of parameter injection
    public double getExchangeRate(String sourceCurrency, String destinationCurrency, double value) {
         return value; //returns the given value
    }

}
