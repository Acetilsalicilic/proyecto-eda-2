package entidades;

import algoritmos.AlgoritmosBusqueda;
import algoritmos.AlgoritmosOrdenamiento;

import javax.swing.text.html.Option;
import java.util.*;

public class Playlist {
    private String nombre;
    private List<Cancion> canciones;

    public Playlist(String nombre) {
        this.nombre = nombre;
        this.canciones = new LinkedList<>();
    }

    /* Métodos importantes */
    public int getDuracionTotal() {
        int ans = 0;
        for (var c : canciones)
            ans += c.getSegundos();
        return ans;
    }

    public void anadirCancion(Cancion cancion) {
        canciones.add(cancion);
    }

    public void borrarCancion(Cancion cancion) {
        canciones.remove(cancion);
    }

    public void ordenarPorNombre() {
        AlgoritmosOrdenamiento.heapSort(canciones,
                Comparator.comparing(Cancion::getAnioLanzamiento)
        );
    }

    public void ordenarPorFecha() {
        AlgoritmosOrdenamiento.mergeSort(canciones,
                Comparator.comparing(Cancion::getAnioLanzamiento)
        );
    }

    public void ordenarPorDuracion() {
        AlgoritmosOrdenamiento.heapSort(canciones,
                Comparator.comparing(Cancion::getSegundos)
        );
    }

    public Optional<Cancion> buscarPorNombre(String nombre) {
        List<Cancion> copia = new ArrayList<>(canciones);
        AlgoritmosOrdenamiento.heapSort(copia, Comparator.comparing(Cancion::getNombre));

        int ind = AlgoritmosBusqueda.binarySearch(copia, c -> c.getNombre().compareToIgnoreCase(nombre));

        if (ind == -1)
            return Optional.empty();
        return Optional.of(canciones.get(ind));
    }

    public List<Cancion> buscarPorArtista(Artista artista) {
        return AlgoritmosBusqueda.linearFilter(canciones, c -> c.getArtista() == artista);
    }

    /* Getters y Setters */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

}
