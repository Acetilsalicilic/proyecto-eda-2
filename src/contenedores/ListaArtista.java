package contenedores;

import entidades.Artista;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListaArtista {
    private final List<Artista> artistas = new ArrayList<>();

    public ListaArtista() {
    }

    public void agregar(Artista artista) {
        artistas.add(Objects.requireNonNull(artista));
    }

    public boolean eliminar(Artista artista) {
        return artistas.remove(artista);
    }

    public Artista obtener(int indice) {
        return artistas.get(indice);
    }

    public int tamano() {
        return artistas.size();
    }

    public boolean estaVacia() {
        return artistas.isEmpty();
    }

    public void limpiar() {
        artistas.clear();
    }

    public List<Artista> getArtistas() {
        return artistas;
    }
}
