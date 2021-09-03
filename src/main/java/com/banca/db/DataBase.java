package com.banca.db;
import java.util.*;
import java.io.FileReader;
import java.io.FileWriter;

import com.banca.models.Cuenta;
import com.banca.utils.Constante;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.PrintWriter;

public class DataBase {

    private static final String PATH = new Constante().PATH_DB;

    public DataBase() {
    }

    public Cuenta searchByNumeroCuenta(String numeroCuenta) throws IOException, NumberFormatException, CsvException{
        String fileName = PATH;
        CSVReader reader = new CSVReader(new FileReader(fileName));
        List<String[]> allElements = (List<String[]>) reader.readAll();
        for(String[] element : allElements){
            if(element[1].equals(numeroCuenta)){
                return new Cuenta(element[0], element[1], Double.valueOf(element[2]));
            }
        }
        clearDB(fileName);
        FileWriter mFileWriter = new FileWriter(fileName, false);
        CSVWriter writer = new CSVWriter(mFileWriter);
        writer.writeAll(allElements);
        writer.close();
        return new Cuenta();   
    }

    private void clearDB(String path) throws IOException {
        FileWriter fw = new FileWriter(path, false); 
        PrintWriter pw = new PrintWriter(fw, false);
        pw.flush();
        pw.close();
        fw.close();
    }
    
}
