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
    public static <T extends Entidad> void mergeSort(List<T> lista, Comparator<T> cmp) {
        particionar(lista, cmp, 0, lista.size());
    }

    /**
     *  Implementa la separación en sublistas utilizado en Merge Sort.
     *
     * @param lista
     * @param cmp
     * @param i indice inicio. Inclusivo
     * @param j indice final. Exclusivo
     * @param <T> Subtipo de Entidad
     */
    private static <T extends Entidad> void particionar(List<T> lista, Comparator<T> cmp, int i, int j) {
        if (i >= j - 1) return;

        System.out.println("Particionando ["+i+", "+j+"]");

        int mid = i + (j - i)/2;
        particionar(lista, cmp, i, mid);
        particionar(lista, cmp, mid, j);

        merge(lista, cmp, i, mid, j);
    }
    private static <T extends Entidad> void merge(List<T> lista, Comparator<T> cmp, int i, int j, int k) {
        Queue<T> queue = new LinkedList<>();

        int x = i; // Mitad 1
        int y = j; // Mitad 2

        while (x < j && y < k) {
            if (cmp.compare(lista.get(x), lista.get(y)) < 0)
                queue.add(lista.get(x++));
            else
                queue.add(lista.get(y++));
        }

        while (x < j)
            queue.add(lista.get(x++));

        while (y < k)
            queue.add(lista.get(y++));

        for (int a = i; a < k; a++)
            lista.set(a, queue.poll());

        System.out.printf("Después de merge [%d, %d, %d]: ", i, j, k);
        System.out.println(lista.subList(i, k));
    }
}
