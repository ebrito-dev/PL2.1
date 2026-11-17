import comparators.EstudianteComparators;
import data.EstudiantesData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import models.Estudiante;

public class App {

    public static void main(String[] args) {
        List<Estudiante> original = EstudiantesData.obtenerEstudiantes();
        List<String> codigosOriginales = extraerCodigos(original);

        // C1: Codigo ascendente (Orden natural)
        List<Estudiante> c1 = new ArrayList<>(original);
        c1.sort(null);
        imprimirConsulta("C1", "Codigo ascendente (Orden natural)", c1);
        verificarOrden(c1, Comparator.naturalOrder());

        // C2: Nombre ascendente; codigo ascendente (Lambda)
        List<Estudiante> c2Lambda = new ArrayList<>(original);
        c2Lambda.sort(EstudianteComparators.porNombreYCodigoLambda());
        imprimirConsulta("C2 (Lambda)", "Nombre ascendente; codigo ascendente", c2Lambda);
        verificarOrden(c2Lambda, EstudianteComparators.porNombreYCodigoLambda());

        // C2: Equivalencia con referencias a metodos
        List<Estudiante> c2Ref = new ArrayList<>(original);
        c2Ref.sort(EstudianteComparators.porNombreYCodigoReferencia());
        System.out.println("Equivalencia C2 (Lambda vs Referencia): " +
                extraerCodigos(c2Lambda).equals(extraerCodigos(c2Ref)));
        System.out.println();

        // C3: Nota ascendente, sin desempate
        List<Estudiante> c3 = new ArrayList<>(original);
        c3.sort(EstudianteComparators.porNotaAsc());
        imprimirConsulta("C3", "Nota ascendente, sin desempate", c3);
        verificarOrden(c3, EstudianteComparators.porNotaAsc());

        // C4: Nota descendente; nombre ascendente
        List<Estudiante> c4 = new ArrayList<>(original);
        c4.sort(EstudianteComparators.porNotaDescYNombreAsc());
        imprimirConsulta("C4", "Nota descendente; nombre ascendente", c4);
        verificarOrden(c4, EstudianteComparators.porNotaDescYNombreAsc());

        // C5: Edad ascendente; nombre descendente
        List<Estudiante> c5 = new ArrayList<>(original);
        c5.sort(EstudianteComparators.porEdadAscYNombreDesc());
        imprimirConsulta("C5", "Edad ascendente; nombre descendente", c5);
        verificarOrden(c5, EstudianteComparators.porEdadAscYNombreDesc());

        // C6: Carrera asc; nota desc; nombre asc; codigo asc
        List<Estudiante> c6 = new ArrayList<>(original);
        c6.sort(EstudianteComparators.porCarreraNotaNombreCodigo());
        imprimirConsulta("C6", "Carrera asc; nota desc; nombre asc; codigo asc", c6);
        verificarOrden(c6, EstudianteComparators.porCarreraNotaNombreCodigo());

        // C7: Nota ajustada desc; nombre asc; codigo asc
        List<Estudiante> c7 = new ArrayList<>(original);
        c7.sort(EstudianteComparators.porNotaAjustadaDescNombreCodigo());
        imprimirConsultaC7(c7);
        verificarOrden(c7, EstudianteComparators.porNotaAjustadaDescNombreCodigo());

        // Pruebas de aceptacion y casos borde
        ejecutarPruebas(original, codigosOriginales, c3);
    }

    private static void imprimirConsulta(String id, String criterio, List<Estudiante> lista) {
        System.out.println("=== " + id + ": " + criterio + " ===");
        for (Estudiante e : lista) {
            System.out.println(e);
        }
        System.out.println("Total registros: " + lista.size());
        System.out.println();
    }

    private static void imprimirConsultaC7(List<Estudiante> lista) {
        System.out.println("=== C7: Nota ajustada desc; nombre asc; codigo asc ===");
        for (Estudiante e : lista) {
            double ajustada = EstudianteComparators.calcularNotaAjustada(e);
            System.out.printf("%s | Nota Ajustada: %4.1f%n", e, ajustada);
        }
        System.out.println("Total registros: " + lista.size());
        System.out.println();
    }

    private static List<String> extraerCodigos(List<Estudiante> lista) {
        List<String> codigos = new ArrayList<>();
        for (Estudiante e : lista) {
            codigos.add(e.getCodigo());
        }
        return codigos;
    }

    private static void verificarOrden(List<Estudiante> lista, Comparator<Estudiante> comp) {
        for (int i = 0; i < lista.size() - 1; i++) {
            if (comp.compare(lista.get(i), lista.get(i + 1)) > 0) {
                throw new IllegalStateException("Error de orden entre posicion " + i + " y " + (i + 1));
            }
        }
    }

    private static void ejecutarPruebas(List<Estudiante> original, List<String> codigosOriginales, List<Estudiante> c3) {
        System.out.println("=== VERIFICACIONES Y PRUEBAS DE ACEPTACION ===");

        // 1. Original intacto
        boolean originalIntacto = extraerCodigos(original).equals(codigosOriginales);
        System.out.println("1. Lista original intacta (20 codigos en secuencia inicial): " + originalIntacto);

        // 2. Estabilidad en C3 (nota 8.5 -> E014, E007, E005, E008, E010)
        List<String> nota85 = new ArrayList<>();
        for (Estudiante e : c3) {
            if (Double.compare(e.getNota(), 8.5) == 0) {
                nota85.add(e.getCodigo());
            }
        }
        System.out.println("2. Estabilidad C3 (nota 8.5): " + nota85);

        // 3. Lista vacia y un solo elemento
        List<Estudiante> vacia = new ArrayList<>();
        vacia.sort(null);
        List<Estudiante> uno = new ArrayList<>(List.of(original.get(0)));
        uno.sort(EstudianteComparators.porNotaAsc());
        System.out.println("3. Lista vacia size=" + vacia.size() + ", un elemento=" + uno.get(0).getCodigo());

        // 4. Empate completo en C4 (E014, E005, E010)
        List<Estudiante> empateC4 = new ArrayList<>(List.of(
            original.get(0),  // E014 (Ana, 8.5)
            original.get(6),  // E005 (Ana, 8.5)
            original.get(18)  // E010 (Ana, 8.5)
        ));
        empateC4.sort(EstudianteComparators.porNotaDescYNombreAsc());
        System.out.println("4. Empate completo C4 (debe conservar E014, E005, E010): " + extraerCodigos(empateC4));

        // 5. Caso propio: Empate en edad y nombre en C5 (E003 y E013: Luis, 19)
        List<Estudiante> empateC5 = new ArrayList<>(List.of(
            original.get(1),  // E003 (Luis, 19)
            original.get(19)  // E013 (Luis, 19)
        ));
        empateC5.sort(EstudianteComparators.porEdadAscYNombreDesc());
        System.out.println("5. Caso propio - Empate completo C5 (E003, E013): " + extraerCodigos(empateC5));
    }
}