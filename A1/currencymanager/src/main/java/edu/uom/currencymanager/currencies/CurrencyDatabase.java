package edu.uom.currencymanager.currencies;

import edu.uom.currencymanager.currencyserver.CurrencyServer;
import edu.uom.currencymanager.currencyserver.DefaultCurrencyServer;
import edu.uom.currencymanager.filereader.FileReader_fromPath;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CurrencyDatabase {

    CurrencyServer currencyServer;
    List<Currency> currencies = new ArrayList<Currency>();
    HashMap<String, ExchangeRate> exchangeRates = new HashMap<String, ExchangeRate>();
    FileReader_fromPath reader ;
    Random random = new Random();



    String currenciesFile = "target" + File.separator + "classes" + File.separator + "currencies.txt";

    public CurrencyDatabase(FileReader_fromPath reader) throws Exception {
        this.reader = reader;
        init();
    }

    public void init() throws Exception {
        //Initialise currency server
        currencyServer = new DefaultCurrencyServer();
        currencies = reader.readFile(currenciesFile);
    }

    public Currency getCurrencyByCode(String code) {

        for (Currency currency : currencies) {
            if (currency.code.equalsIgnoreCase(code)) {
                return currency;
            }
        }

        return null;
    }

    public boolean currencyExists(String code) {
        return getCurrencyByCode(code) != null;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public List<Currency> getMajorCurrencies() {
        List<Currency> result = new ArrayList<Currency>();

        for (Currency currency : currencies) {
            if (currency.major) {
                result.add(currency);
            }
        }

        return result;
    }

    public ExchangeRate getExchangeRate(String sourceCurrencyCode, String destinationCurrencyCode) throws  Exception {
        long FIVE_MINUTES_IN_MILLIS = 300000;  //5*60*100

        ExchangeRate result = null;

        Currency sourceCurrency = getCurrencyByCode(sourceCurrencyCode);
        if (sourceCurrency == null) {
            throw new Exception("Unkown currency: " + sourceCurrencyCode);
        }

        Currency destinationCurrency = getCurrencyByCode(destinationCurrencyCode);
        if (destinationCurrency == null) {
            throw new Exception("Unkown currency: " + destinationCurrencyCode);
        }

        //Check if exchange rate exists in database
        String key = sourceCurrencyCode + destinationCurrencyCode;
        if (exchangeRates.containsKey(key)) {
            result = exchangeRates.get(key);
            if (System.currentTimeMillis() - result.timeLastChecked > FIVE_MINUTES_IN_MILLIS) {
                result = null;
            }
        }

        if (result == null) {
            //Random being generated where needed and passed as a parameter
            double rate = currencyServer.getExchangeRate(sourceCurrencyCode, destinationCurrencyCode, random.nextDouble()*1.5);
            result = new ExchangeRate(sourceCurrency,destinationCurrency, rate, System.currentTimeMillis());

            //Cache exchange rate
            exchangeRates.put(key, result);

            //Cache inverse exchange rate
            String inverseKey = destinationCurrencyCode+sourceCurrencyCode;
            exchangeRates.put(inverseKey, new ExchangeRate(destinationCurrency, sourceCurrency, 1/rate, System.currentTimeMillis()));
        }

        return result;
    }

    public void addCurrency(Currency currency) throws Exception {

        //Save to list
        currencies.add(currency);

        //Persist
        persist();
    }

    public void deleteCurrency(String code) throws Exception {

        //Save to list
        currencies.remove(getCurrencyByCode(code));

        //Persist
        persist();
    }

    public void persist() throws Exception {

        //Persist list
        BufferedWriter writer = new BufferedWriter(new FileWriter(currenciesFile));

        writer.write("code,name,major\n");
        for (Currency currency : currencies) {
            writer.write(currency.code + "," + currency.name + "," + (currency.major ? "yes" : "no"));
            writer.newLine();
        }

        writer.flush();
        writer.close();
    }

}