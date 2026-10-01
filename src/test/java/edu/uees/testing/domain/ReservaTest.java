package edu.uees.testing.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReservaTest {

    @Test
    void idVacioEsInvalido() {
        // Arrange
        String id = " ";

        // Act
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Reserva(id, "NORMAL"));

        // Assert
        assertEquals("Id obligatorio", ex.getMessage());
    }

    @Test
    void tipoNuloSeAsumeNormalYQuedaPendiente() {
        // Arrange
        String tipo = null;

        // Act
        Reserva reserva = new Reserva("R-010", tipo);

        // Assert
        assertEquals("NORMAL", reserva.getTipo());
        assertEquals(EstadoReserva.PENDIENTE, reserva.getEstado());
    }
}
