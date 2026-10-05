package hu.pte.rushhour;

import model.Tabla;
import io.Tablatolvaso;

public class Main {
    public static void main(String[] args) {
        Tabla tabla= Tablatolvaso.loadBoard("tabla.txt");
        new Jatek(tabla).start();
    }
}