package algoritmos;

import entidades.Entidad;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class AlgoritmosBusqueda {
    public static <T extends Entidad> int linearSearch(List<T> lista, Predicate<T> es) {
        for (int i=0; i<lista.size(); i++)
            if (es.test(lista.get(i)))
                return i;
        return -1;
    }
    public static <T extends Entidad> List<T> linearFilter(List<T> lista, Predicate<T> es) {
        List<T> lista2 = new ArrayList<>();
        for (var e : lista)
            if (es.test(e))
                lista2.add(e);
        return lista2;
    }
    public static <T extends  Entidad> int binarySearch(List<T> lista, Function<T, Integer> cmp) {
        int i = 0;
        int j = lista.size();

        while (i < j) {
            int mid = i + (j-i)/2;

            int comp = cmp.apply(lista.get(mid));
            if (comp == 0) return mid;
            else if (comp > 0) j = mid;
            else i = mid + 1;
        }
        return -1;
    }
}
