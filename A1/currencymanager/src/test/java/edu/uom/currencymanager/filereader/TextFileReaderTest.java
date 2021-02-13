package edu.uom.currencymanager.filereader;

import edu.uom.currencymanager.currencies.Currency;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TextFileReaderTest {
    TextFileReader  textFileReader = new TextFileReader();
    BufferedReader bufferedReader ;

    @Before
    public void setUp()  {


    }

    @Test
    public void parsing_fail_empty()  {

        //setup
        List<Currency> currencies = new ArrayList<Currency>();

        //execute
        try {
            BufferedReader bufferedReader=  Mockito.mock(BufferedReader.class);
            Mockito.when(bufferedReader.readLine()).thenReturn("");
            textFileReader.parsing(currencies , bufferedReader );

        } catch (Exception e) {
            //e.printStackTrace();
            assertEquals(e.getMessage(), "Parsing error when reading currencies file.");

        }

    }





}