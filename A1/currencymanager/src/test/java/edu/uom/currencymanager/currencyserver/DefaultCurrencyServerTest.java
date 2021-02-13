package edu.uom.currencymanager.currencyserver;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultCurrencyServerTest {

    CurrencyServer defaultServer ;
    @Before
    public void setUp() throws Exception {
        //setup
        defaultServer = new DefaultCurrencyServer();
    }

    @Test
    public void getExchangeRate_0() {

        //execute
        Double result = defaultServer.getExchangeRate("A", "B",0);

        Double expected = Double.valueOf(0);
        //verify
        assertEquals(expected, result);


    }

    @Test
    public void getExchangeRate_Min() {

        //execute
        Double result = defaultServer.getExchangeRate("A", "B",Double.MAX_VALUE);

        Double expected = Double.MAX_VALUE;
        //verify
        assertEquals(expected, result);


    }

    @Test
    public void getExchangeRate_Max() {

        //execute
        Double result = defaultServer.getExchangeRate("A", "B",Double.MIN_VALUE);

        Double expected = Double.valueOf(Double.MIN_VALUE);
        //verify
        assertEquals(expected, result);


    }
}