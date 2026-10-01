package edu.uees.testing.service;

import edu.uees.testing.availability.DisponibilidadClient;
import edu.uees.testing.domain.EstadoReserva;
import edu.uees.testing.domain.Reserva;
import edu.uees.testing.notification.Notificador;
import edu.uees.testing.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConfirmarReservaTest {

    private DisponibilidadClient disponibilidad;
    private ReservaRepository repository;
    private Notificador notificador;
    private ReservaService servicio;

    @BeforeEach
    void prepararServicio() {
        disponibilidad = mock(DisponibilidadClient.class);
        repository = mock(ReservaRepository.class);
        notificador = mock(Notificador.class);
        servicio = new ReservaService(disponibilidad, repository, notificador);
    }

    @Test
    void reservaDisponibleSeConfirmaGuardaYNotifica() {
        // Arrange: el Stub responde que hay disponibilidad
        when(disponibilidad.estaDisponible(any())).thenReturn(true);
        Reserva reserva = new Reserva("R-001", "NORMAL");

        // Act
        servicio.confirmar(reserva);

        // Assert: estado y las dos interacciones que deben ocurrir
        assertEquals(EstadoReserva.CONFIRMADA, reserva.getEstado());
        verify(repository).guardar(reserva);
        verify(notificador).enviarConfirmacion(reserva);
    }

    @Test
    void reservaNoDisponibleNoSeGuardaNiNotifica() {
        // Arrange: el Stub responde que no hay disponibilidad
        when(disponibilidad.estaDisponible(any())).thenReturn(false);
        Reserva reserva = new Reserva("R-002", "NORMAL");

        // Act
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> servicio.confirmar(reserva));

        // Assert
        assertEquals("Horario no disponible", ex.getMessage());
        assertEquals(EstadoReserva.PENDIENTE, reserva.getEstado());
        verify(repository, never()).guardar(any());
        verify(notificador, never()).enviarConfirmacion(any());
    }

    @Test
    void reservaNulaNoConsultaDependencias() {
        // Arrange: no hace falta configurar respuestas

        // Act
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> servicio.confirmar(null));

        // Assert
        assertEquals("Reserva obligatoria", ex.getMessage());
        verify(disponibilidad, never()).estaDisponible(any());
        verify(repository, never()).guardar(any());
        verify(notificador, never()).enviarConfirmacion(any());
    }
}
