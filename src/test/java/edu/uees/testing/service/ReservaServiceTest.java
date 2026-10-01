package edu.uees.testing.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReservaServiceTest {

    // puedeCancelar() y calcularTotal() no usan colaboradores, por eso null es suficiente aqui.
    private final ReservaService servicio = new ReservaService(null, null, null);

    @Test
    void entornoJUnitFunciona() {
        assertTrue(true);
    }

    // CP-01
    @Test
    void cincoHorasPermitenCancelar() {
        // Arrange
        int horas = 5;

        // Act
        boolean resultado = servicio.puedeCancelar(horas);

        // Assert
        assertTrue(resultado);
    }

    // CP-02
    @Test
    void dosHorasEsElLimitePermitido() {
        // Arrange
        int horas = 2;

        // Act
        boolean resultado = servicio.puedeCancelar(horas);

        // Assert
        assertTrue(resultado);
    }

    // CP-03
    @Test
    void unaHoraNoPermiteCancelar() {
        // Arrange
        int horas = 1;

        // Act
        boolean resultado = servicio.puedeCancelar(horas);

        // Assert
        assertFalse(resultado);
    }

    // CP-04
    @Test
    void ceroHorasNoPermitenCancelar() {
        // Arrange
        int horas = 0;

        // Act
        boolean resultado = servicio.puedeCancelar(horas);

        // Assert
        assertFalse(resultado);
    }

    // CP-05
    @Test
    void normalNoRecibeDescuento() {
        // Arrange
        double totalBase = 100;

        // Act
        double total = servicio.calcularTotal("NORMAL", totalBase);

        // Assert
        assertEquals(100.0, total, 0.001);
    }

    // CP-06
    @Test
    void vipRecibeQuincePorCiento() {
        // Arrange
        double totalBase = 100;

        // Act
        double total = servicio.calcularTotal("VIP", totalBase);

        // Assert
        assertEquals(85.0, total, 0.001);
    }

    // CP-07
    @Test
    void estudianteRecibeDiezPorCiento() {
        // Arrange
        double totalBase = 100;

        // Act
        double total = servicio.calcularTotal("ESTUDIANTE", totalBase);

        // Assert
        assertEquals(90.0, total, 0.001);
    }

    // CP-08
    @Test
    void totalCeroVipDevuelveCero() {
        // Arrange
        double totalBase = 0;

        // Act
        double total = servicio.calcularTotal("VIP", totalBase);

        // Assert
        assertEquals(0.0, total, 0.001);
    }

    // CP-09
    @Test
    void totalNegativoEsInvalido() {
        // Arrange
        double totalBase = -1;

        // Act
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> servicio.calcularTotal("NORMAL", totalBase));

        // Assert
        assertEquals("Total base inválido", ex.getMessage());
    }
}
