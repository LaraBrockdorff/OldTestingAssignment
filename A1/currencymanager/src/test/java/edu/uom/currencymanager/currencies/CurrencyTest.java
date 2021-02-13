package edu.uom.currencymanager.currencies;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CurrencyTest {

    Currency currency;

    @Before
    public void setUp() throws Exception {

         currency = new Currency("A", "aa", false);
    }

    @Test
    public void fromString_test_Valid() throws Exception {
        //execute
        Currency actualCurrency =  Currency.fromString("A,aa, no");

        //verify
        assertEquals(currency.code, actualCurrency.code);
        assertEquals(currency.name, actualCurrency.name);
        assertEquals(currency.major, actualCurrency.major);

    }

    //verification being done through annotation
    @Test(expected = NullPointerException.class)
    public void fromString_test_Invalid_null() throws Exception {
        //execute
        Currency actualCurrency =  Currency.fromString(null);

    }

    @Test
    public void toString_test() {

        //execute
        String actual = currency.toString();

        String expected = "A - aa";

        //verify
        assertEquals(expected, actual);
    }
}