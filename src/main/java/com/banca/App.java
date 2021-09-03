package com.banca;

import com.banca.models.Cuenta;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
/**
 * Una clase para representar la cuenta bancaria de una persona
 * con sus atributos Y métodos principales (descritas en el PDF)
 * @version 1.0 3-Sep-2021
 * @author Gibran Aguilar Zuñiga
*/
public final class App {
    private App() {
    }    
    /**
     * Run the logic of app.
     * @throws CsvException
     */    
    public void run() throws IOException, CsvException {
        Cuenta cuenta2 = new Cuenta();
        cuenta2.ingresarDinero("10100", 200);
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
