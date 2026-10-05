package model;

import model.Jarmu;

import java.util.ArrayList;
import java.util.List;

public class Tabla {
    private final int rows;
    private final int cols;
    private List<Jarmu> vehicles;

    public Tabla(int rows, int cols, List<Jarmu> vehicles) {
        this.rows = rows;
        this.cols = cols;
        this.vehicles = new ArrayList<>(vehicles);
    }

    public char[][] getGrid() {
        char[][] grid = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = '.';
            }
        }
        for (Jarmu v : vehicles) {
            for (int i = 0; i < v.length(); i++) {
                int r = v.isHorizontal() ? v.startPos().row() : v.startPos().row() + i;
                int c = v.isHorizontal() ? v.startPos().col() + i : v.startPos().col();
                if (r >= 0 && r < rows && c >= 0 && c < cols) {
                    grid[r][c] = v.id();
                }
            }
        }
        return grid;
    }

    public boolean applyMove(char vehicleId, int offset) {
        if (offset > 1 || offset < -1) {
            return false; // Ha 1-nél nagyobb a szám (pl. 2, vagy -3), azonnal elutasítja a lépést
        }
        Jarmu target = vehicles.stream().filter(v -> v.id() == vehicleId).findFirst().orElse(null);
        if (target == null || offset == 0) return false;

        Jarmu movedTarget = target.move(offset);

        if (movedTarget.startPos().row() < 0 || movedTarget.startPos().col() < 0) return false;
        if (movedTarget.isHorizontal() && movedTarget.startPos().col() + movedTarget.length() > cols) return false;
        if (!movedTarget.isHorizontal() && movedTarget.startPos().row() + movedTarget.length() > rows) return false;

        vehicles.remove(target);
        vehicles.add(movedTarget);
        return true;
    }

    public List<Jarmu> getVehicles() {
        return vehicles;
    }
}