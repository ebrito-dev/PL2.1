package comparators;

import java.util.Comparator;
import models.Estudiante;

public class EstudianteComparators {

    // C2: Nombre ascendente; codigo ascendente (lambda de dos parametros)
    public static Comparator<Estudiante> porNombreYCodigoLambda() {
        return (a, b) -> {
            int cmpNombre = a.getNombre().compareTo(b.getNombre());
            if (cmpNombre != 0) {
                return cmpNombre;
            }
            return a.getCodigo().compareTo(b.getCodigo());
        };
    }

    // C2 (Alternativa): comparing y thenComparing con referencias a metodos
    public static Comparator<Estudiante> porNombreYCodigoReferencia() {
        return Comparator.comparing(Estudiante::getNombre)
                .thenComparing(Estudiante::getCodigo);
    }

    // C3: Nota ascendente, sin desempate (comparingDouble y referencia a metodo)
    public static Comparator<Estudiante> porNotaAsc() {
        return Comparator.comparingDouble(Estudiante::getNota);
    }

    // C4: Nota descendente; nombre ascendente (reversed y thenComparing)
    public static Comparator<Estudiante> porNotaDescYNombreAsc() {
        return Comparator.comparingDouble(Estudiante::getNota)
                .reversed()
                .thenComparing(Estudiante::getNombre);
    }

    // C5: Edad ascendente; nombre descendente (comparingInt y sentido independiente)
    public static Comparator<Estudiante> porEdadAscYNombreDesc() {
        return Comparator.comparingInt(Estudiante::getEdad)
                .thenComparing(Comparator.comparing(Estudiante::getNombre).reversed());
    }

    // C6: Carrera asc; nota desc; nombre asc; codigo asc
    public static Comparator<Estudiante> porCarreraNotaNombreCodigo() {
        return Comparator.comparing(Estudiante::getCarrera)
                .thenComparing(Comparator.comparingDouble(Estudiante::getNota).reversed())
                .thenComparing(Estudiante::getNombre)
                .thenComparing(Estudiante::getCodigo);
    }

    // C7: Nota ajustada desc; nombre asc; codigo asc
    public static double calcularNotaAjustada(Estudiante e) {
        if (e.getCarrera().equals("Computacion")) {
            return Math.min(10.0, e.getNota() + 0.5);
        }
        return e.getNota();
    }

    public static Comparator<Estudiante> porNotaAjustadaDescNombreCodigo() {
        return Comparator.comparingDouble((Estudiante e) -> calcularNotaAjustada(e))
                .reversed()
                .thenComparing(Estudiante::getNombre)
                .thenComparing(Estudiante::getCodigo);
    }
}