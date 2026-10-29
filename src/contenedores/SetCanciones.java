package contenedores;

import entidades.Cancion;

import java.util.ArrayList;
import java.util.LinkedList;

public class SetCanciones {
    protected ArrayList<LinkedList<Cancion>> canciones;
    public SetCanciones(int capacidad) {
        canciones = new ArrayList<>(capacidad);
        for (int i=0; i<capacidad; i++)
            canciones.add(new LinkedList<>());
    }
    public boolean contieneCancion(Cancion cancion) {
        int hash = cancion.getId() % canciones.size();

        for (var c : canciones.get(hash)) {
            if (c.getId() == cancion.getId())
                return true;
        }
        return false;
    }
    public void agregarCancion(Cancion cancion) {
        if (contieneCancion(cancion))
            return;
        int hash = cancion.getId() % canciones.size();

        canciones.get(hash).add(cancion);
    }
    public void eliminarCancion(Cancion cancion) {
        if (!contieneCancion(cancion)) return;

        int hash = cancion.getId() % canciones.size();

        canciones.get(hash).remove(cancion);
    }
}
