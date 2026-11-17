# PL2.1 · Ordenamiento con Comparator y Comparable

- **Autor:** Edison Brito
- **Asignatura:** Estructura de Datos
- **JDK utilizado:** Java SE 25.0.4.1

## Objetivo
Implementar el orden natural de la clase `Estudiante` mediante `Comparable<Estudiante>` y construir distintos criterios de ordenamiento externos con `Comparator<Estudiante>` aplicando expresiones lambda, referencias a métodos, desempates y verificando tanto la estabilidad como la conservación de la lista original.

## Estructura del Proyecto
- `src/models/Estudiante.java`: Modelo inmutable con orden natural por código ascendente.
- `src/data/EstudiantesData.java`: Carga de los 20 registros iniciales en el orden exacto de la guía.
- `src/comparators/EstudianteComparators.java`: Implementación de los comparadores para las consultas C2 a C7.
- `src/App.java`: Ejecución de las siete consultas, validaciones de orden y pruebas de aceptación.
- `evidencias/salidas.txt`: Registro real de la salida obtenida en consola.

## Compilación y Ejecución desde Terminal
```bash
mkdir -p bin
javac -d bin src/models/*.java src/data/*.java src/comparators/*.java src/App.java
java -cp bin App

Tabla de Consultas (C1 - C7)IDCriterio de OrdenamientoMétodo / ImplementaciónC1Código ascendenteEstudiante.compareTo() mediante list.sort(null)C2Nombre ascendente; código ascendenteEstudianteComparators.porNombreYCodigoLambda() y porNombreYCodigoReferencia()C3Nota ascendente, sin desempateEstudianteComparators.porNotaAsc() con Comparator.comparingDoubleC4Nota descendente; nombre ascendenteEstudianteComparators.porNotaDescYNombreAsc() con .reversed() y .thenComparing()C5Edad ascendente; nombre descendenteEstudianteComparators.porEdadAscYNombreDesc() con comparingInt y sentido independienteC6Carrera ascendente; nota descendente; nombre ascendente; código ascendenteEstudianteComparators.porCarreraNotaNombreCodigo()C7Nota ajustada descendente; nombre ascendente; código ascendenteEstudianteComparators.porNotaAjustadaDescNombreCodigo()

Tabla de PruebasEscenarioEsperadoObtenidoEstadoC1 (20 registros)E001, E002, ..., E020Secuencia de E001 a E020AprobadoC2 (20 registros)Inicia E005, E010, E014, E017Ambas versiones idénticasAprobadoC3 (nota 8.5)E014, E007, E005, E008, E010[E014, E007, E005, E008, E010]AprobadoC4 (20 registros)Inicia E017, E011, E001E017, E011, E001AprobadoC5 (20 registros)Inicia E001, E016, E004E001, E016, E004AprobadoC6 (20 registros)E001, E018, E005, E010, E014E001, E018, E005, E010, E014AprobadoC7 (Nota ajustada)E001 (10.0), E018 (9.7)Orig 9.5 -> 10.0; 9.2 -> 9.7AprobadoLista vacía y 1 elemSin erroressize=0 y E014 sin fallosAprobadoC4 empate completoE014, E005, E010[E014, E005, E010]AprobadoOriginal intacto20 códigos inicialestrueAprobadoCaso propio: C5 empateConserva E003, E013[E003, E013] (Luis, 19)Aprobado

Análisis Técnico
Signo del resultado de compareTo(): En Estudiante, this.codigo.compareTo(otro.codigo) devuelve un número negativo si el código actual va antes alfabéticamente que el otro, 0 si ambos códigos son idénticos y un número positivo si va después.

Inversión de comparadores compuestos (C4 y C5): Si se coloca .reversed() al final de toda una cadena compuesta, se invierte tanto el criterio principal como los desempates. En C4 se invirtió primero la nota antes de encadenar el nombre para mantener el nombre ascendente; en C5, como la edad debía ir ascendente y solo el nombre descendente, se aplicó .reversed() únicamente dentro del comparador secundario del nombre.

Empates con códigos distintos: Al comparar únicamente por nota (como en C3), dos estudiantes con códigos diferentes devuelven 0 cuando tienen la misma calificación (por ejemplo, E014 y E007 ambos con 8.5), ya que el comparador solo evalúa la clave indicada y no el resto de atributos del objeto.

Coincidencias entre consultas: Tanto C1 como C6 y C7 arrancan con E001 (Sofia), pero por motivos distintos: en C1 por tener el primer código, en C6 por encabezar Computación con nota 9.5 y en C7 porque al sumar el bono de 0.5 alcanza 10.0. Por otro lado, E005, E010 y E014 aparecen en ese mismo orden en C2 y C6 porque comparten nombre (Ana), nota (8.5) y carrera (Computacion), resolviéndose el empate por su código ascendente.

Conclusiones
Usar Comparable dentro de Estudiante resultó práctico para definir el orden base por código en C1, pero para el resto de consultas (C2 a C7) Comparator fue mucho más flexible porque permitió ordenar por nota, edad o carrera creando listas nuevas sin tener que modificar la clase original ni alterar los datos cargados al inicio.

En C3 se comprobó la estabilidad del método sort de Java: los cinco estudiantes con nota 8.5 salieron exactamente en el orden en que entraron (E014, E007, E005, E008, E010). Lo mismo ocurrió en la prueba de empate total de C4 con las tres estudiantes llamadas Ana (E014, E005, E010), demostrando que cuando no hay un criterio adicional de desempate se respeta la posición previa.

Encadenar criterios requiere cuidar exactamente dónde se coloca .reversed(). Al probar C5 comprobé que invertir solo el segundo criterio (nombre) mantiene la edad de menor a mayor mientras ordena los nombres empatados de la Z a la A, y en C7 quedó claro que calcular la nota ajustada dentro del comparador permite reordenar a los estudiantes de Computación (subiendo a E001 a 10.0) sin modificar su nota real de 9.5.