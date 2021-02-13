package edu.uom.currencymanager.filereader;

import edu.uom.currencymanager.currencies.Currency;

import java.util.List;

public interface FileReader_fromPath {

    public List<Currency> readFile (String path) throws Exception;
}
