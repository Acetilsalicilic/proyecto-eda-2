package servicios;

import algoritmos.AlgoritmosBusqueda;
import algoritmos.AlgoritmosOrdenamiento;
import contenedores.*;
import entidades.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GestorMusical {
    private final ListaCancion canciones = new ListaCancion();
    private final ListaPlaylist playlists = new ListaPlaylist();
    private final ListaArtista artistas = new ListaArtista();
    private final MapCanciones indiceCanciones = new MapCanciones(31);
    private final List<Album> albumes = new ArrayList<>();

    public static GestorMusical instance = new GestorMusical();

    private GestorMusical() {}

    public Cancion agregarCancion(String nombre, Artista artista, LocalDate anio, String genero, Album album, int segundos) {
        if (nombre == null || nombre.isEmpty()) throw new IllegalArgumentException("Nombre vacío");
        if (segundos <= 0) throw new IllegalArgumentException("Duración inválida");
        Cancion cancion = new Cancion(nombre, artista, anio, genero, album, segundos);
        canciones.agregar(cancion);
        indiceCanciones.agregarCancion(cancion);
        return cancion;
    }

    public boolean eliminarCancion(Cancion cancion) {
        if (cancion == null) return false;
        boolean eliminado = canciones.eliminar(cancion);
        if (eliminado) {
            indiceCanciones.eliminarCancion(cancion);
        }
        return eliminado;
    }

    public List<Cancion> listarCanciones() {
        return canciones.getCanciones();
    }

    private Comparator<Cancion> comparadorPara(CriterioOrden criterio) {
        return switch (criterio) {
            case NOMBRE -> Comparator.comparing(Cancion::getNombre, String.CASE_INSENSITIVE_ORDER);
            case ARTISTA -> Comparator.comparing(c -> c.getArtista() == null ? "" : c.getArtista().getNombre(), String.CASE_INSENSITIVE_ORDER);
            case ANIO -> Comparator.comparing(c -> c.getAnioLanzamiento() == null ? LocalDate.MIN : c.getAnioLanzamiento());
            case DURACION -> Comparator.comparingInt(Cancion::getSegundos);
        };
    }

    public void ordenarCanciones(CriterioOrden criterio, AlgoritmoOrden algoritmo) {
        Comparator<Cancion> comparador = comparadorPara(criterio);
        List<Cancion> lista = canciones.getCanciones();
        if (algoritmo == AlgoritmoOrden.HEAP) {
            AlgoritmosOrdenamiento.heapSort(lista, comparador);
        } else {
            AlgoritmosOrdenamiento.mergeSort(lista, comparador);
        }
    }

    public List<Cancion> busquedaLinealPorNombre(String nombre) {
        if (nombre == null) return new ArrayList<>();
        return AlgoritmosBusqueda.linearFilter(canciones.getCanciones(),
                c -> c.getNombre() != null && c.getNombre().equalsIgnoreCase(nombre));
    }

//    private void asegurarOrdenPorNombre() {
//        ordenarCanciones(CriterioOrden.NOMBRE, AlgoritmoOrden.HEAP);
//    }

    public Optional<Cancion> busquedaBinariaPorNombre(String nombre) {
        if (nombre == null) return Optional.empty();

        ordenarCanciones(CriterioOrden.NOMBRE, AlgoritmoOrden.HEAP);
        List<Cancion> lista = canciones.getCanciones();

        int indice = AlgoritmosBusqueda.binarySearch(lista, c -> {
            if (c.getNombre() == null) return -1;
            return c.getNombre().compareToIgnoreCase(nombre);
        });

        if (indice == -1) return Optional.empty();
        return Optional.of(lista.get(indice));
    }

    public Optional<Cancion> buscarCancionPorId(int id) {
            return indiceCanciones.get(id);
    }

    public String describirTablaHash() {
        StringBuilder sb = new StringBuilder();
        var tabla = indiceCanciones.getTable();
        sb.append("Tabla Hash - Capacidad: ").append(tabla.size()).append("\n");
        int colisiones = 0;
        for (int i = 0; i < tabla.size(); i++) {
            var cubeta = tabla.get(i);
            sb.append("Cubeta ").append(i).append(": ").append(cubeta.size()).append(" elementos\n");
            if (cubeta.size() > 1) colisiones += (cubeta.size() - 1);
            for (var c : cubeta) {
                sb.append("  - ").append(c.getNombre()).append(" (ID: ").append(c.getId()).append(")\n");
            }
        }
        double factorCarga = tabla.isEmpty() ? 0 : (double) canciones.tamano() / tabla.size();
        sb.append("Colisiones: ").append(colisiones).append("\n");
        sb.append("Factor de carga: ").append(String.format("%.2f", factorCarga)).append("\n");
        return sb.toString();
    }

    public Artista agregarArtista(String nombre, LocalDate fechaNacimiento, String tipo) {
        if (nombre == null || nombre.isEmpty()) throw new IllegalArgumentException("Nombre vacío");
        Artista artista = new Artista(nombre, fechaNacimiento, tipo);
        artistas.agregar(artista);
        return artista;
    }

    public boolean eliminarArtista(Artista artista) {
        return artistas.eliminar(artista);
    }

    public List<Artista> listarArtistas() {
        return artistas.getArtistas();
    }

    private Comparator<Artista> comparadorParaArtista(CriterioArtista criterio) {
        return switch (criterio) {
            case NOMBRE -> Comparator.comparing(Artista::getNombre, String.CASE_INSENSITIVE_ORDER);
            case EDAD -> Comparator.comparing(Artista::getFechaNacimiento, Comparator.nullsLast(Comparator.naturalOrder()));
            case TIPO -> Comparator.comparing(Artista::getTipoArtista, String.CASE_INSENSITIVE_ORDER);
        };
    }

    public void ordenarArtistas(CriterioArtista criterio) {
        AlgoritmosOrdenamiento.mergeSort(artistas.getArtistas(), comparadorParaArtista(criterio));
    }

    public List<Cancion> cancionesDe(Artista artista) {
        return AlgoritmosBusqueda.linearFilter(canciones.getCanciones(),
                c -> c.getArtista() != null && c.getArtista().equals(artista));
    }

    public Playlist crearPlaylist(String nombre) {
        if (nombre == null || nombre.isEmpty()) throw new IllegalArgumentException("Nombre vacío");
        Playlist playlist = new Playlist(nombre);
        playlists.agregar(playlist);
        return playlist;
    }

    public boolean eliminarPlaylist(String nombre) {
        if (nombre == null) return false;
        for (int i = 0; i < playlists.tamano(); i++) {
            if (playlists.obtener(i).getNombre().equalsIgnoreCase(nombre)) {
                return playlists.eliminar(playlists.obtener(i));
            }
        }
        return false;
    }

    public List<Playlist> listarPlaylists() {
        return playlists.getPlaylists();
    }

    public Playlist buscarPlaylistPorNombre(String nombre) {
        if (nombre == null) return null;
        for (Playlist p : playlists.getPlaylists()) {
            if (p.getNombre().equalsIgnoreCase(nombre)) return p;
        }
        return null;
    }

    public boolean agregarCancionAPlaylist(String nombrePlaylist, Cancion cancion) {
        Playlist playlist = buscarPlaylistPorNombre(nombrePlaylist);
        if (playlist == null || cancion == null) return false;
        if (playlist.contieneCancion(cancion)) return false;
        playlist.anadirCancion(cancion);
        return true;
    }

    public boolean eliminarCancionDePlaylist(String nombrePlaylist, Cancion cancion) {
        Playlist playlist = buscarPlaylistPorNombre(nombrePlaylist);
        if (playlist == null || cancion == null) return false;
        if (!playlist.contieneCancion(cancion)) return false;
        playlist.borrarCancion(cancion);
        return true;
    }

    public List<Cancion> cancionesDePlaylist(String nombrePlaylist) {
        Playlist playlist = buscarPlaylistPorNombre(nombrePlaylist);
        if (playlist == null) return new ArrayList<>();
        return playlist.getCanciones();
    }

    public void ordenarCancionesDePlaylist(String nombrePlaylist, CriterioOrden criterio, AlgoritmoOrden algoritmo) {
        Playlist playlist = buscarPlaylistPorNombre(nombrePlaylist);
        if (playlist == null) return;
        List<Cancion> lista = playlist.getCanciones();
        Comparator<Cancion> comparador = comparadorPara(criterio);
        if (algoritmo == AlgoritmoOrden.HEAP) {
            AlgoritmosOrdenamiento.heapSort(lista, comparador);
        } else {
            AlgoritmosOrdenamiento.mergeSort(lista, comparador);
        }
    }

    public List<Cancion> buscarCancionEnPlaylist(String nombrePlaylist, String nombre) {
        Playlist playlist = buscarPlaylistPorNombre(nombrePlaylist);
        if (playlist == null || nombre == null) return new ArrayList<>();
        return AlgoritmosBusqueda.linearFilter(playlist.getCanciones(),
                c -> c.getNombre() != null && c.getNombre().equalsIgnoreCase(nombre));
    }

    public boolean reproducirPlaylist(String nombrePlaylist) {
        Playlist playlist = buscarPlaylistPorNombre(nombrePlaylist);
        if (playlist == null || playlist.getCanciones().isEmpty()) return false;
        System.out.println("Reproduciendo: " + playlist.getNombre());
        int total = 0;
        for (Cancion c : playlist.getCanciones()) {
            String artistaNombre = c.getArtista() == null ? "Desconocido" : c.getArtista().getNombre();
            System.out.println("  > " + c.getNombre() + " - " + artistaNombre + " (" + c.getSegundos() + "s)");
            total += c.getSegundos();
        }
        System.out.println("Duración total: " + (total / 60) + ":" + String.format("%02d", total % 60));
        return true;
    }

    public ListaCancion getListaCanciones() { return canciones; }
    public ListaArtista getListaArtistas() { return artistas; }
    public ListaPlaylist getListaPlaylists() { return playlists; }
    public Album seleccionarOCrearAlbum(String nombreAlbum) {
        if (nombreAlbum == null || nombreAlbum.isEmpty()) return null;
        for (Album a : albumes) {
            if (a.getNombre().equalsIgnoreCase(nombreAlbum)) return a;
        }
        Album nuevo = new Album(nombreAlbum, "", LocalDate.now());
        albumes.add(nuevo);
        return nuevo;
    }
}
