package contenedores;

import entidades.Cancion;

import java.util.Optional;

public class MapCanciones extends SetCanciones {
    public MapCanciones(int capacidad) {
        super(capacidad);
    }

    public Optional<Cancion> get(int id) {
        int hash = id % canciones.size();
        for (var c : canciones.get(hash))
            if (c.getId() == id)
                return Optional.of(c);
        return Optional.empty();
    }
}
