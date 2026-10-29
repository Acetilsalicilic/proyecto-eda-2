package contenedores;

import entidades.Cancion;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SetCanciones {
    protected ArrayList<LinkedList<Cancion>> canciones;
    protected int conteoColisiones = 0;
    public SetCanciones(int capacidad) {
        canciones = new ArrayList<>(capacidad);
        for (int i=0; i<capacidad; i++)
            canciones.add(new LinkedList<>());
    }
    public ArrayList<LinkedList<Cancion>> getTable() {
        return canciones;
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

        // Incrementar el conteo de colisiones
        if (!canciones.get(hash).isEmpty())
            conteoColisiones++;
        canciones.get(hash).add(cancion);
    }
    public void eliminarCancion(Cancion cancion) {
        if (!contieneCancion(cancion)) return;

        int hash = cancion.getId() % canciones.size();

        canciones.get(hash).remove(cancion);

        if (!canciones.get(hash).isEmpty())
            conteoColisiones--;
    }
    public int getConteoColisiones() {
        return conteoColisiones;
    }
    public double getFactorCarga() {
        int totalElementos = 0;
        for (var l : canciones)
            totalElementos += l.size();
        return (double) totalElementos / canciones.size();
    }
}
