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

/**
 * Una clase para representar la cuenta bancaria de una persona
 * con sus atributos Y métodos principales (descritas en el PDF)
 * @version 1.0 3-Sep-2021
 * @author Gibran Aguilar Zuñiga
*/

public class Cuenta {
    
    private static final String PATH = Constante.PATH_DB;

    private String nombrePropietario;
    private double saldoDisponible;
    private String numeroCuenta;
    
    public Cuenta() {
    }

    /** 
      *Método para inicializar cueta con la última cueta creada
      *@param nombrePropietario nombre de la persona para crear cuenta
      *@param saldoDisponible saldo de la cuenta de la persona
      *@throws IOException 
      *@throws CsvValidationException
    */
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

    /** 
      * Función set para cambiar el nombre de la cuenta.
    */
    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }

    /** 
      * Función get para retornar el saldo disponoble de la cuenta.
      * @return el saldo disponoble de la cuenta.
    */
    public double getSaldoDisponible() {
        return this.saldoDisponible;
    }

    /** 
      * Función set para cambiar el saldo disponoble de la cuenta.
      
    */
    public void setSaldoDisponible(double saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    /** 
      * Función  para retornar cambiar el nombre de la cuenta.
      * @param nombrePropietario nombre de la persona de la cuenta
      * @return regresas el constructor de la cuenta
    */
    public Cuenta nombrePropietario(String nombrePropietario) {
        setNombrePropietario(nombrePropietario);
        return this;
    }

    /** 
      * Función  para cambiar el saldo disponoble de la cuenta.
      * @param saldoDisponible nombre de la persona de la cuenta
      * @return regresas el constructor de la cuenta
    */
    public Cuenta saldoDisponible(double saldoDisponible) {
        setSaldoDisponible(saldoDisponible);
        return this;
    }

    /** 
      * Función  para comparar objetos
      * @param o objeto a comparar
      * @return true or false dependiendo el caso.
    */
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

    /** 
      * Función  para transformar a un hash rl nombre y saldo disponible.
      * @return el ojeto después de aplicarle la función hash
    */
    @Override
    public int hashCode() {
        return Objects.hash(nombrePropietario, saldoDisponible);
    }

    /** 
      * Función  para convertir en json la información de las cuentas
      * @return regresas un json con la información de la cuenta
    */
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

    /** 
      * Función  para imprimir la información de las cuentas.
    */
    public void printCuenta(){
        System.out.println("-------------------------");
        System.out.println("cliente: "+this.nombrePropietario);
        System.out.println("num. cuenta: "+this.saldoDisponible);
        System.out.println("saldo disponible: "+this.numeroCuenta);
        System.out.println("-------------------------");
    }
    
    /** 
      * Función  para crear una cuenta dado los atributos de la clase.
      *@throws IOException 
      *@throws CsvValidationException 
    */
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

    /** 
      * Función  para obtener la última cuenta.
      * @return regresas el valor de la última cuenta, 10100 es el valor por default
      *@throws IOException 
      *@throws CsvValidationException
    */
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

    /** 
      * Función  para transferir dinero de una cuenta a otra.
      * @param numeroCuenta número de cuenta
      * @param monto la cantidad a transferir
      *@throws IOException 
      *@throws CsvValidationException
    */
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

    /** 
      * Función  para ingresar dinero a una cuenta.
      * @param numeroCuenta número de cuenta
      * @param monto la cantidad a ingresar
      *@throws IOException 
     * @throws CsvException
    */
    public void ingresarDinero(double monto) throws IOException, CsvException{
        String fileName = PATH;
        CSVReader reader = new CSVReader(new FileReader(fileName));
        List<String[]> allElements = (List<String[]>) reader.readAll();
        for(String[] element : allElements){
            if(element[1].equals(this.numeroCuenta)){
                element[2] = String.valueOf(Double.valueOf(element[2]) + monto);
            }
        }
        clearCsv(fileName);
        FileWriter mFileWriter = new FileWriter(fileName, false);
        CSVWriter writer = new CSVWriter(mFileWriter);
        writer.writeAll(allElements);
        writer.close();
    }

    /** 
      * Función  para retirar dinero de una cuenta.
      * @param numeroCuenta número de cuenta
      * @param monto la cantidad a ingresar
      *@throws IOException 
      *@throws CsvValidationException
    */
    public void retirarDinero(double monto) throws IOException, CsvException{
        String fileName = PATH;
        CSVReader reader = new CSVReader(new FileReader(fileName));
        List<String[]> allElements = (List<String[]>) reader.readAll();
        for(String[] element : allElements){
            if(element[1].equals(this.numeroCuenta)){
                element[2] = String.valueOf(Double.valueOf(element[2]) - monto);
            }
        }
        clearCsv(fileName);
        FileWriter mFileWriter = new FileWriter(fileName, false);
        CSVWriter writer = new CSVWriter(mFileWriter);
        writer.writeAll(allElements);
        writer.close();
    }

    /** 
      * Función  para borrar la información de un csv.
      * @param path directorio donde se encuentra el archivo
    */
    private void clearCsv(String path) throws IOException {
        FileWriter fw = new FileWriter(path, false); 
        PrintWriter pw = new PrintWriter(fw, false);
        pw.flush();
        pw.close();
        fw.close();
    }

}
