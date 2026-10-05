package hu.pte.rushhour;

import db.Adatbazis;
import model.Tabla;
import model.Jarmu;

import java.util.Scanner;
import java.util.Random;

public class Jatek {
    private Tabla tabla;
    private Adatbazis dbHelper;

    public Jatek(Tabla tabla) {
        this.tabla = tabla;
        this.dbHelper = new Adatbazis();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Játékos neve: ");
        String playerName = scanner.nextLine();
        dbHelper.initDatabase();

        boolean running = true;
        while (running) {
            printBoard();
            System.out.println("Te jössz! (Pl.: A 1 vagy X -1) [képzeletbeli kilépés: exit]");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) break;

            try {
                String[] parts = input.split(" ");
                if (tabla.applyMove(parts[0].charAt(0), Integer.parseInt(parts[1]))) {

                    // Játékos győzelmének ellenőrzése
                    Jarmu heroA = tabla.getVehicles().stream().filter(v -> v.id() == 'A').findFirst().orElse(null);
                    if (heroA != null && heroA.startPos().col() + heroA.length() >= 6) {
                        printBoard();
                        System.out.println("Gratulálok " + playerName + ", nyertél!");
                        dbHelper.incrementWin(playerName);
                        running = false;
                        continue; // Kilép a ciklusból
                    }

                    // AI lépése
                    System.out.println("A gép gondolkodik...");
                    makeAIMove();

                    // Gép győzelmének ellenőrzése
                    Jarmu heroB = tabla.getVehicles().stream().filter(v -> v.id() == 'B').findFirst().orElse(null);
                    if (heroB != null && heroB.startPos().col() <= 0) {
                        printBoard();
                        System.out.println("A Gép nyert! Sajnos most vesztettél.");
                        running = false;
                    }

                } else {
                    System.out.println("Érvénytelen lépés!");
                }
            } catch (Exception e) {
                System.out.println("Hibás formátum.");
            }
        }
        dbHelper.printHighScores();
    }

    private void makeAIMove() {
        Random rand = new Random();
        boolean moved = false;
        while (!moved) {
            Jarmu v = tabla.getVehicles().get(rand.nextInt(tabla.getVehicles().size()));

            // A gép nem irányíthatja a játékos járművét
            if (v.id() == 'A') continue;

            int offset = rand.nextBoolean() ? 1 : -1;
            if (tabla.applyMove(v.id(), offset)) {
                System.out.println("-> A gép lépett ezzel: " + v.id() + " " + offset);
                moved = true;
            }
        }
    }

    private void printBoard() {
        System.out.println("-----------------");
        for (char[] row : tabla.getGrid()) {
            for (char cell : row) System.out.print(cell + " ");
            System.out.println();
        }
    }
}