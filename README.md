# Semana 7 · Suite de pruebas del servicio de reservas (Ae6)

Pruebas unitarias de `ReservaService`: cancelación, descuentos y confirmación de reservas, con
JUnit 5, Mockito y cobertura con JaCoCo.

## Requisitos
- Java 21
- Maven 3.9+
- Git

## Ejecutar las pruebas
```bash
mvn clean test
```
Resultado esperado: 23 pruebas, 0 fallos, `BUILD SUCCESS`.

## Cobertura
`mvn clean test` también genera el reporte en `target/site/jacoco/index.html`.

## Estructura de las pruebas
| Clase | Qué prueba |
|---|---|
| `service/ReservaServiceTest` | Cancelación (límites de 2 horas) y cálculo de descuentos, sin dobles de prueba |
| `service/ConfirmarReservaTest` | `confirmar()` con Stub de disponibilidad y Mocks de repositorio y notificador |
| `domain/ReservaTest` | Validaciones al crear una reserva |

## Documentación
- `docs/01_MATRIZ_CASOS_PLANTILLA.md`: matriz de 22 casos.
- `docs/02_ANALISIS_COBERTURA_PLANTILLA.md`: análisis de JaCoCo.
- `docs/03_PULL_REQUEST_PLANTILLA.md`: descripción del Pull Request.
