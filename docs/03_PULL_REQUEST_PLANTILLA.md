# test: completar suite, cobertura y evidencia de Ae6

Base: `main` · Compare: `ae6/suite-pruebas`

## Objetivo
Proteger con pruebas automáticas las reglas críticas del módulo de reservas: límite de
cancelación, descuentos por tipo de cliente y confirmación con disponibilidad.

## Cambios realizados
- Casos de cancelación justo encima del límite (3 horas) y con horas negativas.
- Descuentos con tipo en minúsculas, tipo desconocido, tipo nulo y total con decimales.
- Confirmación: orden consultar, guardar y notificar (`InOrder`) y fallo al guardar sin notificación.
- Análisis de cobertura JaCoCo, matriz de 22 casos y README actualizados.

## Casos de prueba
- Cancelación: 5, 3, 2 (límite), 1, 0 y -1 horas.
- Descuentos: NORMAL, VIP, ESTUDIANTE, `vip`, tipo desconocido, tipo nulo, total cero, total decimal y total negativo (excepción).
- Confirmación: disponible, no disponible, reserva nula, orden de interacciones y error al guardar.
- `Reserva`: id en blanco y tipo nulo.

Uso de dobles: `DisponibilidadClient` es un Stub (`when(...).thenReturn(...)`) porque decide el
camino del método. `ReservaRepository` y `Notificador` son Mocks (`verify`, `never()`, `InOrder`)
porque su efecto es la interacción: guardar y avisar.

## Cómo verificar
```bash
git switch ae6/suite-pruebas
mvn clean test
```
Resultado esperado: 23 pruebas, 0 fallos, `BUILD SUCCESS`. Reporte en `target/site/jacoco/index.html`.

## Cobertura
`ReservaService`: 100 % de líneas (21/21) y ramas (12/12). Proyecto: 36/39 líneas y 17/18 ramas.
Con 100 % de cobertura, cambiar `equalsIgnoreCase` por `equals` no rompía ninguna prueba. Se
agregó `vipEnMinusculasRecibeQuincePorCiento`, que sí detecta ese cambio. Detalle en
`docs/02_ANALISIS_COBERTURA_PLANTILLA.md`.

## Limitaciones
- `Reserva.cancelar()`, `getId()` y la rama `id == null` no se ejecutan. No hay regla de negocio que las use todavía.
- Si `guardar` falla, la reserva queda en estado CONFIRMADA en memoria porque `confirmar()` cambia el estado antes de guardar. Se detectó al escribir `errorAlGuardarNoEnviaNotificacion`. Corregirlo es un cambio de código productivo fuera del alcance de Ae6.
- `calcularTotal` usa `double`. Para dinero sería mejor `BigDecimal`.

## Autorrevisión
- [x] Compila
- [x] Pruebas en verde
- [x] Sin archivos accidentales (`target/` está en `.gitignore`)
- [x] Commits descriptivos
- [x] Documentación actualizada

## Uso de IA
Declarado en el reporte técnico de Ae6.
