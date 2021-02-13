package edu.uom.currencymanager.currencies;

import org.junit.Test;

import static edu.uom.currencymanager.currencies.Util.formatAmount;
import static org.junit.Assert.*;

public class UtilTest {


    @Test
    public void formatAmountTest_withRounding() {
        //setup
        double original = 23.567;
        String expected ="23.57";

        //execute
        String actual = formatAmount(original);


        //verify
        assertEquals(expected, actual);
    }


    @Test
    public void formatAmountTest_OneDecimal() {
        //setup
        double original = 3.4;
        String expected ="3.40";

        //exercise
        String actual = formatAmount(original);

        //verify
        assertEquals(expected, actual);
    }
    @Test
    public void formatAmountTest_Zeros() {
        //setup
        double original = 0000;
        String expected ="0.00";

        //execute
        String actual = formatAmount(original);

        //verify
        assertEquals(expected, actual);
    }
}