package edu.uom.currencymanager.currencyserver;

public interface CurrencyServer {
    double rate = 0;

    /**
     * Returns the current exchange rate for a source and destination currency.
     * @param sourceCurrency - Source currency.
     * @param destinationCurrency - Destination currency.
     * @return The exchange rate from source currency to destination currency.
     */
    public double getExchangeRate(String sourceCurrency, String destinationCurrency, double value);

}
