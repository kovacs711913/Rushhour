package io;

import model.Tabla;
import model.Kordinata;
import model.Jarmu;
import java.util.ArrayList;
import java.util.List;

public class Tablatolvaso {
    public static Tabla loadBoard(String filename) {
        List<Jarmu> vehicles = new ArrayList<>();

        // Játékos (A)
        vehicles.add(new Jarmu('A', new Kordinata(2, 1), 2, true));

        // Gépi ellenfél (B)
        vehicles.add(new Jarmu('B', new Kordinata(4, 4), 2, true));

        // Semleges akadály
        vehicles.add(new Jarmu('X', new Kordinata(0, 0), 3, false));

        return new Tabla(6, 6, vehicles);
    }
}