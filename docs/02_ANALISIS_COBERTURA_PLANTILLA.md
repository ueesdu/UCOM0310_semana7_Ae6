# Análisis de cobertura

Reporte: `mvn clean test` genera `target/site/jacoco/index.html` (JaCoCo 0.8.12).

## Resultado observado

| Momento | Pruebas | `ReservaService` (líneas / ramas) | `Reserva` (líneas / ramas) | Proyecto (instrucciones / ramas) |
|---|---|---|---|---|
| Fin del Laboratorio 2 | 15 | 21/21 · 12/12 | 11/14 · 5/6 | 94,9 % · 17/18 |
| Final de Ae6 | 23 | 21/21 · 12/12 | 11/14 · 5/6 | 94,9 % · 17/18 |

- Cobertura de líneas: 36 de 39 (92 %).
- Cobertura de ramas: 17 de 18 (94 %).
- Clase o método analizado: `ReservaService` (los tres métodos públicos) y `Reserva`.

El porcentaje no cambió entre el laboratorio y Ae6, aunque se agregaron 8 pruebas. Esas pruebas
no ejecutan código nuevo: verifican comportamientos que ya se ejecutaban sin ser comprobados.

## Huecos relevantes

1. **Tipo en minúsculas.** `calcularTotal` usa `equalsIgnoreCase`. Con 17 pruebas y 100 % de
   líneas y ramas en `ReservaService`, cambié temporalmente `"VIP".equalsIgnoreCase(tipo)` por
   `"VIP".equals(tipo)` y la suite siguió en verde. Ninguna prueba enviaba `"vip"`.
2. **Orden y fallos en `confirmar`.** El servicio guarda y después notifica. Ninguna prueba
   comprobaba ese orden ni qué pasa si `guardar` falla.
3. **`Reserva`.** Siguen sin ejecutarse la rama `id == null` (solo se probó el id en blanco),
   `getId()` y `cancelar()`. Ningún servicio usa `cancelar()` todavía.

## Decisiones

- ¿Qué prueba nueva se añadió? Por el hueco 1, `vipEnMinusculasRecibeQuincePorCiento` (con el
  cambio a `equals` falla: esperaba 85.0 y obtuvo 100.0). Por el hueco 2,
  `reservaSeGuardaAntesDeNotificar` (con `InOrder`) y `errorAlGuardarNoEnviaNotificacion`.
- ¿Qué riesgo protege? Cobrar sin descuento a un cliente VIP por cómo se escribió el tipo, y
  avisar al estudiante de una reserva que no se guardó.
- ¿Por qué no basta con el porcentaje? El hueco 1 existía con 100 % de líneas y ramas. La
  cobertura dice qué código se ejecutó, no si una aserción revisó el resultado correcto.
- El hueco 3 no se cubrió a propósito: `cancelar()` no tiene regla de negocio que probar y una
  prueba solo para subir el porcentaje no protege nada.
