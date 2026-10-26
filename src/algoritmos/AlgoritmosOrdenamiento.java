package algoritmos;

import entidades.Cancion;
import entidades.Entidad;

import java.time.chrono.ChronoLocalDate;
import java.util.*;
import java.util.function.Function;

import static java.util.Collections.swap;

public class AlgoritmosOrdenamiento {
    public static <T extends Entidad> void heapSort(List<T> lista, Comparator<T> cmp) {
        for (int i=lista.size() - 1; i>=0; i--)
            heapify(lista, cmp, i, lista.size());

        for (int i=lista.size() - 1; i>=0; i--) {
            swap(lista, 0, i);
            heapify(lista, cmp, 0, i);
        }
    }
    private static <T extends Entidad> void  heapify (List<T> lista, Comparator<T> cmp, int indice, int limite) {
        if (indice >= limite)
            return;

        int max = indice;
        if (indice*2 < limite && cmp.compare(lista.get(indice*2), lista.get(max)) > 0)
            max = indice*2;

        if (indice*2+1 < limite && cmp.compare(lista.get(indice*2+1), lista.get(max)) > 0)
            max = indice*2+1;

        if (max != indice) {
            swap(lista, indice, max);
            heapify(lista, cmp, max, limite);
        }
    }
    public static <T extends Entidad> void mergeSort(List<Entidad> lista, Comparator<Entidad> cmp) {

    }
}
