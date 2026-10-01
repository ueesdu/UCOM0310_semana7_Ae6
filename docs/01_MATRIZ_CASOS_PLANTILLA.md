# Matriz de casos

| ID | Regla | Escenario | Entrada | Esperado | Tipo | Riesgo |
|---|---|---|---|---|---|---|
| CP-01 | Cancelar con 2 o más horas | Anticipación habitual | 5 | true | Normal | Que la regla general deje de permitir cancelar |
| CP-02 | Cancelar con 2 o más horas | Límite permitido | 2 | true | Límite | Usar `>` en lugar de `>=` |
| CP-03 | Cancelar con 2 o más horas | Debajo del límite | 1 | false | Límite | Bajar el límite a 1 hora |
| CP-04 | Cancelar con 2 o más horas | Sin anticipación | 0 | false | Extremo | Permitir cancelar a último momento |
| CP-05 | NORMAL sin descuento | Cliente normal | NORMAL, 100 | 100 | Normal | Aplicar un descuento que no corresponde |
| CP-06 | VIP 15 % | Cliente VIP | VIP, 100 | 85 | Alternativo | Porcentaje VIP mal escrito |
| CP-07 | ESTUDIANTE 10 % | Cliente estudiante | ESTUDIANTE, 100 | 90 | Alternativo | Confundir el descuento de estudiante con el VIP |
| CP-08 | Total no negativo | Total cero | VIP, 0 | 0 | Límite | Rechazar el cero, que sí es válido |
| CP-09 | Total no negativo | Total negativo | NORMAL, -1 | IllegalArgumentException("Total base inválido") | Inválido | Cobrar un total negativo |
