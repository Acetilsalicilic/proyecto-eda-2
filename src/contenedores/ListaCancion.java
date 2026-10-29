package contenedores;

import entidades.Cancion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListaCancion {
    private final List<Cancion> canciones = new ArrayList<>();

    public ListaCancion() {
    }

    public void agregar(Cancion cancion) {
        canciones.add(Objects.requireNonNull(cancion));
    }

    public boolean eliminar(Cancion cancion) {
        return canciones.remove(cancion);
    }

    public Cancion obtener(int indice) {
        return canciones.get(indice);
    }

    public int tamano() {
        return canciones.size();
    }

    public boolean estaVacia() {
        return canciones.isEmpty();
    }

    public void limpiar() {
        canciones.clear();
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }
}
