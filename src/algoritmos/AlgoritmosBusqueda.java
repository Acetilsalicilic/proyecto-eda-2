package algoritmos;

import entidades.Entidad;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class AlgoritmosBusqueda {
    public static <T extends Entidad> int linearSearch(List<T> lista, T obj, Comparator<T> cmp) {
        for (int i=0; i<lista.size(); i++)
            if (cmp.compare(lista.get(i), obj) == 0)
                return i;
        return -1;
    }
    public static <T extends  Entidad> int binarySearch(List<T> lista, T obj, Comparator<T> cmp) {
        int i = 0;
        int j = lista.size();

        while (i < j) {
            int mid = i + (j-i)/2;

            int comp = cmp.compare(lista.get(mid), obj);
            if (comp == 0) return mid;
            else if (comp > 0) j = mid;
            else i = mid + 1;
        }
        return -1;
    }
}
