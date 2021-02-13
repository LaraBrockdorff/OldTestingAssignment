package edu.uom.currencymanager.currencies;

import edu.uom.currencymanager.filereader.FileReader_fromPath;
import edu.uom.currencymanager.filereader.TextFileReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class CurrencyDatabaseTest {

    CurrencyDatabase currencyDatabase ;
    Currency testCurrency1;
    Currency testCurrency2;
    Currency testCurrencyMajor;
    Currency testCurrencyAdded;

    List<Currency> currenciesList = new ArrayList<Currency>();
    HashMap<String, ExchangeRate> exchangeRates = new HashMap<String, ExchangeRate>();

    FileReader_fromPath fileReader ;

    long time = System.currentTimeMillis();



    @Before
    public void setUp() throws Exception {


        testCurrency1 = new Currency("TSA", "test1", false);
        testCurrency2 = new Currency("TSB", "test2", false);
        testCurrencyAdded = new Currency("ADD", "added", false);
        testCurrencyMajor = new Currency("MJR", "testMJR", true);

        currenciesList.add(testCurrency1);
        currenciesList.add(testCurrency2);
        currenciesList.add(testCurrencyMajor);

        //Mocking the file reader. Tests no longer fail if the curriences
        // file is not presnet, as they are not relying on it

        //the tests are now independant of the file
        fileReader = Mockito.mock(TextFileReader.class);

        Mockito.doReturn(currenciesList).when(fileReader).readFile(Mockito.anyString());

        currencyDatabase = new CurrencyDatabase(fileReader);


    }

    @After
    public void tearDown() throws Exception {
        currencyDatabase = null;
    }



    @Test(expected = NullPointerException.class)
    public void getCurrencyByCode_nullList() {
        //setup
        currencyDatabase.currencies = new ArrayList<Currency>(null);

        //execute
         Currency actualReturned =currencyDatabase.getCurrencyByCode("TSA");

        //verify
        //done in annotation asserting that a null pointer exception is thrown


    }

    @Test
    public void getCurrencyByCode_EmptyList() {
        //setup
        currencyDatabase.currencies = new ArrayList<Currency>();

        //execute
        Currency actualReturned =currencyDatabase.getCurrencyByCode("TSA");

        //verify
        assertEquals(actualReturned, null);

    }

    @Test
    public void getCurrencyByCode_matching() {

        //setup
        currencyDatabase.currencies = currenciesList;

        //execute
        Currency actualReturned =currencyDatabase.getCurrencyByCode("TSA");

        //verify
        assertEquals(actualReturned, testCurrency1);
    }

    @Test
    public void getCurrencyByCode_notFound() {
        //setup
        currencyDatabase.currencies = currenciesList;

        //execute
        Currency actualReturned =currencyDatabase.getCurrencyByCode("FAKE");

        //verify
        assertEquals(actualReturned, null);
    }

    @Test
    public void currencyExists_true() {

        //setup
        currencyDatabase.currencies = currenciesList;

        //execute
        boolean actualReturned =currencyDatabase.currencyExists("TSA");

        //verify
        assertEquals(actualReturned, true);
    }

    @Test
    public void currencyExists_false() {

        //setup
        currencyDatabase.currencies = currenciesList;

        //execute
        boolean actualReturned =currencyDatabase.currencyExists("FAKE");

        //verify
        assertEquals(actualReturned, false);
    }

    @Test(expected = NullPointerException.class)
    public void currencyExists_NullList() {
        //setup
        currencyDatabase.currencies = null;

        //execute
        boolean actualReturned =currencyDatabase.currencyExists("TSA");

        //verify
        //veifies nullpointer exception is thrown form annotation
    }

    @Test
    public void currencyExists_EmptyList() {
        //setup
        currencyDatabase.currencies = new ArrayList<Currency>();

        //execute
        boolean actualReturned =currencyDatabase.currencyExists("TSA");

        //verify
        assertEquals(actualReturned, false);
    }

    @Test
    public void getCurrencies_containing() {
        //setup
        currencyDatabase.currencies = currenciesList;

        //execute
        List<Currency> actualReturned =currencyDatabase.getCurrencies();

        //verify
        assertEquals(actualReturned, currenciesList);

    }

    @Test
    public void getCurrencies_Empty() {
        //setup
        currencyDatabase.currencies = new ArrayList<Currency>();

        //execute
        List<Currency> actualReturned =currencyDatabase.getCurrencies();

        //verify
        assertEquals(actualReturned, new ArrayList<Currency>());

    }

    @Test
    public void getCurrencies_null() {

        //setup
        currencyDatabase.currencies = null;

        //execute
        List<Currency> actualReturned =currencyDatabase.getCurrencies();

        //verify
        assertEquals(actualReturned, null);
    }

    @Test
    public void getMajorCurrencies_present() {
        //setup
        List<Currency> expectedList = new ArrayList<Currency>();
        expectedList.add(testCurrencyMajor);

        currencyDatabase.currencies = currenciesList;

        //execute
        List<Currency> actualReturned =currencyDatabase.getMajorCurrencies();

        //verify
        assertEquals(actualReturned,expectedList);
    }

    @Test
    public void getMajorCurrencies_notPresnt() {
        //setup
        List<Currency> newCurrenciesList = new ArrayList<Currency>();
        newCurrenciesList.add(testCurrency2);
        newCurrenciesList.add(testCurrency1);
        currencyDatabase.currencies = newCurrenciesList;

        //execute
        List<Currency> actualReturned =currencyDatabase.getMajorCurrencies();

        //verify
        assertEquals(actualReturned, new ArrayList<Currency>());
    }

    @Test
    public void getMajorCurrencies_emptyList() {
        //setup
        currencyDatabase.currencies = new ArrayList<Currency>();

        //execute
        List<Currency> actualReturned =currencyDatabase.getMajorCurrencies();

        //verify
        assertEquals(actualReturned, new ArrayList<Currency>());
    }

    @Test(expected = NullPointerException.class)
    public void getMajorCurrencies_nullList() {
        //setup
        currencyDatabase.currencies = null;

        //execute
        List<Currency> actualReturned =currencyDatabase.getMajorCurrencies();

        //verify
        //done in annotation
    }


    @Test
    public void getExchangeRate_sourceNull() {

        //Setup
        currencyDatabase.currencies = currenciesList;

        //execute and verify if exception is thrown
        try {
            ExchangeRate exchangeRateReturned = currencyDatabase.getExchangeRate(null, testCurrency2.code);
        } catch (Exception e) {
           // e.printStackTrace();
            assertEquals(e.getMessage(), "Unkown currency: null");
        }


    }

    @Test
    public void getExchangeRate_destinationNull() {

        //setup
        currencyDatabase.currencies = currenciesList;

        //execute and verify if exception is thrown
        try {
            ExchangeRate exchangeRateReturned = currencyDatabase.getExchangeRate(testCurrency1.code, null);
        } catch (Exception e) {
            //e.printStackTrace();
            assertEquals(e.getMessage(), "Unkown currency: null");
        }

    }

    @Test
    public void getExchangeRate_notPresentInHashT() throws Exception {

    //setup
        //cleaning the hashmap before we start
        HashMap<String, ExchangeRate> exchangeRatesClean = new HashMap<String, ExchangeRate>() ;
        currencyDatabase.exchangeRates=exchangeRatesClean;

        //uses currency service . Use stubs to control the exchange rate in tests
        currencyDatabase.random =  Mockito.mock(Random.class);

        Mockito.when(currencyDatabase.random.nextDouble()).thenReturn(1.3/1.5);


        //populating currencies list
        currencyDatabase.currencies = currenciesList;


        //setting up expected objects
        ExchangeRate exchangeRateExpected= new ExchangeRate(testCurrency1,testCurrency2,1.3,time);

    //execute
        ExchangeRate exchangeRateReturned =
                currencyDatabase.getExchangeRate(testCurrency1.code, testCurrency2.code);

    //verify
        assertEquals(exchangeRateExpected.toString(), exchangeRateReturned.toString());



    }

    @Test
    public void getExchangeRate_PresentInHashT() throws Exception {

        //setup

        ExchangeRate exchangeRate1= new ExchangeRate(testCurrency1,testCurrency2,1.3, time);
        ExchangeRate exchangeRate2= new ExchangeRate(testCurrency2,testCurrency1,1/1.3, time);


        //poulating the hashmap before we start
        HashMap<String, ExchangeRate> exchangeRatesStart = new HashMap<String, ExchangeRate>() ;
        exchangeRatesStart.put(testCurrency1.code+testCurrency2.code, exchangeRate1);
        exchangeRatesStart.put(testCurrency2.code+testCurrency1.code, exchangeRate2);
        currencyDatabase.exchangeRates=exchangeRatesStart;

        //no longer require the use of stubs

        //populating currencies list
        currencyDatabase.currencies = currenciesList;


        //execute
        ExchangeRate exchangeRateReturned =
                currencyDatabase.getExchangeRate(testCurrency1.code, testCurrency2.code);

        //verify
        assertEquals(exchangeRate1.toString(), exchangeRateReturned.toString());


    }

    @Test
    public void getExchangeRate_PresentInHash_Expired() throws Exception {

        //setup

        ExchangeRate exchangeRate1= new ExchangeRate(testCurrency1,testCurrency2,1.30, time-3000005);
        ExchangeRate exchangeRate2= new ExchangeRate(testCurrency2,testCurrency1,1/1.3, time-3000005);


//        exchangeRate1.timeLastChecked = Long.MAX_VALUE;
//        exchangeRate2.timeLastChecked = Long.MAX_VALUE;
        //poulating the hashmap before we start
        HashMap<String, ExchangeRate> exchangeRatesStart = new HashMap<String, ExchangeRate>() ;
        exchangeRatesStart.put(testCurrency1.code+testCurrency2.code, exchangeRate1);
        exchangeRatesStart.put(testCurrency2.code+testCurrency1.code, exchangeRate2);
        currencyDatabase.exchangeRates=exchangeRatesStart;

        //no longer require the use of stubs

        //populating currencies list
        currencyDatabase.currencies = currenciesList;

        currencyDatabase.random =  Mockito.mock(Random.class);

        Mockito.when(currencyDatabase.random.nextDouble()).thenReturn(0.5);

        ExchangeRate exchangeRateExpected= new ExchangeRate(testCurrency1,testCurrency2,0.75, time);


        //execute
        ExchangeRate exchangeRateReturned =
                currencyDatabase.getExchangeRate(testCurrency1.code, testCurrency2.code);

        //verify
        assertEquals(exchangeRateExpected.toString(), exchangeRateReturned.toString());


    }

    @Test
    public void addCurrency_validCurr_validList() throws Exception {



        //setup
        currencyDatabase.currencies = currenciesList;

        //execute
        currencyDatabase.addCurrency(testCurrencyAdded);

        List<Currency> expectedList = currenciesList;
        expectedList.add(testCurrencyAdded);

        //verify
        assertEquals(currencyDatabase.currencies, expectedList);

    }





    @Test
    public void deleteCurrency_occurs() throws Exception {

        //setup
        currencyDatabase.currencies = currenciesList;

        //execute
        currencyDatabase.deleteCurrency(testCurrency1.code);

        List<Currency> expectedList = currenciesList;
        expectedList.remove(testCurrency1);

        //verify
        assertEquals(currencyDatabase.currencies, expectedList);
    }

    @Test
    public void deleteCurrency_NotInList() throws Exception {

        //setup
        currencyDatabase.currencies = currenciesList;

        //execute
        currencyDatabase.deleteCurrency(testCurrencyAdded.code);


        //verify
        assertEquals(currencyDatabase.currencies, currenciesList);
    }



}