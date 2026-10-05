package hu.pte.rushhour;

import model.Tabla;
import model.Jarmu;
import model.Kordinata;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class Tablateszt {
    @Test
    void testValidMove() {
        Jarmu car = new Jarmu('A', new Kordinata(2, 1), 2, true);
        Tabla tabla = new Tabla(6, 6, List.of(car));

        assertTrue(tabla.applyMove('A', 1));
        assertEquals(2, tabla.getVehicles().get(0).startPos().col());
    }

    @Test
    void testInvalidMove() {
        Jarmu car = new Jarmu('A', new Kordinata(0, 0), 2, true);
        Tabla tabla = new Tabla(6, 6, List.of(car));

        assertFalse(tabla.applyMove('A', -1)); // Nem mehet le a pályáról
        assertFalse(tabla.applyMove('B', 1));  // Nem létező jármű
    }
}