package entidades;

import java.util.LinkedList;
import java.util.List;

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
