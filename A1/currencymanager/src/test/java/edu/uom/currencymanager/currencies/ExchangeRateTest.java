package edu.uom.currencymanager.currencies;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExchangeRateTest {

    ExchangeRate exchangeRate;

    @Before
    public void setUp()  {
        long time = 1000;

        //Dummy/Fake objects created to be passed to the ExchangeRate class to test functionality.
        Currency sourceCurrency = new Currency("SRC", "source", false);
        Currency destinationCurrency =  new Currency("DST", "destination", false);
        double rate =2.0;
        exchangeRate= new ExchangeRate(sourceCurrency,destinationCurrency,rate, time);

    }


    @Test
    public void toString_test() {

        //execute
        String actual = exchangeRate.toString();


        //verify
        assertEquals("SRC 1 = DST 2.00", actual);
    }
}