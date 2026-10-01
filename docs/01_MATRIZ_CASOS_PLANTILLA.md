# Matriz de casos

Reglas de `ReservaService`: cancelar con 2 o más horas, descuento VIP 15 % y ESTUDIANTE 10 %,
total base no negativo, y confirmar solo si hay disponibilidad (guardar y luego notificar).

| ID | Regla | Escenario | Entrada | Esperado | Tipo | Riesgo | Prueba |
|---|---|---|---|---|---|---|---|
| CP-01 | Cancelar con ≥ 2 h | Anticipación habitual | 5 | true | Normal | La regla general deja de permitir cancelar | `cincoHorasPermitenCancelar` |
| CP-02 | Cancelar con ≥ 2 h | Límite permitido | 2 | true | Límite | Usar `>` en lugar de `>=` | `dosHorasEsElLimitePermitido` |
| CP-03 | Cancelar con ≥ 2 h | Debajo del límite | 1 | false | Límite | Bajar el límite a 1 hora | `unaHoraNoPermiteCancelar` |
| CP-04 | Cancelar con ≥ 2 h | Sin anticipación | 0 | false | Extremo | Cancelar a último momento | `ceroHorasNoPermitenCancelar` |
| CP-05 | Cancelar con ≥ 2 h | Justo encima del límite | 3 | true | Límite | Subir el límite a 3 horas | `tresHorasPermitenCancelar` |
| CP-06 | Cancelar con ≥ 2 h | Horas negativas | -1 | false | Inválido | Aceptar datos imposibles | `horasNegativasNoPermitenCancelar` |
| CP-07 | NORMAL sin descuento | Cliente normal | NORMAL, 100 | 100 | Normal | Descontar a quien no corresponde | `normalNoRecibeDescuento` |
| CP-08 | VIP 15 % | Cliente VIP | VIP, 100 | 85 | Alternativo | Porcentaje VIP mal escrito | `vipRecibeQuincePorCiento` |
| CP-09 | ESTUDIANTE 10 % | Cliente estudiante | ESTUDIANTE, 100 | 90 | Alternativo | Cruzar los descuentos | `estudianteRecibeDiezPorCiento` |
| CP-10 | Total no negativo | Total cero | VIP, 0 | 0 | Límite | Rechazar el cero, que es válido | `totalCeroVipDevuelveCero` |
| CP-11 | Total no negativo | Total negativo | NORMAL, -1 | IllegalArgumentException "Total base inválido" | Excepción | Cobrar un total negativo | `totalNegativoEsInvalido` |
| CP-12 | VIP sin importar mayúsculas | Tipo en minúsculas | vip, 100 | 85 | Alternativo | Cambiar `equalsIgnoreCase` por `equals` | `vipEnMinusculasRecibeQuincePorCiento` |
| CP-13 | Sin descuento por defecto | Tipo desconocido | CORPORATIVO, 100 | 100 | Alternativo | Dar descuento a un tipo no previsto | `tipoDesconocidoNoRecibeDescuento` |
| CP-14 | Sin descuento por defecto | Tipo nulo | null, 100 | 100 | Inválido | `NullPointerException` en el cálculo | `tipoNuloSeCobraSinDescuento` |
| CP-15 | ESTUDIANTE 10 % | Total con decimales | ESTUDIANTE, 45.50 | 40.95 | Normal | Redondeo o truncado del total | `estudianteConTotalDecimalRecibeDiezPorCiento` |
| CP-16 | Confirmar con disponibilidad | Disponible | R-001 + true | CONFIRMADA, guarda y notifica | Normal | Confirmar sin guardar o sin avisar | `reservaDisponibleSeConfirmaGuardaYNotifica` |
| CP-17 | Confirmar con disponibilidad | No disponible | R-002 + false | IllegalStateException, sigue PENDIENTE, no guarda ni notifica | Alternativo | Guardar una reserva sin horario | `reservaNoDisponibleNoSeGuardaNiNotifica` |
| CP-18 | Reserva obligatoria | Reserva nula | null | IllegalArgumentException, no consulta dependencias | Excepción | Llamar a servicios externos con datos nulos | `reservaNulaNoConsultaDependencias` |
| CP-19 | Guardar y luego notificar | Orden de interacciones | R-003 + true | consulta, guarda y notifica en ese orden | Normal | Avisar antes de persistir | `reservaSeGuardaAntesDeNotificar` |
| CP-20 | Guardar y luego notificar | Falla el repositorio | R-004 + error al guardar | excepción y no notifica | Excepción | Avisar de una reserva que no se guardó | `errorAlGuardarNoEnviaNotificacion` |
| CP-21 | Id obligatorio | Id en blanco | " " | IllegalArgumentException "Id obligatorio" | Inválido | Reservas sin identificador | `idVacioEsInvalido` |
| CP-22 | Tipo por defecto | Tipo nulo al crear | R-010, null | tipo NORMAL y estado PENDIENTE | Alternativo | Reserva sin tipo ni estado inicial | `tipoNuloSeAsumeNormalYQuedaPendiente` |

CP-01 a CP-04 y CP-07 a CP-11 vienen del Laboratorio 1, CP-16 a CP-18 y CP-21 a CP-22 del
Laboratorio 2. CP-05, CP-06 y CP-12 a CP-15, CP-19 y CP-20 son de Ae6.
