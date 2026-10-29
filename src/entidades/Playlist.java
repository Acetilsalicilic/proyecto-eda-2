package entidades;

import algoritmos.AlgoritmosBusqueda;
import algoritmos.AlgoritmosOrdenamiento;
import contenedores.SetCanciones;

import javax.swing.text.html.Option;
import java.util.*;

public class Playlist {
    private String nombre;
    private List<Cancion> canciones;
    private SetCanciones conjunto_canciones;

    public Playlist(String nombre) {
        this.nombre = nombre;
        this.canciones = new LinkedList<>();
        this.conjunto_canciones = new SetCanciones(10);
    }

    /* Métodos importantes */
    public int getDuracionTotal() {
        int ans = 0;
        for (var c : canciones)
            ans += c.getSegundos();
        return ans;
    }

    public void anadirCancion(Cancion cancion) {
        if (cancion == null) return;
        if (conjunto_canciones.contieneCancion(cancion)) return;
        conjunto_canciones.agregarCancion(cancion);
        canciones.add(cancion);
    }

    public void borrarCancion(Cancion cancion) {
        if (cancion == null) return;
        if (conjunto_canciones.contieneCancion(cancion)) {
            conjunto_canciones.eliminarCancion(cancion);
        }
        canciones.remove(cancion);
    }

    public boolean contieneCancion(Cancion cancion) {
        return conjunto_canciones.contieneCancion(cancion);
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
