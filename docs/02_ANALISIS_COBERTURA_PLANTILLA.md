# Análisis de cobertura

Reporte generado con `mvn clean test` en `target/site/jacoco/index.html`.

## Resultado observado

| Clase | Antes (13 pruebas) | Después (15 pruebas) |
|---|---|---|
| `ReservaService` | líneas 21/21, ramas 12/12 | líneas 21/21, ramas 12/12 |
| `Reserva` | líneas 9/14, ramas 3/6 | líneas 11/14, ramas 5/6 |
| Proyecto | instrucciones 87,7 %, ramas 15/18 | instrucciones 94,9 %, ramas 17/18 |

- Cobertura de líneas: `ReservaService` 100 %, proyecto 92 % al final.
- Cobertura de ramas: `ReservaService` 100 %, proyecto 17 de 18.
- Clase o método analizado: `ReservaService` (los tres métodos) y `Reserva`.

## Huecos relevantes

1. `Reserva` tenía sin ejecutar la validación del id y el caso de tipo nulo. Son reglas de
   negocio: una reserva sin id no debe existir y un tipo nulo se trata como NORMAL. Ninguna
   prueba de `ReservaService` las tocaba porque siempre se crean reservas válidas.
2. `ReservaService` está al 100 %, pero hay comportamientos que pasan por líneas ya cubiertas y
   no están verificados: un tipo escrito en minúsculas (`"vip"`), un tipo desconocido y un tipo
   nulo en `calcularTotal`. Si alguien cambia `equalsIgnoreCase` por `equals`, ninguna prueba falla.
3. `Reserva.cancelar()` y `getId()` siguen sin ejecutarse. Hoy ningún servicio usa `cancelar()`,
   así que se deja anotado y no se prueba solo para subir el porcentaje.

## Decisiones

- ¿Qué prueba nueva se añadió? `ReservaTest.idVacioEsInvalido()` y
  `ReservaTest.tipoNuloSeAsumeNormalYQuedaPendiente()`.
- ¿Qué riesgo protege? Que se pueda crear una reserva sin id o que un tipo nulo rompa el
  cálculo del total.
- ¿Por qué no basta con el porcentaje? `ReservaService` ya tenía 100 % de líneas y ramas, y aun
  así el hueco 2 existe. La cobertura dice qué se ejecutó, no qué se comprobó con una aserción.
