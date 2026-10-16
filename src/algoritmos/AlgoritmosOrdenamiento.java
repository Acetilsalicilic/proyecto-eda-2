package algoritmos;

import entidades.Cancion;
import entidades.Entidad;

import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.function.Function;

public class AlgoritmosOrdenamiento {
    public static void radixSort(List<Entidad> c, Function<Entidad, String> getter) {
        String clave = getter.apply(c.getFirst());
    }
    public static void mergeSort(List<Entidad> lista, Function<Entidad, Comparable<ChronoLocalDate>> getter) {

    }
}
