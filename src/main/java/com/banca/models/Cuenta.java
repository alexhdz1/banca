package com.banca.models;

import com.banca.utils.Constante;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.PrintWriter;
import java.util.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Objects;
import java.io.IOException;

public class Cuenta {
    
    private static final String PATH = new Constante().PATH_DB;

    private String nombrePropietario;
    private double saldoDisponible;
    private String numeroCuenta;
    
    
    public Cuenta() {
    }

    public Cuenta(String nombrePropietario, double saldoDisponible) throws CsvValidationException, IOException {
        this.nombrePropietario = nombrePropietario;
        this.saldoDisponible = saldoDisponible;
        this.numeroCuenta = String.valueOf(obtenerUltimoNumeroCuenta());
    }

    public Cuenta(String nombrePropietario, String numeroCuenta, double saldoDisponible){
        this.nombrePropietario = nombrePropietario;
        this.saldoDisponible = saldoDisponible;
        this.numeroCuenta = numeroCuenta;
    }

    /**
     * Obtiene el nombre del propietario
     * @return
     */
    public String getNombrePropietario() {
        return this.nombrePropietario;
    }

    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }

    public double getSaldoDisponible() {
        return this.saldoDisponible;
    }

    public void setSaldoDisponible(double saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public Cuenta nombrePropietario(String nombrePropietario) {
        setNombrePropietario(nombrePropietario);
        return this;
    }

    public Cuenta saldoDisponible(double saldoDisponible) {
        setSaldoDisponible(saldoDisponible);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Cuenta)) {
            return false;
        }
        Cuenta cuenta = (Cuenta) o;
        return Objects.equals(nombrePropietario, cuenta.nombrePropietario)
                && saldoDisponible == cuenta.saldoDisponible;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombrePropietario, saldoDisponible);
    }

    @Override
    public String toString() {
        return "{"
            +
            "nombrePropietario='" + getNombrePropietario()
            +
            "'"
            +
            ",saldoDisponible='"
            +
            getSaldoDisponible()
            +
            "'"
            +
            "}";
    }
    
    public void crearCuenta() throws IOException , CsvValidationException{
        String[] entries = { this.nombrePropietario,
                            String.valueOf(obtenerUltimoNumeroCuenta()),
                            String.valueOf(this.saldoDisponible)};
        String fileName = PATH;
        FileWriter mFileWriter = new FileWriter(fileName, true);
        CSVWriter writer = new CSVWriter(mFileWriter);
        writer.writeNext(entries);
        writer.close();
    }

    private Integer obtenerUltimoNumeroCuenta() throws IOException, CsvValidationException {
        String fileName = PATH;
        CSVReader reader = new CSVReader(new FileReader(fileName));
        String [] nextLine;
        String lastRegister = "10100";
        
        if (reader.readNext() == null){
            return Integer.valueOf(lastRegister);
        }
        while ((nextLine = reader.readNext() ) != null) {
            lastRegister = nextLine[1];
        }
        return  Integer.valueOf(lastRegister) + 1;
    }

    public void transferirDinero(String numeroCuenta, double monto) throws IOException, CsvException{
        String fileName = PATH;
        CSVReader reader = new CSVReader(new FileReader(fileName));
        List<String[]> allElements = (List<String[]>) reader.readAll();
        for(String[] element : allElements){
            if(element[1].equals(numeroCuenta)){
                element[2] = String.valueOf(Double.valueOf(element[2]) + monto);
            }
        }
        clearCsv(fileName);
        FileWriter mFileWriter = new FileWriter(fileName, false);
        CSVWriter writer = new CSVWriter(mFileWriter);
        writer.writeAll(allElements);
        writer.close();
    }

    public void ingresarDinero(String numeroCuenta, double monto) throws IOException, CsvException{
        String fileName = PATH;
        CSVReader reader = new CSVReader(new FileReader(fileName));
        List<String[]> allElements = (List<String[]>) reader.readAll();
        for(String[] element : allElements){
            if(element[1].equals(numeroCuenta)){
                element[2] = String.valueOf(Double.valueOf(element[2]) + monto);
            }
        }
        clearCsv(fileName);
        FileWriter mFileWriter = new FileWriter(fileName, false);
        CSVWriter writer = new CSVWriter(mFileWriter);
        writer.writeAll(allElements);
        writer.close();
    }

    public void retirarDinero(String numeroCuenta, double monto) throws IOException, CsvException{
        String fileName = PATH;
        CSVReader reader = new CSVReader(new FileReader(fileName));
        List<String[]> allElements = (List<String[]>) reader.readAll();
        for(String[] element : allElements){
            if(element[1].equals(numeroCuenta)){
                element[2] = String.valueOf(Double.valueOf(element[2]) - monto);
            }
        }
        clearCsv(fileName);
        FileWriter mFileWriter = new FileWriter(fileName, false);
        CSVWriter writer = new CSVWriter(mFileWriter);
        writer.writeAll(allElements);
        writer.close();
    }

    private void clearCsv(String path) throws IOException {
        FileWriter fw = new FileWriter(path, false); 
        PrintWriter pw = new PrintWriter(fw, false);
        pw.flush();
        pw.close();
        fw.close();
    }

}
