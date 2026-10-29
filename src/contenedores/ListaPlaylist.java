package contenedores;

import entidades.Playlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListaPlaylist {
    private final List<Playlist> playlists = new ArrayList<>();

    public ListaPlaylist() {
    }

    public void agregar(Playlist playlist) {
        playlists.add(Objects.requireNonNull(playlist));
    }

    public boolean eliminar(Playlist playlist) {
        return playlists.remove(playlist);
    }

    public Playlist obtener(int indice) {
        return playlists.get(indice);
    }

    public int tamano() {
        return playlists.size();
    }

    public boolean estaVacia() {
        return playlists.isEmpty();
    }

    public void limpiar() {
        playlists.clear();
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }
}
