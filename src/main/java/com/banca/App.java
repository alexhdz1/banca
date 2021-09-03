package com.banca;

import com.banca.db.DataBase;
import com.banca.models.Cuenta;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
/**
 * Hello world!
 */

public final class App {
    private App() {
    }    
    /**
     * Run the logic of app.
     * @throws CsvException
     */    
    public void run() throws IOException, CsvException {
        DataBase db = new DataBase();
        Cuenta solicitud = db.searchByNumeroCuenta("1000");
        solicitud.retirarDinero(1000);
        System.out.println(solicitud.toString());
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     * @throws IOException
     * @throws CsvException
    */
    public static void main(String[] args) throws IOException, CsvException{
        App app = new App();
        app.run();
    }
}
