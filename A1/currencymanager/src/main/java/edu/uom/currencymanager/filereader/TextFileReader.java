package edu.uom.currencymanager.filereader;

import edu.uom.currencymanager.currencies.Currency;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class TextFileReader implements FileReader_fromPath {

//    String path;
//    BufferedReader reader;


    public List<Currency> readFile(String path) throws Exception {
        List<Currency> currencies= new ArrayList<Currency>();
        //Read in supported currencies from text file
        BufferedReader reader = new BufferedReader(new java.io.FileReader(path));
        parsing(currencies, reader);

        return currencies;
    }

     void parsing(List<Currency> currencies, BufferedReader reader) throws Exception {
        //skip the first line to avoid header
        String firstLine = reader.readLine();
        if (!firstLine.equals("code,name,major")) {
            throw new Exception("Parsing error when reading currencies file.");
        }

        while (reader.ready()) {
            String  nextLine = reader.readLine();

            //Check if line has 2 commas
            int numCommas = 0;
            char[] chars = nextLine.toCharArray();
            for (char c : chars) {
                if (c == ',') numCommas++;
            }

            if (numCommas != 2) {
                throw new Exception("Parsing error: expected two commas in line " + nextLine);
            }

            Currency currency = Currency.fromString(nextLine);

            if (currency.code.length() == 3) {
                if (!currencyExists(currency.code, currencies)) {
                    currencies.add(currency);
                }
            } else {
                System.err.println("Invalid currency code detected: " + currency.code);
            }
        }
    }

    private boolean currencyExists(String code, List<Currency> currentCurr) {

       Currency presentCurr = null;


            for (Currency currency : currentCurr) {
                if (currency.code.equalsIgnoreCase(code)) {
                    presentCurr= currency;
                }
            }

            if(presentCurr == null){
                return false;
            }
        return true;
    }
}
